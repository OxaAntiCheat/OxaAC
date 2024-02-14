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
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityShootBowEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class AntiFastBow
/*     */   implements Listener {
/*     */   private Main main;
/*  29 */   private ArrayList<Player> drawing = new ArrayList<>();
/*  30 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  31 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiFastBow(Main main) {
/*  34 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _MainAC) {
/*  38 */     this.main = _MainAC;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void shootBow(EntityShootBowEvent e) {
/*  43 */     if (e.getEntity() instanceof Player) {
/*  44 */       Player player = (Player)e.getEntity();
/*  45 */       if (this.drawing.contains(player) && e.getForce() == 1.0D) {
/*  46 */         e.setCancelled(true);
/*  47 */         handleFastBow(player);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void rightClickItem(PlayerInteractEvent e) {
/*  54 */     Player player = e.getPlayer();
/*  55 */     if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) && 
/*  56 */       e.getItem() != null && e.getItem().getType() == Material.BOW) {
/*  57 */       if (!this.drawing.contains(player)) {
/*  58 */         this.drawing.add(player);
/*     */       }
/*  60 */       checkDraw(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleFastBow(Player player) {
/*  65 */     if (!this.AC.contains(player) && this.main.getConfig().getBoolean("anti-fast-bow.enabled")) {
/*  66 */       handleWarningsAndKick(player);
/*  67 */       this.AC.add(player);
/*  68 */       resetAC(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleWarningsAndKick(Player player) {
/*  74 */     if (!this.warnings.containsKey(player)) {
/*  75 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/*  77 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/*  78 */       this.warnings.remove(player);
/*  79 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*  81 */     String playerName = player.getName();
/*  82 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  83 */     if (player.getGameMode() == GameMode.SURVIVAL && this.main.getConfig().getBoolean("anti-fast-bow.enabled") && !whiteList.contains(playerName)) {
/*  84 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-fast-bow.broadcast"));
/*  85 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-bow.report"));
/*  86 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/*  87 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*     */       
/*  89 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/*  90 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/*  91 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/*  92 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Fast Bow");
/*  93 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*  96 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  97 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  98 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/*  99 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "fast Bow");
/* 100 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/* 104 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 105 */         String date = dateFormat.format(new Date());
/* 106 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 107 */         String nomFichier = "logs-" + date + ".txt";
/* 108 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 109 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 110 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 111 */         String date2 = dateFormat2.format(new Date());
/* 112 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: FastBow.");
/* 113 */         writer.close();
/* 114 */       } catch (IOException e) {
/* 115 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 118 */       if (this.main.getConfig().getInt("anti-fast-bow.max-violations") < kickCount) {
/* 119 */         if (this.main.getConfig().getBoolean("anti-fast-bow.time-ban-enabled")) {
/* 120 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 121 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-bow.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-fast-bow.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 123 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 124 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-bow.ban-message"), null, null);
/*     */         } 
/* 126 */         int kickCount2 = 0;
/* 127 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 129 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 130 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 131 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 132 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "fast Bow");
/* 133 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 136 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 137 */           String date = dateFormat.format(new Date());
/* 138 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 139 */           String nomFichier = "logs-" + date + ".txt";
/* 140 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 141 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 142 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 143 */           String date2 = dateFormat2.format(new Date());
/* 144 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-fast-bow.max-violations") + " times.");
/* 145 */           writer.close();
/* 146 */         } catch (IOException e) {
/* 147 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 151 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 152 */         if (onlinePlayer.isOp()) {
/* 153 */           player.sendMessage("§d§l[Oxa AntiCheat] §r&4the &r" + player.getName() + " &4player was kicked for fast bow.");
/*     */         }
/*     */       } 
/*     */       
/* 157 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fast-bow.kick-message"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkDraw(Player player) {
/* 162 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (this.drawing.contains(player)) {
/*     */             this.drawing.remove(player);
/*     */           }
/* 166 */         }, 19L);
/*     */   }
/*     */   
/*     */   private void resetAC(Player player) {
/* 170 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {  }, 120L);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 175 */       URL url = new URL(webhookUrl);
/* 176 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 177 */       connection.setRequestMethod("POST");
/* 178 */       connection.setRequestProperty("Content-Type", "application/json");
/* 179 */       connection.setDoOutput(true);
/*     */       
/* 181 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 183 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 184 */         try { byte[] input = json.getBytes("utf-8");
/* 185 */           os.write(input, 0, input.length); }
/* 186 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 191 */       connection.disconnect();
/* 192 */     } catch (Exception e) {
/* 193 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiFastBow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */