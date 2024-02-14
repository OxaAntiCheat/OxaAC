/*     */ package fr.aorux.oxa;
/*     */ 
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.text.SimpleDateFormat;
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
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ 
/*     */ public class VerificationFall
/*     */   implements Listener {
/*     */   private Main main;
/*  25 */   private HashMap<Player, Integer> fallDistances = new HashMap<>();
/*     */   
/*     */   public VerificationFall(Main main) {
/*  28 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _MainAC) {
/*  32 */     this.main = _MainAC;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent event) {
/*  37 */     Player player = event.getPlayer();
/*  38 */     Location from = event.getFrom();
/*  39 */     Location to = event.getTo();
/*     */     
/*  41 */     if (from != null && to != null && from.getY() > to.getY()) {
/*  42 */       int fallDistance = (int)(from.getY() - to.getY());
/*     */       
/*  44 */       if (fallDistance > 3) {
/*  45 */         this.fallDistances.put(player, Integer.valueOf(fallDistance));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamage(EntityDamageEvent event) {
/*  52 */     if (event.getEntity() instanceof Player) {
/*  53 */       Player player = (Player)event.getEntity();
/*     */       
/*  55 */       if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
/*  56 */         int fallDistance = ((Integer)this.fallDistances.getOrDefault(player, Integer.valueOf(0))).intValue();
/*     */         
/*  58 */         if (fallDistance >= 3 && event.getCause() == EntityDamageEvent.DamageCause.FALL && event.getDamage() == 0.0D) {
/*  59 */           handleAntiAntifall(player, fallDistance);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleAntiAntifall(Player player, int fallDistance) {
/*  66 */     String playerName = player.getName();
/*  67 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  68 */     if (player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName) && 
/*  69 */       player.getGameMode() == GameMode.SURVIVAL) {
/*  70 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("verification-fall.broadcast"));
/*  71 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-fall.report"));
/*     */       
/*  73 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/*  74 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*  75 */       this.main.saveConfig();
/*     */       
/*  77 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/*  78 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/*  79 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/*  80 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "AntiFall");
/*  81 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*  84 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  85 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  86 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/*  87 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "AntiFall");
/*  88 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/*  92 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  93 */         String date = dateFormat.format(new Date());
/*  94 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*  95 */         String nomFichier = "logs-" + date + ".txt";
/*  96 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*  97 */         FileWriter writer = new FileWriter(cheminFichier, true);
/*  98 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*  99 */         String date2 = dateFormat2.format(new Date());
/* 100 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: AntiFall.");
/* 101 */         writer.close();
/* 102 */       } catch (IOException e) {
/* 103 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 106 */       if (this.main.getConfig().getInt("verification-fall.max-violations") < kickCount) {
/* 107 */         if (this.main.getConfig().getBoolean("verification-fall.time-ban-enabled")) {
/* 108 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 109 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-fall.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("verification-fall.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 111 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 112 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-fall.ban-message"), null, null);
/*     */         } 
/* 114 */         int kickCount2 = 0;
/* 115 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 117 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 118 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 119 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 120 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "AntiFall");
/* 121 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 124 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 125 */           String date = dateFormat.format(new Date());
/* 126 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 127 */           String nomFichier = "logs-" + date + ".txt";
/* 128 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 129 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 130 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 131 */           String date2 = dateFormat2.format(new Date());
/* 132 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("verification-fall.max-violations") + " times.");
/* 133 */           writer.close();
/* 134 */         } catch (IOException e) {
/* 135 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 139 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 140 */         if (onlinePlayer.isOp()) {
/* 141 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for use AntiFall cheat.");
/*     */         }
/*     */       } 
/*     */       
/* 145 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-fall.kick-message"));
/*     */     } 
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 151 */       URL url = new URL(webhookUrl);
/* 152 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 153 */       connection.setRequestMethod("POST");
/* 154 */       connection.setRequestProperty("Content-Type", "application/json");
/* 155 */       connection.setDoOutput(true);
/*     */       
/* 157 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 159 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 160 */         try { byte[] input = json.getBytes("utf-8");
/* 161 */           os.write(input, 0, input.length); }
/* 162 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 167 */       connection.disconnect();
/* 168 */     } catch (Exception e) {
/* 169 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\VerificationFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */