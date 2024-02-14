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
/*     */ public class AntiWalkLave implements Listener {
/*     */   private Main main;
/*  30 */   private ArrayList<Player> onWater = new ArrayList<>();
/*  31 */   private ArrayList<Player> offWater = new ArrayList<>();
/*  32 */   private HashMap<Player, Location> origin = new HashMap<>();
/*  33 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  34 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiWalkLave(Main main) {
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
/*  51 */     Block beside = world.getBlockAt(new Location(world, (location.getBlockX() - 1), (location.getBlockY() - 1), (location.getBlockZ() - 1)));
/*  52 */     Block beside2 = world.getBlockAt(new Location(world, (location.getBlockX() + 1), (location.getBlockY() - 1), (location.getBlockZ() - 1)));
/*  53 */     Block beside3 = world.getBlockAt(new Location(world, (location.getBlockX() - 1), (location.getBlockY() - 1), (location.getBlockZ() + 1)));
/*  54 */     Block beside4 = world.getBlockAt(new Location(world, (location.getBlockX() + 1), (location.getBlockY() - 1), (location.getBlockZ() + 1)));
/*  55 */     Block beside5 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), (location.getBlockZ() - 1)));
/*  56 */     Block beside6 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), (location.getBlockZ() + 1)));
/*  57 */     Block beside7 = world.getBlockAt(new Location(world, (location.getBlockX() - 1), (location.getBlockY() - 1), location.getBlockZ()));
/*  58 */     Block beside8 = world.getBlockAt(new Location(world, (location.getBlockX() + 1), (location.getBlockY() - 1), location.getBlockZ()));
/*  59 */     Block on = world.getBlockAt(new Location(world, location.getBlockX(), location.getBlockY(), location.getBlockZ()));
/*     */     
/*  61 */     if (isOnWater(under, under2, on, location, beside, beside2, beside3, beside4, beside5, beside6, beside7, beside8)) {
/*  62 */       handleOnWater(player, location);
/*     */     } else {
/*  64 */       handleNotOnWater(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
/*  70 */     Player player = event.getPlayer();
/*     */     
/*  72 */     resetAC(player);
/*     */   }
/*     */   
/*     */   private boolean isOnWater(Block under, Block under2, Block on, Location location, Block beside, Block beside2, Block beside3, Block beside4, Block beside5, Block beside6, Block beside7, Block beside8) {
/*  76 */     return ((under.getType() == Material.LEGACY_LAVA || under.getType() == Material.LAVA) && (
/*  77 */       under2.getType() == Material.LEGACY_LAVA || under2.getType() == Material.LAVA) && 
/*  78 */       on.getType() == Material.AIR && 
/*  79 */       location.getY() % 1.0D == 0.0D && ((
/*  80 */       beside.getType() == Material.LAVA && beside2.getType() == Material.LAVA && beside3.getType() == Material.LAVA && beside4.getType() == Material.LAVA && 
/*  81 */       beside5.getType() == Material.LAVA && beside6.getType() == Material.LAVA && beside7.getType() == Material.LAVA && beside8.getType() == Material.LAVA) || (
/*  82 */       beside.getType() == Material.LEGACY_LAVA && beside2.getType() == Material.LEGACY_LAVA && beside3.getType() == Material.LEGACY_LAVA && beside4.getType() == Material.LEGACY_LAVA && 
/*  83 */       beside5.getType() == Material.LEGACY_LAVA && beside6.getType() == Material.LEGACY_LAVA && beside7.getType() == Material.LEGACY_LAVA && beside8.getType() == Material.LEGACY_LAVA)));
/*     */   }
/*     */   
/*     */   private void handleOnWater(Player player, Location location) {
/*  87 */     if (!this.onWater.contains(player)) {
/*  88 */       this.onWater.add(player);
/*  89 */       if (!this.origin.containsKey(player)) {
/*  90 */         this.origin.put(player, location);
/*     */       }
/*  92 */       if (this.main.getConfig().getBoolean("anti-walk-lava.enabled")) {
/*  93 */         checkStillOnWater(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleNotOnWater(Player player) {
/*  99 */     if (this.onWater.contains(player)) {
/* 100 */       this.offWater.add(player);
/* 101 */       this.onWater.remove(player);
/* 102 */       this.origin.remove(player);
/*     */     } else {
/* 104 */       this.offWater.remove(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkStillOnWater(Player player) {
/* 109 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (this.onWater.contains(player) && !this.offWater.contains(player)) {
/*     */             kickJesus(player);
/*     */           }
/* 113 */         }, 15L);
/*     */   }
/*     */   
/*     */   private void kickJesus(Player player) {
/* 117 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (this.onWater.contains(player) && !player.getAllowFlight()) {
/*     */             if (!this.AC.contains(player)) {
/*     */               handleWarningsAndKick(player);
/*     */               this.AC.add(player);
/*     */             } 
/*     */             resetAC(player);
/*     */           } 
/*     */           handleWarningsAndKick(player);
/* 126 */         }, 1L);
/*     */   }
/*     */   
/*     */   private void handleWarningsAndKick(Player player) {
/* 130 */     if (!this.warnings.containsKey(player)) {
/* 131 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/* 133 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/* 134 */       this.warnings.remove(player);
/* 135 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*     */     
/* 138 */     String playerName = player.getName();
/* 139 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/* 140 */     if (player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName)) {
/* 141 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-walk-lava.broadcast"));
/* 142 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-walk-lava.report"));
/* 143 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 144 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/* 145 */       this.main.saveConfig();
/*     */       
/* 147 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/* 148 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/* 149 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/* 150 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Walk on Lava");
/* 151 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/* 154 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 155 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 156 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/* 157 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Walk on Lava");
/* 158 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/*     */       try {
/* 162 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 163 */         String date = dateFormat.format(new Date());
/* 164 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 165 */         String nomFichier = "logs-" + date + ".txt";
/* 166 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 167 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 168 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 169 */         String date2 = dateFormat2.format(new Date());
/* 170 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Walk on the lava.");
/* 171 */         writer.close();
/* 172 */       } catch (IOException e) {
/* 173 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 176 */       if (this.main.getConfig().getInt("anti-walk-lava.max-violations") < kickCount) {
/* 177 */         if (this.main.getConfig().getBoolean("anti-walk-lava.time-ban-enabled")) {
/* 178 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 179 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-walk-lava.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-walk-lava.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 181 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 182 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-walk-lava.ban-message"), null, null);
/*     */         } 
/* 184 */         int kickCount2 = 0;
/* 185 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */         
/* 187 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 188 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 189 */           String title = this.main.getConfig().getString("webhook.punishments.title");
/* 190 */           String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Walk on Lava");
/* 191 */           sendWebhook(webhookUrl, title, description);
/*     */         } 
/*     */         try {
/* 194 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 195 */           String date = dateFormat.format(new Date());
/* 196 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 197 */           String nomFichier = "logs-" + date + ".txt";
/* 198 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 199 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 200 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 201 */           String date2 = dateFormat2.format(new Date());
/* 202 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-walk-lava.max-violations") + " times.");
/* 203 */           writer.close();
/* 204 */         } catch (IOException e) {
/* 205 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 209 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 210 */         if (onlinePlayer.isOp()) {
/* 211 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for walk on lava.");
/*     */         }
/*     */       } 
/*     */       
/* 215 */       player.teleport(this.origin.get(player));
/*     */       
/* 217 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-walk-lava.kick-message"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetAC(Player player) {
/* 222 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {  }, 120L);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 227 */       URL url = new URL(webhookUrl);
/* 228 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 229 */       connection.setRequestMethod("POST");
/* 230 */       connection.setRequestProperty("Content-Type", "application/json");
/* 231 */       connection.setDoOutput(true);
/*     */       
/* 233 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 235 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 236 */         try { byte[] input = json.getBytes("utf-8");
/* 237 */           os.write(input, 0, input.length); }
/* 238 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 243 */       connection.disconnect();
/* 244 */     } catch (Exception e) {
/* 245 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiWalkLave.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */