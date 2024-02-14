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
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.ArmorStand;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class AntiKillAura implements Listener {
/*  28 */   private Map<UUID, Integer> npcHits = new HashMap<>();
/*     */   private Main main;
/*     */   
/*     */   public AntiKillAura(Main main) {
/*  32 */     this.main = main;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
/*  37 */     if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
/*  38 */       Player attacker = (Player)event.getDamager();
/*  39 */       if (!hasAttackedInvisibleNPC(attacker)) {
/*     */         
/*  41 */         attacker.sendMessage("Your attack frequency or targets are suspicious.");
/*     */         
/*  43 */         if (hasAttackedInvisibleNPC(attacker)) {
/*  44 */           int hits = ((Integer)this.npcHits.getOrDefault(attacker.getUniqueId(), Integer.valueOf(0))).intValue() + 1;
/*  45 */           this.npcHits.put(attacker.getUniqueId(), Integer.valueOf(hits));
/*     */           
/*  47 */           if (hits >= 5) {
/*  48 */             Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + attacker.getName() + this.main.getConfig().getString("anti-killaura.broadcast"));
/*  49 */             String playerName = attacker.getName();
/*  50 */             List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  51 */             if (attacker.getGameMode() == GameMode.SURVIVAL && this.main.getConfig().getBoolean("anti-killaura.enabled") && !whiteList.contains(playerName)) {
/*  52 */               attacker.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-killaura.report"));
/*     */               
/*  54 */               int kickCount = this.main.getConfig().getInt("player-kick-count." + attacker.getUniqueId(), 0) + 1;
/*  55 */               this.main.getConfig().set("player-kick-count." + attacker.getUniqueId(), Integer.valueOf(kickCount));
/*  56 */               this.main.saveConfig();
/*     */               
/*  58 */               if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/*  59 */                 String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/*  60 */                 String title = this.main.getConfig().getString("webhook.alerts.title");
/*  61 */                 String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", attacker.getName()).replace("%cheat_name%", "KillAura");
/*  62 */                 sendWebhook(webhookUrl, title, description);
/*     */               } 
/*     */               
/*  65 */               if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  66 */                 String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  67 */                 String title = this.main.getConfig().getString("webhook.punishments.title");
/*  68 */                 String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", attacker.getName()).replace("%cheat_name%", "KillAura");
/*  69 */                 sendWebhook(webhookUrl, title, description);
/*     */               } 
/*     */               
/*     */               try {
/*  73 */                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  74 */                 String date = dateFormat.format(new Date());
/*  75 */                 String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*  76 */                 String nomFichier = "logs-" + date + ".txt";
/*  77 */                 String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*  78 */                 FileWriter writer = new FileWriter(cheminFichier, true);
/*  79 */                 SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*  80 */                 String date2 = dateFormat2.format(new Date());
/*  81 */                 writer.write("\n[" + date2 + "] the player " + attacker.getName() + " was kicked for using the following cheat: AntiKnockBack");
/*  82 */                 writer.close();
/*  83 */               } catch (IOException e) {
/*  84 */                 e.printStackTrace();
/*     */               } 
/*     */               
/*  87 */               if (this.main.getConfig().getInt("anti-killaura.max-violations") < kickCount) {
/*  88 */                 if (this.main.getConfig().getBoolean("anti-killaura.time-ban-enabled")) {
/*  89 */                   Bukkit.getBanList(BanList.Type.NAME).addBan(attacker.getName(), 
/*  90 */                       "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-killaura.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-killaura.time-ban") * 60 * 60 * 1000)), null);
/*     */                 } else {
/*  92 */                   Bukkit.getBanList(BanList.Type.NAME).addBan(attacker.getName(), 
/*  93 */                       "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-killaura.ban-message"), null, null);
/*     */                 } 
/*  95 */                 int kickCount2 = 0;
/*  96 */                 this.main.getConfig().set("player-kick-count." + attacker.getUniqueId(), Integer.valueOf(kickCount2));
/*     */                 
/*  98 */                 if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  99 */                   String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 100 */                   String title = this.main.getConfig().getString("webhook.punishments.title");
/* 101 */                   String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", attacker.getName()).replace("%cheat_name%", "KillAura");
/* 102 */                   sendWebhook(webhookUrl, title, description);
/*     */                 } 
/*     */                 try {
/* 105 */                   SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 106 */                   String date = dateFormat.format(new Date());
/* 107 */                   String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 108 */                   String nomFichier = "logs-" + date + ".txt";
/* 109 */                   String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 110 */                   FileWriter writer = new FileWriter(cheminFichier, true);
/* 111 */                   SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 112 */                   String date2 = dateFormat2.format(new Date());
/* 113 */                   writer.write("\n[" + date2 + "] the player " + attacker.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-killaura.max-violations") + " times.");
/* 114 */                   writer.close();
/* 115 */                 } catch (IOException e) {
/* 116 */                   e.printStackTrace();
/*     */                 } 
/*     */               } 
/*     */               
/* 120 */               for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 121 */                 if (onlinePlayer.isOp()) {
/* 122 */                   onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + attacker.getName() + " §4player was kicked for Kill Aura.");
/*     */                 }
/*     */               } 
/*     */               
/* 126 */               attacker.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-killaura.kick-message"));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasAttackedInvisibleNPC(Player attacker) {
/* 135 */     spawnInvisibleNPC(attacker.getLocation(), attacker);
/* 136 */     return (attacker.getLastDamageCause() != null && 
/* 137 */       attacker.getLastDamageCause().getEntity() != null && 
/* 138 */       attacker.getLastDamageCause().getEntity().getType() == EntityType.ARMOR_STAND);
/*     */   }
/*     */   
/*     */   public static void spawnInvisibleNPC(Location location, final Player targetPlayer) {
/* 142 */     final ArmorStand invisibleNPC = (ArmorStand)location.getWorld().spawn(location, ArmorStand.class);
/* 143 */     invisibleNPC.setVisible(false);
/* 144 */     invisibleNPC.setSmall(true);
/* 145 */     invisibleNPC.setGravity(false);
/* 146 */     invisibleNPC.setCustomNameVisible(false);
/* 147 */     invisibleNPC.setAI(false);
/* 148 */     invisibleNPC.setCanPickupItems(false);
/* 149 */     invisibleNPC.setCollidable(false);
/* 150 */     invisibleNPC.setBasePlate(false);
/*     */     
/* 152 */     (new BukkitRunnable() {
/* 153 */         int seconds = 10;
/*     */ 
/*     */         
/*     */         public void run() {
/* 157 */           if (this.seconds <= 0 || invisibleNPC.isDead()) {
/* 158 */             invisibleNPC.remove();
/* 159 */             cancel();
/*     */           } else {
/* 161 */             invisibleNPC.teleport(targetPlayer.getLocation());
/*     */           } 
/*     */           
/* 164 */           this.seconds--;
/*     */         }
/* 166 */       }).runTaskTimer((Plugin)Main.getInstance(), 0L, 20L);
/*     */   } public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 170 */       URL url = new URL(webhookUrl);
/* 171 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 172 */       connection.setRequestMethod("POST");
/* 173 */       connection.setRequestProperty("Content-Type", "application/json");
/* 174 */       connection.setDoOutput(true);
/*     */       
/* 176 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 178 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 179 */         try { byte[] input = json.getBytes("utf-8");
/* 180 */           os.write(input, 0, input.length); }
/* 181 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 186 */       connection.disconnect();
/* 187 */     } catch (Exception e) {
/* 188 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiKillAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */