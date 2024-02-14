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
/*     */ import java.util.Iterator;
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
/*     */ public class AntiFly implements Listener {
/*  30 */   private ArrayList<Player> inAir = new ArrayList<>(); private Main main;
/*  31 */   private HashMap<Player, Location> origin = new HashMap<>();
/*  32 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  33 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiFly(Main main) {
/*  36 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _MainAC) {
/*  40 */     this.main = _MainAC;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent e) {
/*  45 */     Player player = e.getPlayer();
/*  46 */     Location location = player.getLocation();
/*  47 */     World world = location.getWorld();
/*  48 */     Block under = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()));
/*  49 */     Block under2 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 2), location.getBlockZ()));
/*  50 */     Block under3 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 3), location.getBlockZ()));
/*  51 */     Block under4 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 4), location.getBlockZ()));
/*  52 */     Block under5 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 5), location.getBlockZ()));
/*  53 */     Block under6 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 6), location.getBlockZ()));
/*  54 */     Block under7 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 7), location.getBlockZ()));
/*  55 */     Block under8 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 8), location.getBlockZ()));
/*  56 */     Block under9 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 9), location.getBlockZ()));
/*  57 */     Block under10 = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 10), location.getBlockZ()));
/*  58 */     if (isInAir(under, under2, location)) {
/*  59 */       if (isInSlime(under, under2, under3, under4, under5, under6, under7, under8, under9, under10, location)) {
/*  60 */         handleInAir(player, location);
/*     */       }
/*  62 */     } else if (this.inAir.contains(player) && !player.getLocation().getBlock().getType().isSolid()) {
/*  63 */       this.inAir.remove(player);
/*  64 */       this.origin.remove(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isInAir(Block under, Block under2, Location playerLocation) {
/*  70 */     double playerHeight = playerLocation.getY() - Math.floor(playerLocation.getY());
/*  71 */     return (under.getType() == Material.AIR && under2.getType() == Material.AIR && playerHeight > 0.1D && playerHeight < 0.9D);
/*     */   }
/*     */   
/*     */   private boolean isInSlime(Block under, Block under2, Block under3, Block under4, Block under5, Block under6, Block under7, Block under8, Block under9, Block under10, Location playerLocation) {
/*  75 */     return (under.getType() != Material.SLIME_BLOCK && under2.getType() != Material.SLIME_BLOCK && under3.getType() != Material.SLIME_BLOCK && under4.getType() != Material.SLIME_BLOCK && 
/*  76 */       under5.getType() != Material.SLIME_BLOCK && under6.getType() != Material.SLIME_BLOCK && under7.getType() != Material.SLIME_BLOCK && under8.getType() != Material.SLIME_BLOCK && 
/*  77 */       under9.getType() != Material.SLIME_BLOCK && under10.getType() != Material.SLIME_BLOCK);
/*     */   }
/*     */   
/*     */   private void handleInAir(Player player, Location location) {
/*  81 */     if (!this.inAir.contains(player)) {
/*  82 */       this.inAir.add(player);
/*  83 */       this.origin.put(player, location);
/*     */     } 
/*     */     
/*  86 */     Location playerOrigin = this.origin.get(player);
/*  87 */     if (playerOrigin.distance(location) >= 4.0D && playerOrigin.getY() <= location.getY() && this.main.getConfig().getBoolean("anti-fly.enabled")) {
/*  88 */       kickFly(player, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void kickFly(Player player, PlayerMoveEvent e) {
/*  94 */     Location location = player.getLocation();
/*  95 */     World world = location.getWorld();
/*  96 */     Block under = world.getBlockAt(new Location(world, location.getBlockX(), (location.getBlockY() - 1), location.getBlockZ()));
/*  97 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */           if (this.inAir.contains(player) && !player.getAllowFlight()) {
/*     */             if (!this.AC.contains(player) && !under.getType().isSolid() && under.getType() != Material.LADDER && under.getType() != Material.VINE && under.getType() != Material.LADDER && under.getType() != Material.VINE) {
/*     */               try {
/*     */                 handleWarningsAndKick(player);
/* 102 */               } catch (Throwable e1) {
/*     */                 e1.printStackTrace();
/*     */               } 
/*     */               
/*     */               this.AC.add(player);
/*     */             } 
/*     */             
/*     */             resetAC(player);
/*     */           } 
/*     */           
/*     */           try {
/*     */             handleWarningsAndKick(player);
/* 114 */           } catch (Throwable e1) {
/*     */             
/*     */             e1.printStackTrace();
/*     */           } 
/* 118 */         }, 1L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
/* 123 */     Player player = event.getPlayer();
/*     */     
/* 125 */     resetAC(player);
/*     */   }
/*     */   
/*     */   private void handleWarningsAndKick(Player player) throws Throwable {
/* 129 */     if (!this.warnings.containsKey(player)) {
/* 130 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/* 132 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/* 133 */       this.warnings.remove(player);
/* 134 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*     */     
/* 137 */     String playerName = player.getName();
/* 138 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/* 139 */     if (player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName) && isNearEdge(player)) {
/* 140 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fly.broadcast"));
/* 141 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-fly.report"));
/* 142 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 143 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/* 144 */       this.main.saveConfig();
/*     */ 
/*     */       
/* 147 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/* 148 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/* 149 */         webhookUrl = this.main.getConfig().getString("webhook.alerts.title");
/* 150 */         String date = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Fly");
/* 151 */         sendWebhook(webhookUrl, webhookUrl, date);
/*     */       } 
/*     */       
/* 154 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 155 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 156 */         webhookUrl = this.main.getConfig().getString("webhook.punishments.title");
/* 157 */         String date = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Fly");
/* 158 */         sendWebhook(webhookUrl, webhookUrl, date);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 164 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 165 */         String webhookUrl = dateFormat.format(new Date());
/* 166 */         String date = "plugins/Oxa-AntiCheat/logs/";
/* 167 */         String cheminLogs = "logs-" + webhookUrl + ".txt";
/* 168 */         String nomFichier = String.valueOf(date) + cheminLogs;
/* 169 */         FileWriter writer = new FileWriter(nomFichier, true);
/* 170 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 171 */         String date2 = dateFormat2.format(new Date());
/* 172 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Fly.");
/* 173 */         writer.close();
/* 174 */       } catch (IOException var15) {
/* 175 */         var15.printStackTrace();
/*     */       } 
/*     */       
/* 178 */       if (this.main.getConfig().getInt("anti-fly.max-violations") < kickCount) {
/* 179 */         if (this.main.getConfig().getBoolean("anti-fly.time-ban-enabled")) {
/* 180 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fly.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-fly.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/* 182 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fly.ban-message"), null, null);
/*     */         } 
/*     */         
/* 185 */         int kickCount2 = 0;
/* 186 */         this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/* 187 */         if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 188 */           String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 189 */           String date = this.main.getConfig().getString("webhook.punishments.title");
/* 190 */           String cheminLogs = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Fly");
/* 191 */           sendWebhook(webhookUrl, date, cheminLogs);
/*     */         } 
/*     */         
/*     */         try {
/* 195 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 196 */           String date = dateFormat.format(new Date());
/* 197 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 198 */           String nomFichier = "logs-" + date + ".txt";
/* 199 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 200 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 201 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 202 */           String date2 = dateFormat2.format(new Date());
/* 203 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-fly.max-violations") + " times.");
/* 204 */           writer.close();
/* 205 */         } catch (IOException var14) {
/* 206 */           var14.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 210 */       Iterator<? extends Player> var21 = Bukkit.getOnlinePlayers().iterator();
/*     */       
/* 212 */       while (var21.hasNext()) {
/* 213 */         Player onlinePlayer = var21.next();
/* 214 */         if (onlinePlayer.isOp()) {
/* 215 */           onlinePlayer.sendMessage("[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for fly.");
/*     */         }
/*     */       } 
/*     */       
/* 219 */       player.teleport(this.origin.get(player));
/* 220 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-fly.kick-message"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetAC(Player player) {
/* 226 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> this.AC.remove(player), 
/*     */         
/* 228 */         120L);
/*     */   }
/*     */   
/*     */   private boolean isNearEdge(Player player) {
/* 232 */     double thresholdX = 0.2D;
/* 233 */     double thresholdZ = 0.2D;
/*     */     
/* 235 */     double playerX = player.getLocation().getX();
/* 236 */     double playerY = player.getLocation().getY();
/* 237 */     double playerZ = player.getLocation().getZ();
/*     */     
/* 239 */     return (player.getWorld().getBlockAt((int)playerX, (int)(playerY - 1.0D), (int)playerZ).isEmpty() && (
/* 240 */       playerX % 1.0D < thresholdX || playerX % 1.0D > 1.0D - thresholdX) && (
/* 241 */       playerZ % 1.0D < thresholdZ || playerZ % 1.0D > 1.0D - thresholdZ));
/*     */   }
/*     */   
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) throws Throwable {
/*     */     try {
/* 246 */       URL url = new URL(webhookUrl);
/* 247 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 248 */       connection.setRequestMethod("POST");
/* 249 */       connection.setRequestProperty("Content-Type", "application/json");
/* 250 */       connection.setDoOutput(true);
/* 251 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/* 252 */       Throwable var6 = null;
/*     */       try {
/* 254 */         OutputStream os = connection.getOutputStream();
/*     */         
/*     */         try {
/* 257 */           byte[] input = json.getBytes("utf-8");
/* 258 */           os.write(input, 0, input.length);
/*     */         } finally {
/* 260 */           if (os != null) {
/* 261 */             os.close();
/*     */           }
/*     */         }
/*     */       
/* 265 */       } catch (Throwable var17) {
/* 266 */         var6 = var17;
/*     */         
/* 268 */         throw var6;
/*     */       } 
/*     */       
/* 271 */       int responseCode = connection.getResponseCode();
/* 272 */       System.out.println("Webhook response code: " + responseCode);
/* 273 */       connection.disconnect();
/* 274 */     } catch (Exception var18) {
/* 275 */       var18.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiFly.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */