/*    */ package fr.aorux.oxa;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class OreManager
/*    */ {
/* 11 */   private final Set<Block> hiddenOreBlocks = new HashSet<>();
/*    */   
/*    */   public void checkAndUpdateOres(Player player) {
/* 14 */     Block playerLocation = player.getLocation().getBlock();
/*    */     
/* 16 */     for (int x = -5; x <= 5; x++) {
/* 17 */       for (int y = -5; y <= 5; y++) {
/* 18 */         for (int z = -5; z <= 5; z++) {
/* 19 */           Block relativeBlock = playerLocation.getRelative(x, y, z);
/* 20 */           if (isHiddenOre(relativeBlock.getType())) {
/* 21 */             this.hiddenOreBlocks.add(relativeBlock);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 26 */     updateOreAppearance(player);
/*    */   }
/*    */   
/*    */   private boolean isHiddenOre(Material material) {
/* 30 */     return (material == Material.STONE);
/*    */   }
/*    */   
/*    */   private void updateOreAppearance(Player player) {
/* 34 */     for (Block hiddenOreBlock : this.hiddenOreBlocks) {
/* 35 */       hiddenOreBlock.setType(Material.STONE);
/*    */     }
/*    */   }
/*    */   
/*    */   public void restoreOreAppearance() {
/* 40 */     for (Block hiddenOreBlock : this.hiddenOreBlocks) {
/* 41 */       hiddenOreBlock.setType(Material.AIR);
/*    */     }
/* 43 */     this.hiddenOreBlocks.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\OreManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */