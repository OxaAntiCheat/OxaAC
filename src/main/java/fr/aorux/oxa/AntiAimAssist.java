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
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerInteractAtEntityEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ 
/*     */ public class AntiAimAssist
/*     */   implements Listener {
/*     */   private Main main;
/*  30 */   private HashMap<Player, Long> lastAttackTime = new HashMap<>();
/*  31 */   private HashMap<Player, Location> lastAttackLocation = new HashMap<>();
/*  32 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  33 */   private HashMap<Player, Integer> consecutiveAttacks = new HashMap<>();
/*  34 */   private final int timeThreshold = 5000;
/*  35 */   private final int consecutiveThreshold = 5;
/*     */   
/*     */   public AntiAimAssist(Main main) {
/*  38 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _Main) {
/*  42 */     this.main = _Main;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent e) {
/*  47 */     Player player = e.getPlayer();
/*  48 */     Location location = player.getLocation();
/*  49 */     World world = location.getWorld();
/*  50 */     Block block = world.getBlockAt(location);
/*  51 */     Entity entity = getNearestEntity(player);
/*     */     
/*  53 */     if (entity != null && block.getType() != Material.AIR) {
/*  54 */       checkAttack(player, location);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
/*  60 */     Player player = e.getPlayer();
/*  61 */     Location location = player.getLocation();
/*  62 */     checkAttack(player, location);
/*     */   }
/*     */   
/*     */   private void checkAttack(Player player, Location location) {
/*  66 */     if (this.lastAttackTime.containsKey(player) && this.lastAttackLocation.containsKey(player)) {
/*  67 */       long currentTime = System.currentTimeMillis();
/*  68 */       long lastAttack = ((Long)this.lastAttackTime.get(player)).longValue();
/*  69 */       Location lastAttackLoc = this.lastAttackLocation.get(player);
/*     */       
/*  71 */       if (currentTime - lastAttack < 5000L && location.distanceSquared(lastAttackLoc) < 2.0D) {
/*  72 */         this.consecutiveAttacks.put(player, Integer.valueOf(((Integer)this.consecutiveAttacks.getOrDefault(player, Integer.valueOf(0))).intValue() + 1));
/*     */         
/*  74 */         if (((Integer)this.consecutiveAttacks.get(player)).intValue() >= 5) {
/*  75 */           handleWarningsAndKick(player);
/*     */         }
/*     */       } else {
/*  78 */         this.consecutiveAttacks.put(player, Integer.valueOf(0));
/*     */       } 
/*     */     } 
/*  81 */     this.lastAttackTime.put(player, Long.valueOf(System.currentTimeMillis()));
/*  82 */     this.lastAttackLocation.put(player, location);
/*     */   }
/*     */   
/*     */   private Entity getNearestEntity(Player player) {
/*  86 */     Entity target = null;
/*  87 */     double closestDistance = Double.MAX_VALUE;
/*     */     
/*  89 */     for (Entity entity : player.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
/*  90 */       if (entity.getType() == EntityType.PLAYER || entity.getType() == EntityType.ARMOR_STAND) {
/*  91 */         double distance = player.getLocation().distanceSquared(entity.getLocation());
/*     */         
/*  93 */         if (distance < closestDistance) {
/*  94 */           closestDistance = distance;
/*  95 */           target = entity;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return target;
/*     */   }
/*     */   
/*     */   private void handleWarningsAndKick(Player player) {
/* 104 */     if (!this.warnings.containsKey(player)) {
/* 105 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/* 107 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/* 108 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*     */     
/* 111 */     String playerName = player.getName();
/* 112 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/* 113 */     if (player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName)) {
/* 114 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-aimassist.broadcast"));
/* 115 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-aimassist.report"));
/* 116 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 117 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/* 118 */       this.main.saveConfig();
/*     */       
/* 120 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/* 121 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/* 122 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/* 123 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "AimAssist");
/* 124 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/* 127 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 128 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 129 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/* 130 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "AimAssist");
/* 131 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/* 135 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 136 */         String date = dateFormat.format(new Date());
/* 137 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 138 */         String nomFichier = "logs-" + date + ".txt";
/* 139 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 140 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 141 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 142 */         String date2 = dateFormat2.format(new Date());
/* 143 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: AimAssist.");
/* 144 */         writer.close();
/* 145 */       } catch (IOException e) {
/* 146 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 149 */       if (this.main.getConfig().getInt("anti-aimassist.max-violations") < kickCount) {
/* 150 */         if (this.main.getConfig().getBoolean("anti-aimassist.time-ban-enabled")) {
/* 151 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 152 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-aimassist.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-aimassist.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 154 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 155 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-aimassist.ban-message"), null, null);
/*     */         } 
/* 157 */         int kickCount2 = 0;
/* 158 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 160 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 161 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 162 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 163 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "AimAssist");
/* 164 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 167 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 168 */           String date = dateFormat.format(new Date());
/* 169 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 170 */           String nomFichier = "logs-" + date + ".txt";
/* 171 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 172 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 173 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 174 */           String date2 = dateFormat2.format(new Date());
/* 175 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-aimassist.max-violations") + " times.");
/* 176 */           writer.close();
/* 177 */         } catch (IOException e) {
/* 178 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 182 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 183 */         if (onlinePlayer.isOp()) {
/* 184 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4The §r" + player.getName() + 
/* 185 */               " §4player was kicked for potential KillAura.");
/*     */         }
/*     */       } 
/*     */       
/* 189 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-killaura.kick-message"));
/*     */     } 
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 195 */       URL url = new URL(webhookUrl);
/* 196 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 197 */       connection.setRequestMethod("POST");
/* 198 */       connection.setRequestProperty("Content-Type", "application/json");
/* 199 */       connection.setDoOutput(true);
/*     */       
/* 201 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 203 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 204 */         try { byte[] input = json.getBytes("utf-8");
/* 205 */           os.write(input, 0, input.length); }
/* 206 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 211 */       connection.disconnect();
/* 212 */     } catch (Exception e) {
/* 213 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiAimAssist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */