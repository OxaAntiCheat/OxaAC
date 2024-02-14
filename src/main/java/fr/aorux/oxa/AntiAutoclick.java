/*    */ package fr.aorux.oxa;
/*    */ 
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.Map;
/*    */ import java.util.Queue;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ 
/*    */ public class AntiAutoclick implements Listener {
/* 19 */   private Map<UUID, Queue<Long>> clickTimestamps = new HashMap<>();
/*    */   private Main main;
/*    */   
/*    */   public AntiAutoclick(Main main) {
/* 23 */     this.main = main;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerInteract(PlayerInteractEvent event) {
/* 28 */     Player player = event.getPlayer();
/* 29 */     UUID playerId = player.getUniqueId();
/*    */     
/* 31 */     long currentTimestamp = System.currentTimeMillis();
/*    */     
/* 33 */     if (!this.clickTimestamps.containsKey(playerId)) {
/* 34 */       this.clickTimestamps.put(playerId, new LinkedList<>());
/*    */     }
/*    */     
/* 37 */     Queue<Long> timestampQueue = this.clickTimestamps.get(playerId);
/* 38 */     timestampQueue.add(Long.valueOf(currentTimestamp));
/*    */     
/* 40 */     int maxQueueSize = 60;
/* 41 */     while (timestampQueue.size() > maxQueueSize) {
/* 42 */       timestampQueue.poll();
/*    */     }
/*    */     
/* 45 */     if (isClickFrequencyNormal(timestampQueue) && timestampQueue.size() >= 300 && this.main.getConfig().getBoolean("anti-auto-click.enabled")) {
/* 46 */       player.sendMessage("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-auto-click.report"));
/* 47 */       player.kickPlayer("§d§l[Oxa AntiCheat] §r" + this.main.getConfig().getString("anti-auto-click.kick-message"));
/*    */       
/*    */       try {
/* 50 */         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/* 51 */         String date = dateFormat.format(new Date());
/* 52 */         String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/* 53 */         String nomFichier = "logs-" + date + ".txt";
/* 54 */         String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/* 55 */         FileWriter writer = new FileWriter(cheminFichier, true);
/* 56 */         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
/* 57 */         String date2 = dateFormat2.format(new Date());
/* 58 */         writer.write("\n[" + date2 + "] the player " + player.getName() + " was kicked for using the following cheat: AutoClicker.");
/* 59 */         writer.close();
/* 60 */       } catch (IOException e) {
/* 61 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean isClickFrequencyNormal(Queue<Long> timestampQueue) {
/* 67 */     if (timestampQueue.size() < 2) {
/* 68 */       return true;
/*    */     }
/*    */     
/* 71 */     long firstClick = ((Long)timestampQueue.peek()).longValue();
/* 72 */     long lastClick = ((Long)timestampQueue.peek()).longValue();
/* 73 */     long totalDifference = 0L;
/*    */     
/* 75 */     for (Iterator<Long> iterator = timestampQueue.iterator(); iterator.hasNext(); ) { long timestamp = ((Long)iterator.next()).longValue();
/* 76 */       long difference = timestamp - lastClick;
/* 77 */       totalDifference += difference;
/* 78 */       lastClick = timestamp; }
/*    */ 
/*    */     
/* 81 */     long averageDifference = totalDifference / (timestampQueue.size() - 1);
/*    */     
/* 83 */     long tolerance = 50L;
/*    */     
/* 85 */     return (Math.abs(averageDifference - firstClick) < tolerance);
/*    */   }
/*    */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\AntiAutoclick.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */