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
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerChangedWorldEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class AntiJesus implements Listener {
/*     */   private Main main;
/*  30 */   private ArrayList<Player> onWater = new ArrayList<>();
/*  31 */   private ArrayList<Player> offWater = new ArrayList<>();
/*  32 */   private HashMap<Player, Location> origin = new HashMap<>();
/*  33 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  34 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiJesus(Main main) {
/*  37 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _Main) {
/*  41 */     this.main = _Main;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent e) {
/*  46 */     Player player = e.getPlayer();
/*  47 */     Location location = player.getLocation();
/*  48 */     World world = location.getWorld();
/*  49 */     Block under = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()));
/*  50 */     Block under2 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 2), location.getBlockZ()));
/*  51 */     Block on = world.getBlockAt(new Location(world, location.getBlockX(), location.getBlockY(), location.getBlockZ()));
/*  52 */     Block beside = world.getBlockAt(new Location(world, (location.getBlockX() - 1), (location.getBlockY() - 1), (location.getBlockZ() - 1)));
/*  53 */     Block beside2 = world.getBlockAt(new Location(world, (location.getBlockX() + 1), (location.getBlockY() - 1), (location.getBlockZ() - 1)));
/*  54 */     Block beside3 = world.getBlockAt(new Location(world, (location.getBlockX() - 1), (location.getBlockY() - 1), (location.getBlockZ() + 1)));
/*  55 */     Block beside4 = world.getBlockAt(new Location(world, (location.getBlockX() + 1), (location.getBlockY() - 1), (location.getBlockZ() + 1)));
/*  56 */     Block beside5 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), (location.getBlockZ() - 1)));
/*  57 */     Block beside6 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), (location.getBlockZ() + 1)));
/*  58 */     Block beside7 = world.getBlockAt(new Location(world, (location.getBlockX() - 1), (location.getBlockY() - 1), location.getBlockZ()));
/*  59 */     Block beside8 = world.getBlockAt(new Location(world, (location.getBlockX() + 1), (location.getBlockY() - 1), location.getBlockZ()));
/*     */     
/*  61 */     if (isOnWater(under, under2, on, location, beside, beside2, beside3, beside4, beside5, beside6, beside7, beside8)) {
/*  62 */       handleOnWater(player, location);
/*     */     } else {
/*  64 */       handleNotOnWater(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isOnWater(Block under, Block under2, Block on, Location location, Block beside, Block beside2, Block beside3, Block beside4, Block beside5, Block beside6, Block beside7, Block beside8) {
/*  69 */     return ((under.getType() == Material.LEGACY_WATER || under.getType() == Material.WATER) && (
/*  70 */       under2.getType() == Material.LEGACY_WATER || under2.getType() == Material.WATER) && 
/*  71 */       on.getType() == Material.AIR && 
/*  72 */       location.getY() % 1.0D == 0.0D && ((
/*  73 */       beside.getType() == Material.WATER && beside2.getType() == Material.WATER && beside3.getType() == Material.WATER && beside4.getType() == Material.WATER && 
/*  74 */       beside5.getType() == Material.WATER && beside6.getType() == Material.WATER && beside7.getType() == Material.WATER && beside8.getType() == Material.WATER) || (
/*  75 */       beside.getType() == Material.LEGACY_WATER && beside2.getType() == Material.LEGACY_WATER && beside3.getType() == Material.LEGACY_WATER && beside4.getType() == Material.LEGACY_WATER && 
/*  76 */       beside5.getType() == Material.LEGACY_WATER && beside6.getType() == Material.LEGACY_WATER && beside7.getType() == Material.LEGACY_WATER && beside8.getType() == Material.LEGACY_WATER)));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
/*  81 */     Player player = event.getPlayer();
/*     */     
/*  83 */     resetAC(player);
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleOnWater(Player player, Location location) {
/*  88 */     if (!this.onWater.contains(player)) {
/*  89 */       this.onWater.add(player);
/*  90 */       if (!this.origin.containsKey(player)) {
/*  91 */         this.origin.put(player, location);
/*     */       }
/*  93 */       if (this.main.getConfig().getBoolean("anti-jesus.enabled")) {
/*  94 */         checkStillOnWater(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleNotOnWater(Player player) {
/* 100 */     if (this.onWater.contains(player)) {
/* 101 */       this.offWater.add(player);
/* 102 */       this.onWater.remove(player);
/* 103 */       this.origin.remove(player);
/*     */     } else {
/* 105 */       this.offWater.remove(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkStillOnWater(Player player) {
/* 110 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (this.onWater.contains(player) && !this.offWater.contains(player)) {
/*     */             kickJesus(player);
/*     */           }
/* 114 */         }, 15L);
/*     */   }
/*     */   
/*     */   private void kickJesus(Player player) {
/* 118 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (this.onWater.contains(player) && !player.getAllowFlight()) {
/*     */             if (!this.AC.contains(player)) {
/*     */               handleWarningsAndKick(player);
/*     */               this.AC.add(player);
/*     */             } 
/*     */             resetAC(player);
/*     */           } 
/*     */           handleWarningsAndKick(player);
/* 127 */         }, 1L);
/*     */   }
/*     */   
/*     */   private void handleWarningsAndKick(Player player) {
/* 131 */     if (!this.warnings.containsKey(player)) {
/* 132 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/* 134 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/* 135 */       this.warnings.remove(player);
/* 136 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*     */     
/* 139 */     String playerName = player.getName();
/* 140 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/* 141 */     if (player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName)) {
/* 142 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-jesus.broadcast"));
/* 143 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-jesus.report"));
/* 144 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 145 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/* 146 */       this.main.saveConfig();
/*     */       
/* 148 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/* 149 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/* 150 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/* 151 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Jesus");
/* 152 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/* 155 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 156 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 157 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/* 158 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Jesus");
/* 159 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/* 163 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 164 */         String date = dateFormat.format(new Date());
/* 165 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 166 */         String nomFichier = "logs-" + date + ".txt";
/* 167 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 168 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 169 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 170 */         String date2 = dateFormat2.format(new Date());
/* 171 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Jesus.");
/* 172 */         writer.close();
/* 173 */       } catch (IOException e) {
/* 174 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 177 */       if (this.main.getConfig().getInt("anti-jesus.max-violations") < kickCount) {
/* 178 */         if (this.main.getConfig().getBoolean("anti-jesus.time-ban-enabled")) {
/* 179 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 180 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-jesus.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-jesus.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 182 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 183 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-jesus.ban-message"), null, null);
/*     */         } 
/* 185 */         int kickCount2 = 0;
/* 186 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 188 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 189 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 190 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 191 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Jesus");
/* 192 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         
/*     */         try {
/* 196 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 197 */           String date = dateFormat.format(new Date());
/* 198 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 199 */           String nomFichier = "logs-" + date + ".txt";
/* 200 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 201 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 202 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 203 */           String date2 = dateFormat2.format(new Date());
/* 204 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-jesus.max-violations") + " times.");
/* 205 */           writer.close();
/* 206 */         } catch (IOException e) {
/* 207 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 211 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 212 */         if (onlinePlayer.isOp()) {
/* 213 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for walk on water.");
/*     */         }
/*     */       } 
/*     */       
/* 217 */       player.teleport(this.origin.get(player));
/*     */       
/* 219 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-jesus.kick-message"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetAC(Player player) {
/* 224 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {  }, 120L);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 229 */       URL url = new URL(webhookUrl);
/* 230 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 231 */       connection.setRequestMethod("POST");
/* 232 */       connection.setRequestProperty("Content-Type", "application/json");
/* 233 */       connection.setDoOutput(true);
/*     */       
/* 235 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 237 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 238 */         try { byte[] input = json.getBytes("utf-8");
/* 239 */           os.write(input, 0, input.length); }
/* 240 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 245 */       connection.disconnect();
/* 246 */     } catch (Exception e) {
/* 247 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiJesus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */