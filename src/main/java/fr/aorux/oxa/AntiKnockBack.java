/*     */ package fr.aorux.oxa;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.bukkit.BanList;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class AntiKnockBack implements Listener {
/*  18 */   private int knockbackCheckDelay = 5;
/*     */   private Main main;
/*     */   
/*     */   public AntiKnockBack(Main main) {
/*  22 */     this.main = main;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDamage(EntityDamageEvent event) {
/*  27 */     if (event.getEntity() instanceof Player) {
/*  28 */       Player damaged = (Player)event.getEntity();
/*     */       
/*  30 */       if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || 
/*  31 */         event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
/*  32 */         markPlayerDamaged(damaged);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent event) {
/*  40 */     Player player = event.getPlayer();
/*     */     
/*  42 */     if (hasPlayerJustTakenDamage(player)) {
/*  43 */       Bukkit.getScheduler().runTaskLater((Plugin)this.main, () -> checkForKnockback(player), this.knockbackCheckDelay);
/*     */     }
/*     */   }
/*     */   
/*     */   private void markPlayerDamaged(Player player) {
/*  48 */     player.setMetadata("justDamaged", (MetadataValue)new FixedMetadataValue((Plugin)this.main, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   private boolean hasPlayerJustTakenDamage(Player player) {
/*  52 */     if (player.hasMetadata("justDamaged") && ((MetadataValue)player.getMetadata("justDamaged").get(0)).asBoolean()) {
/*  53 */       return true;
/*     */     }
/*  55 */     return false;
/*     */   }
/*     */   
/*     */   private void checkForKnockback(Player player) {
/*  59 */     double startY = player.getLocation().getY();
/*     */     
/*  61 */     Bukkit.getScheduler().runTaskLater((Plugin)this.main, () -> {
/*     */           double endY = player.getLocation().getY();
/*     */           
/*     */           if (endY == endY && player.getGameMode() == GameMode.SURVIVAL && this.main.getConfig().getBoolean("verification-knockback.enabled")) {
/*     */             Bukkit.broadcastMessage("§d§l[Oxa AntiCheat] §r" + player.getName() + this.main.getConfig().getString("verification-knockback.broadcast"));
/*     */             
/*     */             player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-knockback.report"));
/*     */             
/*     */             int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/*     */             this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/*     */             this.main.saveConfig();
/*     */             try {
/*     */               SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*     */               String date = dateFormat.format(new Date());
/*     */               String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*     */               String nomFichier = "logs-" + date + ".txt";
/*     */               String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*     */               FileWriter writer = new FileWriter(cheminFichier, true);
/*     */               SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*     */               String date2 = dateFormat2.format(new Date());
/*     */               writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: AntiKnockBack.");
/*     */               writer.close();
/*  83 */             } catch (IOException e) {
/*     */               e.printStackTrace();
/*     */             } 
/*     */             
/*     */             if (this.main.getConfig().getInt("anti-walk-lava.max-violations") < kickCount) {
/*     */               if (this.main.getConfig().getBoolean("anti-walk-lava.time-ban-enabled")) {
/*     */                 Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-walk-lava.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-walk-lava.time-ban") * 60 * 60 * 1000)), null);
/*     */               } else {
/*     */                 Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-walk-lava.ban-message"), null, null);
/*     */               } 
/*     */               
/*     */               try {
/*     */                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*     */                 
/*     */                 String date = dateFormat.format(new Date());
/*     */                 String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*     */                 String nomFichier = "logs-" + date + ".txt";
/*     */                 String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*     */                 FileWriter writer = new FileWriter(cheminFichier, true);
/*     */                 SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/*     */                 String date2 = dateFormat2.format(new Date());
/*     */                 writer.write("\n[" + date2 + "] the player " + player.getName() + " was banned for cheating more than " + this.main.getConfig().getInt("verification-knockback.max-violations") + " times.");
/*     */                 writer.close();
/* 106 */               } catch (IOException e) {
/*     */                 e.printStackTrace();
/*     */               } 
/*     */             } 
/*     */             
/*     */             for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/*     */               if (onlinePlayer.isOp()) {
/*     */                 onlinePlayer.sendMessage("§d§l[Oxa AntiCheat] §r§4the §r" + player.getName() + " §4player was kicked for anti knockBack.");
/*     */               }
/*     */             } 
/*     */             
/*     */             player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("verification-knockback.kick-message"));
/*     */           } 
/*     */           
/*     */           resetPlayerStatus(player);
/* 121 */         }, 5L);
/*     */   }
/*     */   
/*     */   private void resetPlayerStatus(Player player) {
/* 125 */     player.removeMetadata("justDamaged", (Plugin)this.main);
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiKnockBack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */