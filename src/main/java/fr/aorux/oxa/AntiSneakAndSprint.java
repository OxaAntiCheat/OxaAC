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
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class AntiSneakAndSprint implements Listener {
/*     */   private Main main;
/*  27 */   private HashMap<Player, Location> origin = new HashMap<>();
/*  28 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  29 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiSneakAndSprint(Main main) {
/*  32 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _MainAC) {
/*  36 */     this.main = _MainAC;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent e) {
/*  41 */     Player player = e.getPlayer();
/*  42 */     Location loc = player.getLocation();
/*  43 */     if (!this.origin.containsKey(player)) {
/*  44 */       this.origin.put(player, loc);
/*     */     }
/*  46 */     detectChange(player, loc, e);
/*     */   }
/*     */   
/*     */   public void detectChange(Player player, Location loc, PlayerMoveEvent e) {
/*  50 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (!player.hasPotionEffect(PotionEffectType.SPEED) && player.isSneaking()) {
/*     */             Location originLocation = this.origin.get(player);
/*     */ 
/*     */ 
/*     */             
/*     */             if (originLocation != null && originLocation.distance(loc) >= this.main.getConfig().getDouble("anti-sneak-sprint.speed-max")) {
/*     */               if (originLocation.getY() >= loc.getY() - 1.0D && originLocation.getY() <= loc.getY() + 1.0D && this.main.getConfig().getBoolean("anti-sneak-sprint.enabled")) {
/*     */                 kickSpeed(player);
/*     */               }
/*     */             } else {
/*     */               this.origin.remove(player);
/*     */             } 
/*     */           } 
/*  64 */         }, 5L);
/*     */   }
/*     */   
/*     */   private void kickSpeed(Player player) {
/*  68 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (!player.getAllowFlight()) {
/*     */             if (!this.AC.contains(player)) {
/*     */               handleWarningsAndKick(player);
/*     */               
/*     */               this.AC.add(player);
/*     */               resetAC(player);
/*     */             } 
/*     */             Location originLocation = this.origin.get(player);
/*     */             if (originLocation != null) {
/*     */               handleWarningsAndKick(player);
/*     */             }
/*     */           } 
/*  81 */         }, 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleWarningsAndKick(Player player) {
/*  86 */     if (!this.warnings.containsKey(player)) {
/*  87 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/*  89 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/*  90 */       this.warnings.remove(player);
/*  91 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*     */     
/*  94 */     String playerName = player.getName();
/*  95 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  96 */     if (player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName)) {
/*  97 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-sneak-sprint.broadcast"));
/*  98 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-sneak-sprint.report"));
/*  99 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 100 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/* 101 */       this.main.saveConfig();
/*     */       
/* 103 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/* 104 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/* 105 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/* 106 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Sneak and Sprint");
/* 107 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/* 110 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 111 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 112 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/* 113 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Sneak and Sprint");
/* 114 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/* 118 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 119 */         String date = dateFormat.format(new Date());
/* 120 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 121 */         String nomFichier = "logs-" + date + ".txt";
/* 122 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 123 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 124 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 125 */         String date2 = dateFormat2.format(new Date());
/* 126 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Sneak and Sprint.");
/* 127 */         writer.close();
/* 128 */       } catch (IOException e) {
/* 129 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 132 */       if (this.main.getConfig().getInt("anti-sneak-and-sprint.max-violations") < kickCount) {
/* 133 */         if (this.main.getConfig().getBoolean("anti-sneak-and-sprint.time-ban-enabled")) {
/* 134 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 135 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-sneak-and-sprint.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-sneak-and-sprint.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 137 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 138 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-sneak-and-sprint.ban-message"), null, null);
/*     */         } 
/* 140 */         int kickCount2 = 0;
/* 141 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 143 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 144 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 145 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 146 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Sneak and Sprint");
/* 147 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 150 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 151 */           String date = dateFormat.format(new Date());
/* 152 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 153 */           String nomFichier = "logs-" + date + ".txt";
/* 154 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 155 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 156 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 157 */           String date2 = dateFormat2.format(new Date());
/* 158 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-sneak-sprint.max-violations") + " times.");
/* 159 */           writer.close();
/* 160 */         } catch (IOException e) {
/* 161 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 165 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 166 */         if (onlinePlayer.isOp()) {
/* 167 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for sneak and sprint.");
/*     */         }
/*     */       } 
/*     */       
/* 171 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-sneak-sprint.kick-message"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetAC(Player player) {
/* 176 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {  }, 120L);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 181 */       URL url = new URL(webhookUrl);
/* 182 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 183 */       connection.setRequestMethod("POST");
/* 184 */       connection.setRequestProperty("Content-Type", "application/json");
/* 185 */       connection.setDoOutput(true);
/*     */       
/* 187 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 189 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 190 */         try { byte[] input = json.getBytes("utf-8");
/* 191 */           os.write(input, 0, input.length); }
/* 192 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 197 */       connection.disconnect();
/* 198 */     } catch (Exception e) {
/* 199 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiSneakAndSprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */