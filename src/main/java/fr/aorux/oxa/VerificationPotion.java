/*     */ package fr.aorux.oxa;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class VerificationPotion implements Listener {
/*     */   private Main main;
/*     */   
/*     */   public VerificationPotion(Main main) {
/*  24 */     this.main = main;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamage(EntityDamageEvent event) {
/*  29 */     if (event.getEntity() instanceof Player) {
/*  30 */       Player damaged = (Player)event.getEntity();
/*  31 */       if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || 
/*  32 */         event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
/*  33 */         markPlayerDamaged(damaged);
/*  34 */       } else if (event.getCause() == EntityDamageEvent.DamageCause.MAGIC && 
/*  35 */         event.getDamage() <= 0.0D) {
/*  36 */         handlePotionCheating(damaged);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void markPlayerDamaged(Player player) {
/*  43 */     player.setMetadata("justDamaged", (MetadataValue)new FixedMetadataValue((Plugin)this.main, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   private void handlePotionCheating(Player player) {
/*  47 */     String playerName = player.getName();
/*  48 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  49 */     if (player.getGameMode() == GameMode.SURVIVAL && this.main.getConfig().getBoolean("verification-potion.enabled") && !whiteList.contains(playerName)) {
/*  50 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("verification-potion.broadcast"));
/*  51 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-potion.report"));
/*     */       
/*  53 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/*  54 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*  55 */       this.main.saveConfig();
/*     */       
/*  57 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/*  58 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/*  59 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/*  60 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Anti Potion");
/*  61 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*  64 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  65 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  66 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/*  67 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Anti Potion");
/*  68 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/*  72 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  73 */         String date = dateFormat.format(new Date());
/*  74 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*  75 */         String nomFichier = "logs-" + date + ".txt";
/*  76 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*  77 */         FileWriter writer = new FileWriter(cheminFichier, true);
/*  78 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*  79 */         String date2 = dateFormat2.format(new Date());
/*  80 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat : anti potion.");
/*  81 */         writer.close();
/*  82 */       } catch (IOException e) {
/*  83 */         e.printStackTrace();
/*     */       } 
/*     */       
/*  86 */       if (this.main.getConfig().getInt("verification-potion.max-violations") < kickCount) {
/*  87 */         if (this.main.getConfig().getBoolean("verification-potion.time-ban-enabled")) {
/*  88 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/*  89 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-potion.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("verification-potion.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/*  91 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/*  92 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-potion.ban-message"), null, null);
/*     */         } 
/*  94 */         int kickCount2 = 0;
/*  95 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/*  97 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  98 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  99 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 100 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Anti Potion");
/* 101 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 104 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 105 */           String date = dateFormat.format(new Date());
/* 106 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 107 */           String nomFichier = "logs-" + date + ".txt";
/* 108 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 109 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 110 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 111 */           String date2 = dateFormat2.format(new Date());
/* 112 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("verification-potion.max-violations") + " times.");
/* 113 */           writer.close();
/* 114 */         } catch (IOException e) {
/* 115 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 119 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 120 */         if (onlinePlayer.isOp()) {
/* 121 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for use invalid packet.");
/*     */         }
/*     */       } 
/*     */       
/* 125 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-potion.kick-message"));
/*     */     } 
/* 127 */     resetPlayerStatus(player);
/*     */   }
/*     */   
/*     */   private void resetPlayerStatus(Player player) {
/* 131 */     player.removeMetadata("justDamaged", (Plugin)this.main);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 136 */       URL url = new URL(webhookUrl);
/* 137 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 138 */       connection.setRequestMethod("POST");
/* 139 */       connection.setRequestProperty("Content-Type", "application/json");
/* 140 */       connection.setDoOutput(true);
/*     */       
/* 142 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 144 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 145 */         try { byte[] input = json.getBytes("utf-8");
/* 146 */           os.write(input, 0, input.length); }
/* 147 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 152 */       connection.disconnect();
/* 153 */     } catch (Exception e) {
/* 154 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\VerificationPotion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */