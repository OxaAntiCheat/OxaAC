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
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AntiSpeed
/*     */   implements Listener
/*     */ {
/*     */   private Main main;
/*  41 */   private HashMap<Player, Location> origin = new HashMap<>();
/*  42 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  43 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiSpeed(Main main) {
/*  46 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _MainAC) {
/*  50 */     this.main = _MainAC;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent e, PlayerInteractEvent event) {
/*  55 */     Player player = e.getPlayer();
/*  56 */     Location loc = player.getLocation();
/*  57 */     if (!this.origin.containsKey(player)) {
/*  58 */       this.origin.put(player, loc);
/*     */     }
/*  60 */     detectChange(player, loc, e, event);
/*     */   }
/*     */   
/*     */   public void detectChange(Player player, Location loc, PlayerMoveEvent e, PlayerInteractEvent event) {
/*  64 */     Player player1 = e.getPlayer();
/*  65 */     Vector velocity = player1.getVelocity();
/*  66 */     Location location = player1.getLocation();
/*  67 */     World world = location.getWorld();
/*  68 */     Block under = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()));
/*  69 */     Block under2 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 2), location.getBlockZ()));
/*  70 */     Block under3 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 3), location.getBlockZ()));
/*  71 */     Block on = world.getBlockAt(new Location(world, location.getBlockX(), location.getBlockY(), location.getBlockZ()));
/*     */     
/*  73 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (!player.hasPotionEffect(PotionEffectType.SPEED)) {
/*     */             Location originLocation = this.origin.get(player);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             if (originLocation != null && originLocation.distance(loc) >= this.main.getConfig().getDouble("anti-speed.speed-max")) {
/*     */               if (originLocation.getY() >= loc.getY() - 1.0D && originLocation.getY() <= loc.getY() + 1.0D) {
/*     */                 if (event.getPlayer().isSprinting()) {
/*     */                   if (this.main.getConfig().getBoolean("anti-speed.enabled") && originLocation != null && originLocation.distance(loc) >= this.main.getConfig().getDouble("anti-speed.speed-max-sprint") && this.main.getConfig().getBoolean("anti-speed.enabled") && originLocation != null && originLocation.distance(loc) >= this.main.getConfig().getDouble("anti-speed.speed-max-sprint-and-jump") && (location.getY() < 0.0D || location.getY() > 0.0D)) {
/*     */                     if (isOnIce(under, under2, under3, location, on)) {
/*     */                       if (originLocation.distance(loc) >= this.main.getConfig().getDouble("anti-speed.speed-max-on-ice")) {
/*     */                         kickSpeed(player, event);
/*     */                       }
/*     */                     } else {
/*     */                       kickSpeed(player, event);
/*     */                     } 
/*     */                   }
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/*     */                   if (location.getY() < 0.0D || location.getY() > 0.0D || event.getPlayer().isSprinting()) {
/*     */                     if (isOnIce(under, under2, under3, location, on)) {
/*     */                       if (originLocation.distance(loc) >= this.main.getConfig().getDouble("anti-speed.speed-max-on-ice")) {
/*     */                         kickSpeed(player, event);
/*     */                       }
/*     */                     } else {
/*     */                       kickSpeed(player, event);
/*     */                     } 
/*     */                   }
/*     */                 } else if (this.main.getConfig().getBoolean("anti-speed.enabled")) {
/*     */                   if (isOnIce(under, under2, under3, location, on)) {
/*     */                     if (originLocation.distance(loc) >= this.main.getConfig().getDouble("anti-speed.speed-max-on-ice")) {
/*     */                       kickSpeed(player, event);
/*     */                     }
/*     */                   } else {
/*     */                     kickSpeed(player, event);
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */             } else {
/*     */               this.origin.remove(player);
/*     */             } 
/*     */           } 
/* 120 */         }, 5L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
/* 125 */     Player player = event.getPlayer();
/*     */     
/* 127 */     resetAC(player);
/*     */   }
/*     */   
/*     */   private boolean isOnIce(Block under, Block under2, Block on, Location location, Block under3) {
/* 131 */     return ((under.getType() == Material.ICE || under.getType() == Material.PACKED_ICE || under.getType() == Material.FROSTED_ICE || 
/* 132 */       under2.getType() == Material.ICE || under2.getType() == Material.PACKED_ICE || under2.getType() == Material.FROSTED_ICE || 
/* 133 */       under3.getType() == Material.ICE || under3.getType() == Material.PACKED_ICE || under3.getType() == Material.FROSTED_ICE) && 
/* 134 */       location.getY() % 1.0D == 0.0D);
/*     */   }
/*     */   
/*     */   private void kickSpeed(Player player, PlayerInteractEvent event) {
/* 138 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (!player.getAllowFlight()) {
/*     */             if (!this.AC.contains(player)) {
/*     */               handleWarningsAndKick(player, event);
/*     */               
/*     */               this.AC.add(player);
/*     */               resetAC(player);
/*     */             } 
/*     */             Location originLocation = this.origin.get(player);
/*     */             if (originLocation != null) {
/*     */               handleWarningsAndKick(player, event);
/*     */             }
/*     */           } 
/* 151 */         }, 1L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleWarningsAndKick(Player player, PlayerInteractEvent event) {
/* 156 */     if (!this.warnings.containsKey(player)) {
/* 157 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/* 159 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/* 160 */       this.warnings.remove(player);
/* 161 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*     */     
/* 164 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/* 165 */     Player player1 = event.getPlayer();
/* 166 */     ItemStack item = event.getItem();
/* 167 */     if (item != null && player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(player.getName()) && player.getVehicle() == null && !player.isGliding() && (!(player.getVehicle() instanceof org.bukkit.entity.Boat) || !(player.getVehicle() instanceof org.bukkit.entity.Boat)) && 
/* 168 */       !(player.getVehicle() instanceof org.bukkit.entity.Horse) && !(player.getVehicle() instanceof org.bukkit.entity.Donkey) && !(player.getVehicle() instanceof org.bukkit.entity.Skeleton) && !(player.getVehicle() instanceof org.bukkit.entity.Pig) && !(player.getVehicle() instanceof org.bukkit.entity.Llama)) {
/* 169 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player1.getName() + this.main.getConfig().getString("anti-speed.broadcast"));
/* 170 */       player1.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-speed.report"));
/* 171 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player1.getUniqueId(), 0) + 1;
/* 172 */       this.main.getConfig().set("player-kick-count." + player1.getUniqueId(), Integer.valueOf(kickCount));
/* 173 */       this.main.saveConfig();
/*     */       
/* 175 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/* 176 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/* 177 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/* 178 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player1.getName()).replace("%cheat_name%", "Speed");
/* 179 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/* 182 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 183 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 184 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/* 185 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player1.getName()).replace("%cheat_name%", "Speed");
/* 186 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/* 190 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 191 */         String date = dateFormat.format(new Date());
/* 192 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 193 */         String nomFichier = "logs-" + date + ".txt";
/* 194 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 195 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 196 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 197 */         String date2 = dateFormat2.format(new Date());
/* 198 */         writer.write("\n[" + date2 + "] the player " + player1.getName() + " was kicked for using the following cheat: Speed.");
/* 199 */         writer.close();
/* 200 */       } catch (IOException e) {
/* 201 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 204 */       if (this.main.getConfig().getInt("anti-speed.max-violations") < kickCount) {
/* 205 */         if (this.main.getConfig().getBoolean("anti-speed.time-ban-enabled")) {
/* 206 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player1.getName(), 
/* 207 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-speed.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-speed.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 209 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player1.getName(), 
/* 210 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-speed.ban-message"), null, null);
/*     */         } 
/* 212 */         int kickCount2 = 0;
/* 213 */         this.main.getConfig().set("player-kick-count." + player1.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 215 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 216 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 217 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 218 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player1.getName()).replace("%cheat_name%", "Speed");
/* 219 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 222 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 223 */           String date = dateFormat.format(new Date());
/* 224 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 225 */           String nomFichier = "logs-" + date + ".txt";
/* 226 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 227 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 228 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 229 */           String date2 = dateFormat2.format(new Date());
/* 230 */           writer.write("\n[" + date2 + "] the player " + player1.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-speed.max-violations") + " times.");
/* 231 */           writer.close();
/* 232 */         } catch (IOException e) {
/* 233 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 237 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 238 */         if (onlinePlayer.isOp()) {
/* 239 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player1.getName() + " §4player was kicked for speed.");
/*     */         }
/*     */       } 
/*     */       
/* 243 */       player1.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-speed.kick-message"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetAC(Player player) {
/* 248 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {  }, 120L);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 253 */       URL url = new URL(webhookUrl);
/* 254 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 255 */       connection.setRequestMethod("POST");
/* 256 */       connection.setRequestProperty("Content-Type", "application/json");
/* 257 */       connection.setDoOutput(true);
/*     */       
/* 259 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 261 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 262 */         try { byte[] input = json.getBytes("utf-8");
/* 263 */           os.write(input, 0, input.length); }
/* 264 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 269 */       connection.disconnect();
/* 270 */     } catch (Exception e) {
/* 271 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiSpeed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */