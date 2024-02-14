/*     */ package fr.aorux.oxa;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketAdapter;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.events.PacketListener;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class PacketChecker
/*     */   implements Listener {
/*  27 */   private Set<String> knownCheats = new HashSet<>();
/*     */   private Main main;
/*     */   
/*     */   public PacketChecker(JavaPlugin plugin, Main main) {
/*  31 */     initializeKnownCheats();
/*  32 */     this.main = main;
/*     */     
/*  34 */     ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)plugin, new PacketType[] { PacketType.Play.Client.CUSTOM_PAYLOAD })
/*     */         {
/*     */           public void onPacketReceiving(PacketEvent event) {
/*  37 */             Player player = event.getPlayer();
/*  38 */             String packetName = event.getPacketType().name();
/*  39 */             byte[] packetData = (byte[])event.getPacket().getByteArrays().readSafely(0);
/*     */             
/*  41 */             PacketChecker.this.checkPacket(player, packetName, packetData);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void initializeKnownCheats() {
/*  47 */     this.knownCheats.add("Fullbright");
/*  48 */     this.knownCheats.add("SpeedHack");
/*  49 */     this.knownCheats.add("FlyCheat");
/*  50 */     this.knownCheats.add("Aimbot");
/*  51 */     this.knownCheats.add("X-Ray");
/*  52 */     this.knownCheats.add("KillAura");
/*  53 */     this.knownCheats.add("NoClip");
/*  54 */     this.knownCheats.add("AutoClicker");
/*  55 */     this.knownCheats.add("ReachHack");
/*  56 */     this.knownCheats.add("Criticals");
/*  57 */     this.knownCheats.add("FastBreak");
/*  58 */     this.knownCheats.add("ScaffoldWalk");
/*  59 */     this.knownCheats.add("AntiKB");
/*  60 */     this.knownCheats.add("BunnyHop");
/*  61 */     this.knownCheats.add("ESP");
/*  62 */     this.knownCheats.add("NoFall");
/*  63 */     this.knownCheats.add("ForceField");
/*  64 */     this.knownCheats.add("InventoryHack");
/*  65 */     this.knownCheats.add("AutoEat");
/*  66 */     this.knownCheats.add("JesusWalk");
/*  67 */     this.knownCheats.add("NoSwing");
/*  68 */     this.knownCheats.add("SpeedMine");
/*  69 */     this.knownCheats.add("Spider");
/*  70 */     this.knownCheats.add("NoSlowDown");
/*  71 */     this.knownCheats.add("FastBow");
/*  72 */     this.knownCheats.add("Phase");
/*  73 */     this.knownCheats.add("AutoArmor");
/*  74 */     this.knownCheats.add("AntiVanish");
/*  75 */     this.knownCheats.add("AutoSoup");
/*  76 */     this.knownCheats.add("SprintHack");
/*  77 */     this.knownCheats.add("NoResist");
/*  78 */     this.knownCheats.add("Timer");
/*  79 */     this.knownCheats.add("Derp");
/*  80 */     this.knownCheats.add("AutoFish");
/*  81 */     this.knownCheats.add("FastPlace");
/*  82 */     this.knownCheats.add("AutoSpleef");
/*  83 */     this.knownCheats.add("NoRender");
/*  84 */     this.knownCheats.add("Tracer");
/*  85 */     this.knownCheats.add("Glide");
/*  86 */     this.knownCheats.add("NoWeb");
/*  87 */     this.knownCheats.add("ChestESP");
/*  88 */     this.knownCheats.add("Blink");
/*  89 */     this.knownCheats.add("ForceOp");
/*  90 */     this.knownCheats.add("AutoDisconnect");
/*  91 */     this.knownCheats.add("AntiKnockback");
/*  92 */     this.knownCheats.add("VClip");
/*  93 */     this.knownCheats.add("FastLadder");
/*     */   }
/*     */   
/*     */   public void checkPacket(Player player, String packetName, byte[] packetData) {
/*  97 */     this.main.saveDefaultConfig();
/*  98 */     this.main.reloadConfig();
/*  99 */     String playerName = player.getName();
/* 100 */     List<String> whiteList = this.main.getConfig().getStringList("white-list.player");
/* 101 */     if (whiteList != null && player.getGameMode() == GameMode.SURVIVAL && !whiteList.contains(playerName)) {
/* 102 */       if (this.main.getConfig().getBoolean("webhook.alerts.enabled")) {
/* 103 */         String webhookUrl = this.main.getConfig().getString("webhook.alerts.webhook-url");
/* 104 */         String title = this.main.getConfig().getString("webhook.alerts.title");
/* 105 */         String description = this.main.getConfig().getString("webhook.alerts.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Invalid Packet");
/* 106 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/*     */       
/* 109 */       if (this.main.getConfig().getBoolean("webhook.punishments.enabled")) {
/* 110 */         String webhookUrl = this.main.getConfig().getString("webhook.punishments.webhook-url");
/* 111 */         String title = this.main.getConfig().getString("webhook.punishments.title");
/* 112 */         String description = this.main.getConfig().getString("webhook.punishments.kick.description").replace("%player_username%", player.getName()).replace("%cheat_name%", "Invalid packet");
/* 113 */         sendWebhook(webhookUrl, title, description);
/*     */       } 
/* 115 */       if (this.knownCheats.contains(packetName)) {
/* 116 */         player.kickPlayer("§d§l[Oxa AntiCheat] §rYou were kicked for using a known cheat: " + packetName);
/*     */         try {
/* 118 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 119 */           String date = dateFormat.format(new Date());
/* 120 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 121 */           String nomFichier = "logs-" + date + ".txt";
/* 122 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 123 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 124 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 125 */           String date2 = dateFormat2.format(new Date());
/* 126 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Packet malicious.");
/* 127 */           writer.close();
/* 128 */         } catch (IOException e) {
/* 129 */           e.printStackTrace();
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 134 */       if (containsMaliciousPattern(packetData)) {
/* 135 */         player.kickPlayer("§d§l[Oxa AntiCheat] §rYou were kicked for using a cheat with malicious content.");
/*     */         try {
/* 137 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 138 */           String date = dateFormat.format(new Date());
/* 139 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 140 */           String nomFichier = "logs-" + date + ".txt";
/* 141 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 142 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 143 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 144 */           String date2 = dateFormat2.format(new Date());
/* 145 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Packet malicious.");
/* 146 */           writer.close();
/* 147 */         } catch (IOException e) {
/* 148 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean containsMaliciousPattern(byte[] packetData) {
/* 155 */     if (packetData == null) {
/* 156 */       return false;
/*     */     }
/*     */     
/* 159 */     String packetContent = new String(packetData, StandardCharsets.UTF_8);
/* 160 */     return packetContent.contains("maliciousPattern");
/*     */   }
/*     */   public static void sendWebhook(String webhookUrl, String title, String description) {
/*     */     try {
/*     */       int responseCode;
/* 165 */       URL url = new URL(webhookUrl);
/* 166 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 167 */       connection.setRequestMethod("POST");
/* 168 */       connection.setRequestProperty("Content-Type", "application/json");
/* 169 */       connection.setDoOutput(true);
/*     */       
/* 171 */       String json = "{\"content\":\"\",\"embeds\":[{\"title\":\"" + title + "\",\"description\":\"" + description + "\",\"color\":8388736}]}";
/*     */       
/* 173 */       Exception exception1 = null, exception2 = null; try { OutputStream os = connection.getOutputStream(); 
/* 174 */         try { byte[] input = json.getBytes("utf-8");
/* 175 */           os.write(input, 0, input.length); }
/* 176 */         finally { if (os != null) os.close();  }  } finally { exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */          }
/*     */ 
/*     */ 
/*     */       
/* 181 */       connection.disconnect();
/* 182 */     } catch (Exception e) {
/* 183 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\PacketChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */