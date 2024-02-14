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
/*     */ 
/*     */ public class AntiHeadroll implements Listener {
/*     */   private Main main;
/*  26 */   private HashMap<Player, Integer> warnings = new HashMap<>();
/*  27 */   private ArrayList<Player> AC = new ArrayList<>();
/*     */   
/*     */   public AntiHeadroll(Main main) {
/*  30 */     this.main = main;
/*     */   }
/*     */   
/*     */   public void setup(Main _MainAC) {
/*  34 */     this.main = _MainAC;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent e) {
/*  39 */     Player player = e.getPlayer();
/*  40 */     Location loc = player.getLocation();
/*  41 */     boolean exceedPitch = false;
/*     */     
/*  43 */     if (loc.getPitch() > 90.0F) {
/*  44 */       player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), 90.0F));
/*  45 */       exceedPitch = true;
/*  46 */     } else if (loc.getPitch() < -90.0F) {
/*  47 */       player.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), -90.0F));
/*  48 */       exceedPitch = true;
/*     */     } 
/*     */     
/*  51 */     if (exceedPitch && !this.AC.contains(player)) {
/*  52 */       String playerName = player.getName();
/*  53 */       List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/*  54 */       if (player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName)) {
/*  55 */         handleExceedPitch(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleExceedPitch(Player player) {
/*  61 */     player.sendMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-headroll.report"));
/*  62 */     Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-headroll.broadcast"));
/*  63 */     if (!this.warnings.containsKey(player) && this.main.getConfig().getBoolean("anti-headroll.enabled")) {
/*  64 */       this.warnings.put(player, Integer.valueOf(1));
/*     */     } else {
/*  66 */       int curWarnings = ((Integer)this.warnings.get(player)).intValue();
/*  67 */       this.warnings.remove(player);
/*  68 */       this.warnings.put(player, Integer.valueOf(curWarnings + 1));
/*     */     } 
/*     */     
/*  71 */     if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/*  72 */       String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/*  73 */       String title = this.main.getConfig().getString("webhook.alerts.title");
/*  74 */       String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Headroll");
/*  75 */       sendWebhook(webhookUrl, title, description);
/*     */     } 
/*     */     
/*  78 */     if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/*  79 */       String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/*  80 */       String title = this.main.getConfig().getString("webhook.punishments.title");
/*  81 */       String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "headroll");
/*  82 */       sendWebhook(webhookUrl, title, description);
/*     */     } 
/*     */     
/*     */     try {
/*  86 */       SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  87 */       String date = dateFormat.format(new Date());
/*  88 */       String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*  89 */       String nomFichier = "logs-" + date + ".txt";
/*  90 */       String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*  91 */       FileWriter writer = new FileWriter(cheminFichier, true);
/*  92 */       SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*  93 */       String date2 = dateFormat2.format(new Date());
/*  94 */       writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Headroll.");
/*  95 */       writer.close();
/*  96 */     } catch (IOException e) {
/*  97 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 100 */     int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 101 */     this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*     */     
/* 103 */     if (this.main.getConfig().getInt("anti-headroll.max-violations") < kickCount) {
/* 104 */       if (this.main.getConfig().getBoolean("anti-headroll.time-ban-enabled")) {
/* 105 */         Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 106 */             "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-headroll.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-headroll.time-ban") * 60 * 60 * 1000)), null);
/*     */       } else {
/* 108 */         Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 109 */             "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-headroll.ban-message"), null, null);
/*     */       } 
/* 111 */       int kickCount2 = 0;
/* 112 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount2));
/*     */       
/* 114 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 115 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 116 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/* 117 */         String description = this.main.getConfig().getString("webhook.punishments.ban.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Headroll");
/* 118 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       try {
/* 121 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 122 */         String date = dateFormat.format(new Date());
/* 123 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 124 */         String nomFichier = "logs-" + date + ".txt";
/* 125 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 126 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 127 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 128 */         String date2 = dateFormat2.format(new Date());
/* 129 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-headroll.max-violations") + " times.");
/* 130 */         writer.close();
/* 131 */       } catch (IOException e) {
/* 132 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 136 */     for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 137 */       if (onlinePlayer.isOp()) {
/* 138 */         onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r&4the &r" + player.getName() + " &4player was kicked for headroll.");
/*     */       }
/*     */     } 
/*     */     
/* 142 */     player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-headroll.kick-message"));
/* 143 */     this.AC.add(player);
/* 144 */     resetAC(player);
/*     */   }
/*     */   
/*     */   private void resetAC(Player player) {
/* 148 */     Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {  }, 120L);
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 153 */       URL url = new URL(webhookUrl);
/* 154 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 155 */       connection.setRequestMethod("POST");
/* 156 */       connection.setRequestProperty("Content-Type", "application/json");
/* 157 */       connection.setDoOutput(true);
/*     */       
/* 159 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 161 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 162 */         try { byte[] input = json.getBytes("utf-8");
/* 163 */           os.write(input, 0, input.length); }
/* 164 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 169 */       connection.disconnect();
/* 170 */     } catch (Exception e) {
/* 171 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiHeadroll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */