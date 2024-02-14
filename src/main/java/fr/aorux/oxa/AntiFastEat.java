/*     */ package fr.aorux.oxa;
/*     */ 
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class AntiFastEat implements Listener {
/*     */   private Main main;
/*  27 */   private ArrayList<Player> eating = new ArrayList<>();
/*  28 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  29 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiFastEat(Main main) {
/*  32 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _MainAC) {
/*  36 */     this.main = _MainAC;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void eatItem(PlayerItemConsumeEvent e) {
/*  41 */     Player player = e.getPlayer();
/*  42 */     if (this.eating.contains(player)) {
/*  43 */       e.setCancelled(true);
/*  44 */       handleFastEat(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void rightClickItem(PlayerInteractEvent e) {
/*  50 */     Player player = e.getPlayer();
/*  51 */     if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
/*  52 */       if (!this.eating.contains(player)) {
/*  53 */         this.eating.add(player);
/*     */       }
/*  55 */       checkEat(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleFastEat(Player player) {
/*  60 */     if (!this.AC.contains(player) && this.main.getConfig().getBoolean("anti-fast-eat.enabled")) {
/*  61 */       handleWarningsAndKick(player);
/*  62 */       this.AC.add(player);
/*  63 */       resetAC(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleWarningsAndKick(Player player) {
/*  69 */     if (!this.warnings.containsKey(player)) {
/*  70 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/*  72 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/*  73 */       this.warnings.remove(player);
/*  74 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*  76 */     String playerName = player.getName();
/*  77 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  78 */     if (player.getGameMode() == GameMode.SURVIVAL && this.main.getConfig().getBoolean("anti-fats-eat.enabled") && !whiteList.contains(playerName)) {
/*  79 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-fast-eat.broadcast"));
/*  80 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-eat.report"));
/*  81 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/*  82 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*  83 */       this.main.saveConfig();
/*     */       
/*  85 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/*  86 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/*  87 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/*  88 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Fast Eat");
/*  89 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*  92 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  93 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  94 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/*  95 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Fast Eat");
/*  96 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/* 100 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 101 */         String date = dateFormat.format(new Date());
/* 102 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 103 */         String nomFichier = "logs-" + date + ".txt";
/* 104 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 105 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 106 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 107 */         String date2 = dateFormat2.format(new Date());
/* 108 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Fast Eat.");
/* 109 */         writer.close();
/* 110 */       } catch (IOException e) {
/* 111 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 114 */       if (this.main.getConfig().getInt("anti-fast-eat.max-violations") < kickCount) {
/* 115 */         if (this.main.getConfig().getBoolean("anti-fast-eat.time-ban-enabled")) {
/* 116 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 117 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-eat.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-fast-eat.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 119 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 120 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-eat.ban-message"), null, null);
/*     */         } 
/* 122 */         int kickCount2 = 0;
/* 123 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 125 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 126 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 127 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 128 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Fast Eat");
/* 129 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 132 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 133 */           String date = dateFormat.format(new Date());
/* 134 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 135 */           String nomFichier = "logs-" + date + ".txt";
/* 136 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 137 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 138 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 139 */           String date2 = dateFormat2.format(new Date());
/* 140 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-fast-eat.max-violations") + " times.");
/* 141 */           writer.close();
/* 142 */         } catch (IOException e) {
/* 143 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 148 */         if (onlinePlayer.isOp()) {
/* 149 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for fast eat.");
/*     */         }
/*     */       } 
/*     */       
/* 153 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-eat.kick-message"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkEat(Player player) {
/* 158 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (this.eating.contains(player)) {
/*     */             this.eating.remove(player);
/*     */           }
/* 162 */         }, 29L);
/*     */   }
/*     */   
/*     */   private void resetAC(Player player) {
/* 166 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {  }, 120L);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 171 */       URL url = new URL(webhookUrl);
/* 172 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 173 */       connection.setRequestMethod("POST");
/* 174 */       connection.setRequestProperty("Content-Type", "application/json");
/* 175 */       connection.setDoOutput(true);
/*     */       
/* 177 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 179 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 180 */         try { byte[] input = json.getBytes("utf-8");
/* 181 */           os.write(input, 0, input.length); }
/* 182 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 187 */       connection.disconnect();
/* 188 */     } catch (Exception e) {
/* 189 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiFastEat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */