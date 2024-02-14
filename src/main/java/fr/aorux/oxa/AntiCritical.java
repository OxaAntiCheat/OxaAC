/*    */ package fr.aorux.oxa;
/*    */ 
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ 
/*    */ public class AntiCritical implements Listener {
/*    */   private Main main;
/* 17 */   private HashMap<Player, Boolean> isJumping = new HashMap<>();
/*    */   
/*    */   public AntiCritical(Main main) {
/* 20 */     this.main = main;
/*    */   }
/*    */   
/*    */   public void setup(Main _MainAC) {
/* 24 */     this.main = _MainAC;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerMove(PlayerMoveEvent event) {
/* 29 */     Player player = event.getPlayer();
/*    */     
/* 31 */     if (event.getFrom().getY() < event.getTo().getY()) {
/* 32 */       this.isJumping.put(player, Boolean.valueOf(true));
/*    */     } else {
/* 34 */       this.isJumping.put(player, Boolean.valueOf(false));
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onEntityDamage(EntityDamageByEntityEvent event) {
/* 40 */     if (event.getDamager() instanceof Player) {
/* 41 */       Player player = (Player)event.getDamager();
/*    */       
/* 43 */       if (((Boolean)this.isJumping.getOrDefault(player, Boolean.valueOf(false))).booleanValue() && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
/* 44 */         handleAntiCritical(player);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void handleAntiCritical(Player player) {
/* 50 */     if (player.getGameMode() == GameMode.SURVIVAL && this.main.getConfig().getBoolean("anti-critical.enabled")) {
/* 51 */       player.sendMessage("[Oxa AntiCheat] " + this.main.getConfig().getInt("anti-critical.report"));
/*    */       
/* 53 */       int kickCount = this.main.getConfig().getInt("player-kick-count." + player.getUniqueId(), 0) + 1;
/* 54 */       this.main.getConfig().set("player-kick-count." + player.getUniqueId(), Integer.valueOf(kickCount));
/* 55 */       this.main.saveConfig();
/*    */       
/* 57 */       if (this.main.getConfig().getInt("anti-critical.max-violations") < kickCount) {
/* 58 */         if (this.main.getConfig().getBoolean("anti-critical.time-ban-enabled")) {
/* 59 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 60 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-critical.ban-message"), new Date(System.currentTimeMillis() + (this.main.getConfig().getInt("anti-critical.time-ban") * 60 * 60 * 1000)), null);
/*    */         } else {
/* 62 */           Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), 
/* 63 */               "§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-critical.ban-message"), null, null);
/*    */         } 
/*    */       }
/*    */       
/* 67 */       for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
/* 68 */         if (onlinePlayer.isOp()) {
/* 69 */           onlinePlayer.sendMessage("[Oxa AntiCheat] §4the §r" + player.getName() + " §4player was kicked for Critical.");
/*    */         }
/*    */       } 
/*    */       
/* 73 */       player.kickPlayer("[Oxa AntiCheat] " + this.main.getConfig().getString("anti-critical.kick-message"));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiCritical.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */