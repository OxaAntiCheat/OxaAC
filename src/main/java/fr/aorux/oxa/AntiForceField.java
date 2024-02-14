/*     */ package fr.aorux.oxa;
/*     */ 
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerAnimationEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ 
/*     */ public class AntiForceField
/*     */   implements Listener
/*     */ {
/*     */   private static final int MAX_ARM_ANIMATION_PACKETS = 8;
/*     */   private static final int MAX_USE_ENTITY_PACKETS = 21;
/*  23 */   private final Map<Player, Integer> armAnimationPacketsCount = new HashMap<>();
/*  24 */   private final Map<Player, Integer> useEntityPacketsCount = new HashMap<>();
/*     */   private Main main;
/*     */   
/*     */   public AntiForceField(Main main) {
/*  28 */     this.main = main;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onArmAnimation(PlayerAnimationEvent event) {
/*  33 */     Player player = event.getPlayer();
/*     */     
/*  35 */     this.armAnimationPacketsCount.put(player, Integer.valueOf(((Integer)this.armAnimationPacketsCount.getOrDefault(player, Integer.valueOf(0))).intValue() + 1));
/*     */     
/*  37 */     if (((Integer)this.armAnimationPacketsCount.get(player)).intValue() > 8 && 
/*  38 */       this.main.getConfig().getBoolean("anti-force-field.enabled") && 
/*  39 */       player.getGameMode() == GameMode.SURVIVAL) {
/*  40 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-force-field.broadcast"));
/*  41 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-force-field.report"));
/*     */       
/*  43 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/*  44 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*  45 */       this.main.saveConfig();
/*     */       
/*     */       try {
/*  48 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  49 */         String date = dateFormat.format(new Date());
/*  50 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*  51 */         String nomFichier = "logs-" + date + ".txt";
/*  52 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*  53 */         FileWriter writer = new FileWriter(cheminFichier, true);
/*  54 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*  55 */         String date2 = dateFormat2.format(new Date());
/*  56 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Force Field.");
/*  57 */         writer.close();
/*  58 */       } catch (IOException e) {
/*  59 */         e.printStackTrace();
/*     */       } 
/*     */       
/*  62 */       if (this.main.getConfig().getInt("anti-force-field.max-violations") < kickCount) {
/*  63 */         if (this.main.getConfig().getBoolean("anti-force-field.time-ban-enabled")) {
/*  64 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/*  65 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-force-field.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-force-field.time-ban") * 60 * 60 * 1000)), null);
/*     */         } else {
/*  67 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/*  68 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-force-field.ban-message"), null, null);
/*     */         } 
/*     */         try {
/*  71 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  72 */           String date = dateFormat.format(new Date());
/*  73 */           String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*  74 */           String nomFichier = "logs-" + date + ".txt";
/*  75 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*  76 */           FileWriter writer = new FileWriter(cheminFichier, true);
/*  77 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*  78 */           String date2 = dateFormat2.format(new Date());
/*  79 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-force-field.max-violations") + " times.");
/*  80 */           writer.close();
/*  81 */         } catch (IOException e) {
/*  82 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/*  86 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/*  87 */         if (onlinePlayer.isOp()) {
/*  88 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked anti-force-field.");
/*     */         }
/*     */       } 
/*     */       
/*  92 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-force-field.kick-message"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUseEntity(PlayerInteractEntityEvent event) {
/* 100 */     Player player = event.getPlayer();
/*     */     
/* 102 */     this.useEntityPacketsCount.put(player, Integer.valueOf(((Integer)this.useEntityPacketsCount.getOrDefault(player, Integer.valueOf(0))).intValue() + 1));
/*     */     
/* 104 */     if (((Integer)this.useEntityPacketsCount.get(player)).intValue() > 21 && 
/* 105 */       this.main.getConfig().getBoolean("anti-force-field.enabled") && 
/* 106 */       player.getGameMode() == GameMode.SURVIVAL) {
/* 107 */       Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("anti-force-field.broadcast"));
/* 108 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-force-field.report"));
/*     */       
/* 110 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 111 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/* 112 */       this.main.saveConfig();
/*     */       
/*     */       try {
/* 115 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 116 */         String date = dateFormat.format(new Date());
/* 117 */         String cheminLogs = "plugins/Oxa AntiCheat/logs/";
/* 118 */         String nomFichier = "logs-" + date + ".txt";
/* 119 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 120 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 121 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 122 */         String date2 = dateFormat2.format(new Date());
/* 123 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: Force Field.");
/* 124 */         writer.close();
/* 125 */       } catch (IOException e) {
/* 126 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 129 */       if (this.main.getConfig().getInt("anti-force-field.max-violations") < kickCount) {
/* 130 */         Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 131 */             "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-force-field.ban-message"), null, null);
/*     */         try {
/* 133 */           SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 134 */           String date = dateFormat.format(new Date());
/* 135 */           String cheminLogs = "plugins/Oxa AntiCheat/logs/";
/* 136 */           String nomFichier = "logs-" + date + ".txt";
/* 137 */           String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 138 */           FileWriter writer = new FileWriter(cheminFichier, true);
/* 139 */           SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 140 */           String date2 = dateFormat2.format(new Date());
/* 141 */           writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("anti-force-field.max-violations") + " times.");
/* 142 */           writer.close();
/* 143 */         } catch (IOException e) {
/* 144 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/* 148 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 149 */         if (onlinePlayer.isOp()) {
/* 150 */           onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for use Force Field.");
/*     */         }
/*     */       } 
/*     */       
/* 154 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-force-field.kick-message"));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiForceField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */