/*     */ package fr.aorux.oxa;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class ReachHack implements Listener {
/*     */   private JavaPlugin plugin;
/*     */   private Main main;
/*  26 */   private Map<UUID, Integer> reachViolationCounter = new HashMap<>();
/*     */   
/*     */   public ReachHack(JavaPlugin plugin) {
/*  29 */     this.plugin = plugin;
/*  30 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)plugin);
/*  31 */     plugin.getConfig().addDefault("reach-hack.enabled", Boolean.valueOf(true));
/*  32 */     plugin.getConfig().addDefault("reach-hack.max-reach", Double.valueOf(4.0D));
/*  33 */     plugin.getConfig().addDefault("reach-hack.max-violations", Integer.valueOf(10));
/*  34 */     plugin.saveDefaultConfig();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
/*  39 */     if (event.getDamager() instanceof Player && event.getEntity() instanceof Entity) {
/*  40 */       Player player = (Player)event.getDamager();
/*  41 */       Entity target = event.getEntity();
/*  42 */       String playerName = player.getName();
/*  43 */       List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  44 */       if (this.plugin.getConfig().getBoolean("reach-hack.enabled") && !whiteList.contains(playerName)) {
/*  45 */         double maxReach = this.plugin.getConfig().getDouble("reach-hack.max-reach");
/*  46 */         double distance = player.getLocation().distance(target.getLocation());
/*  47 */         if (distance > maxReach) {
/*  48 */           int violationCount = ((Integer)this.reachViolationCounter.getOrDefault(player.getUniqueId(), Integer.valueOf(0))).intValue();
/*  49 */           violationCount++;
/*  50 */           if (violationCount > this.plugin.getConfig().getInt("reach-hack.max-violations")) {
/*  51 */             player.kickPlayer("[Oxa AntiCheat] " + this.plugin.getConfig().getString("reach-hack.kick-message").replace("&", "§"));
/*  52 */             int kickCount = this.plugin.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/*  53 */             this.plugin.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*  54 */             this.plugin.saveConfig();
/*  55 */             if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/*  56 */               String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/*  57 */               String title = this.main.getConfig().getString("webhook.alerts.title");
/*  58 */               String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Reach Hack");
/*  59 */               sendWebhook(webhookUrl, title, description);
/*     */             } 
/*     */             
/*  62 */             if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  63 */               String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  64 */               String title = this.main.getConfig().getString("webhook.punishments.title");
/*  65 */               String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Reach Hack");
/*  66 */               sendWebhook(webhookUrl, title, description);
/*     */             } 
/*  68 */             if (kickCount > 10) {
/*  69 */               if (this.main.getConfig().getBoolean("reach-hack.time-ban-enabled")) {
/*  70 */                 Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/*  71 */                     "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("reach-hack.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("reach-hack.time-ban") * 60 * 60 * 1000)), null);
/*     */               } else {
/*  73 */                 Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/*  74 */                     "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("reach-hack.ban-message"), null, null);
/*     */               } 
/*  76 */               int kickCount2 = 0;
/*  77 */               this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */               
/*  79 */               if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  80 */                 String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  81 */                 String title = this.main.getConfig().getString("webhook.punishments.title");
/*  82 */                 String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Reach Hack");
/*  83 */                 sendWebhook(webhookUrl, title, description);
/*     */               } 
/*     */               try {
/*  86 */                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  87 */                 String date = dateFormat.format(new Date());
/*  88 */                 String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*  89 */                 String nomFichier = "logs-" + date + ".txt";
/*  90 */                 String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*  91 */                 FileWriter writer = new FileWriter(cheminFichier, true);
/*  92 */                 SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*  93 */                 String date2 = dateFormat2.format(new Date());
/*  94 */                 writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("reach-hack.max-violations") + " times.");
/*  95 */                 writer.close();
/*  96 */               } catch (IOException e) {
/*  97 */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */           } else {
/* 101 */             player.kickPlayer("[Oxa AntiCheat] " + this.plugin.getConfig().getString("reach-hack.kick-message"));
/*     */             try {
/* 103 */               SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 104 */               String date = dateFormat.format(new Date());
/* 105 */               String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 106 */               String nomFichier = "logs-" + date + ".txt";
/* 107 */               String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 108 */               FileWriter writer = new FileWriter(cheminFichier, true);
/* 109 */               SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 110 */               String date2 = dateFormat2.format(new Date());
/* 111 */               writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Reach Hack.");
/* 112 */               writer.close();
/* 113 */             } catch (IOException e) {
/* 114 */               e.printStackTrace();
/*     */             } 
/* 116 */             this.reachViolationCounter.put(player.getUniqueId(), Integer.valueOf(violationCount));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 125 */       URL url = new URL(webhookUrl);
/* 126 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 127 */       connection.setRequestMethod("POST");
/* 128 */       connection.setRequestProperty("Content-Type", "application/json");
/* 129 */       connection.setDoOutput(true);
/*     */       
/* 131 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 133 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 134 */         try { byte[] input = json.getBytes("utf-8");
/* 135 */           os.write(input, 0, input.length); }
/* 136 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 141 */       connection.disconnect();
/* 142 */     } catch (Exception e) {
/* 143 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\ReachHack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */