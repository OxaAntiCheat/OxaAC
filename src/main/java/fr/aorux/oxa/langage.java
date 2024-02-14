/*     */ package fr.aorux.oxa;
/*     */ 
/*     */ public class langage
/*     */ {
/*     */   private Main main;
/*     */   
/*     */   public langage(Main main) {
/*   8 */     this.main = main;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void langage_change() {
/*  18 */     String en_gb = "en_gb";
/*  19 */     String en_us = "en_us";
/*  20 */     String fr = "fr";
/*  21 */     String es = "es";
/*  22 */     String de = "de";
/*  23 */     String it = "it";
/*  24 */     if (this.main.getConfig().getString("langage") != this.main.getConfig().getString("config.lang"))
/*     */     {
/*     */       
/*  27 */       if (this.main.getConfig().getString("config.lang") == en_gb) {
/*  28 */         String report = this.main.getConfig().getString("It looks like you are using a cheat that allows you to sprint and sneak. You will be a kicker and report to the staff.");
/*  29 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", report);
/*  30 */         String broadcast = this.main.getConfig().getString(" is using a cheat sprint and sneak.");
/*  31 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", broadcast);
/*  32 */         String kick_message = this.main.getConfig().getString("You were kicked for cheat.");
/*  33 */         this.main.getConfig().set("anti-sneak-sprint.kick-message", kick_message);
/*  34 */         String ban_message = this.main.getConfig().getString("You have been banned for Cheat.");
/*  35 */         this.main.getConfig().set("anti-sneak-sprint.ban-message", ban_message);
/*     */         
/*  37 */         String report1 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to anti potion. You will be a kicker and report to the staff.");
/*  38 */         this.main.getConfig().set("verification-potion.broadcast", report1);
/*  39 */         String broadcast1 = this.main.getConfig().getString(" is using a cheat anti potion.");
/*  40 */         this.main.getConfig().set("verification-potion.broadcast", broadcast1);
/*  41 */         String kick_message1 = this.main.getConfig().getString("You were kicked for cheat.");
/*  42 */         this.main.getConfig().set("verification-potion.kick-message", kick_message1);
/*  43 */         String ban_message1 = this.main.getConfig().getString("You have been banned for Cheat.");
/*  44 */         this.main.getConfig().set("verification-potion.ban-message", ban_message1);
/*     */         
/*  46 */         String report2 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to anti fall. You will be a kicker and report to the staff.");
/*  47 */         this.main.getConfig().set("verification-fall.broadcast", report2);
/*  48 */         String broadcast2 = this.main.getConfig().getString(" is using a cheat anti fall.");
/*  49 */         this.main.getConfig().set("verification-fall.broadcast", broadcast2);
/*  50 */         String kick_message2 = this.main.getConfig().getString("You were kicked for cheat.");
/*  51 */         this.main.getConfig().set("verification-fall.kick-message", kick_message2);
/*  52 */         String ban_message2 = this.main.getConfig().getString("You have been banned for Cheat.");
/*  53 */         this.main.getConfig().set("verification-fall.ban-message", ban_message2);
/*     */         
/*  55 */         String report3 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to anti knock back. You will be a kicker and report to the staff.");
/*  56 */         this.main.getConfig().set("verification-knockback.broadcast", report3);
/*  57 */         String broadcast3 = this.main.getConfig().getString(" is using a cheat anti knock back.");
/*  58 */         this.main.getConfig().set("verification-knockback.broadcast", broadcast3);
/*  59 */         String kick_message3 = this.main.getConfig().getString("You were kicked for cheat.");
/*  60 */         this.main.getConfig().set("verification-knockback.kick-message", kick_message3);
/*  61 */         String ban_message3 = this.main.getConfig().getString("You have been banned for Cheat.");
/*  62 */         this.main.getConfig().set("verification-knockback.ban-message", ban_message3);
/*     */         
/*  64 */         String report4 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to kill aura. You will be a kicker and report to the staff.");
/*  65 */         this.main.getConfig().set("anti-killaura.broadcast", report4);
/*  66 */         String broadcast4 = this.main.getConfig().getString(" is using a cheat kill aura.");
/*  67 */         this.main.getConfig().set("anti-killaura.broadcast", broadcast4);
/*  68 */         String kick_message4 = this.main.getConfig().getString("You were kicked for cheat.");
/*  69 */         this.main.getConfig().set("anti-killaura.kick-message", kick_message4);
/*  70 */         String ban_message4 = this.main.getConfig().getString("You have been banned for Cheat.");
/*  71 */         this.main.getConfig().set("anti-killaura.ban-message", ban_message4);
/*     */         
/*  73 */         String report5 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to aimassist. You will be a kicker and report to the staff.");
/*  74 */         this.main.getConfig().set("anti-aimassist.broadcast", report5);
/*  75 */         String broadcast5 = this.main.getConfig().getString(" is using a cheat aimassist.");
/*  76 */         this.main.getConfig().set("anti-aimassist.broadcast", broadcast5);
/*  77 */         String kick_message5 = this.main.getConfig().getString("You were kicked for cheat.");
/*  78 */         this.main.getConfig().set("anti-aimassist.kick-message", kick_message5);
/*  79 */         String ban_message5 = this.main.getConfig().getString("You have been banned for Cheat.");
/*  80 */         this.main.getConfig().set("anti-aimassist.ban-message", ban_message5);
/*     */         
/*  82 */         String report6 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to speed. You will be a kicker and report to the staff.");
/*  83 */         this.main.getConfig().set("anti-speed.broadcast", report6);
/*  84 */         String broadcast6 = this.main.getConfig().getString(" is using a cheat speed.");
/*  85 */         this.main.getConfig().set("anti-speed.broadcast", broadcast6);
/*  86 */         String kick_message6 = this.main.getConfig().getString("You were kicked for cheat.");
/*  87 */         this.main.getConfig().set("anti-speed.kick-message", kick_message6);
/*  88 */         String ban_message6 = this.main.getConfig().getString("You have been banned for Cheat.");
/*  89 */         this.main.getConfig().set("anti-speed.ban-message", ban_message6);
/*     */         
/*  91 */         String report7 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to jesus. You will be a kicker and report to the staff.");
/*  92 */         this.main.getConfig().set("anti-jesus.broadcast", report7);
/*  93 */         String broadcast7 = this.main.getConfig().getString(" is using a cheat jesus.");
/*  94 */         this.main.getConfig().set("anti-jesus.broadcast", broadcast7);
/*  95 */         String kick_message7 = this.main.getConfig().getString("You were kicked for cheat.");
/*  96 */         this.main.getConfig().set("anti-jesus.kick-message", kick_message7);
/*  97 */         String ban_message7 = this.main.getConfig().getString("You have been banned for Cheat.");
/*  98 */         this.main.getConfig().set("anti-jesus.ban-message", ban_message7);
/*     */         
/* 100 */         String report8 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to walk on the lava. You will be a kicker and report to the staff.");
/* 101 */         this.main.getConfig().set("anti-walk-lava.broadcast", report8);
/* 102 */         String broadcast8 = this.main.getConfig().getString(" is using a cheat walk on the lava.");
/* 103 */         this.main.getConfig().set("anti-walk-lava.broadcast", broadcast8);
/* 104 */         String kick_message8 = this.main.getConfig().getString("You were kicked for cheat.");
/* 105 */         this.main.getConfig().set("anti-walk-lava.kick-message", kick_message8);
/* 106 */         String ban_message8 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 107 */         this.main.getConfig().set("anti-walk-lava.ban-message", ban_message8);
/*     */         
/* 109 */         String report9 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to fast eat. You will be a kicker and report to the staff.");
/* 110 */         this.main.getConfig().set("anti-fast-eat.broadcast", report9);
/* 111 */         String broadcast9 = this.main.getConfig().getString(" is using a cheat fast eat.");
/* 112 */         this.main.getConfig().set("anti-fast-eat.broadcast", broadcast9);
/* 113 */         String kick_message9 = this.main.getConfig().getString("You were kicked for cheat.");
/* 114 */         this.main.getConfig().set("anti-fast-eat.kick-message", kick_message9);
/* 115 */         String ban_message9 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 116 */         this.main.getConfig().set("anti-fast-eat.ban-message", ban_message9);
/*     */         
/* 118 */         String report10 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to fast bow. You will be a kicker and report to the staff.");
/* 119 */         this.main.getConfig().set("anti-fast-bow.broadcast", report10);
/* 120 */         String broadcast10 = this.main.getConfig().getString(" is using a cheat fast bow.");
/* 121 */         this.main.getConfig().set("anti-fast-bow.broadcast", broadcast10);
/* 122 */         String kick_message10 = this.main.getConfig().getString("You were kicked for cheat.");
/* 123 */         this.main.getConfig().set("anti-fast-bow.kick-message", kick_message10);
/* 124 */         String ban_message10 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 125 */         this.main.getConfig().set("anti-fast-bow.ban-message", ban_message10);
/*     */         
/* 127 */         String report11 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to fly. You will be a kicker and report to the staff.");
/* 128 */         this.main.getConfig().set("anti-fly.broadcast", report11);
/* 129 */         String broadcast11 = this.main.getConfig().getString(" is using a cheat fly.");
/* 130 */         this.main.getConfig().set("anti-fly.broadcast", broadcast11);
/* 131 */         String kick_message11 = this.main.getConfig().getString("You were kicked for cheat.");
/* 132 */         this.main.getConfig().set("anti-fly.kick-message", kick_message11);
/* 133 */         String ban_message11 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 134 */         this.main.getConfig().set("anti-fly.ban-message", ban_message11);
/*     */         
/* 136 */         String report12 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to headroll. You will be a kicker and report to the staff.");
/* 137 */         this.main.getConfig().set("anti-headroll.broadcast", report12);
/* 138 */         String broadcast12 = this.main.getConfig().getString(" is using a cheat headroll.");
/* 139 */         this.main.getConfig().set("anti-headroll.broadcast", broadcast12);
/* 140 */         String kick_message12 = this.main.getConfig().getString("You were kicked for cheat.");
/* 141 */         this.main.getConfig().set("anti-headroll.kick-message", kick_message12);
/* 142 */         String ban_message12 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 143 */         this.main.getConfig().set("anti-headroll.ban-message", ban_message12);
/*     */         
/* 145 */         String report13 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to reach hack. You will be a kicker and report to the staff.");
/* 146 */         this.main.getConfig().set("reach-hack.broadcast", report13);
/* 147 */         String broadcast13 = this.main.getConfig().getString(" is using a cheat reach hack.");
/* 148 */         this.main.getConfig().set("reach-hack.broadcast", broadcast13);
/* 149 */         String kick_message13 = this.main.getConfig().getString("You were kicked for cheat.");
/* 150 */         this.main.getConfig().set("reach-hack.kick-message", kick_message13);
/* 151 */         String ban_message13 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 152 */         this.main.getConfig().set("reach-hack.ban-message", ban_message13);
/* 153 */       } else if (this.main.getConfig().getString("config.lang") == en_us) {
/* 154 */         String report = this.main.getConfig().getString("It looks like you are using a cheat that allows you to sprint and sneak. You will be a kicker and report to the staff.");
/* 155 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", report);
/* 156 */         String broadcast = this.main.getConfig().getString(" is using a cheat sprint and sneak.");
/* 157 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", broadcast);
/* 158 */         String kick_message = this.main.getConfig().getString("You were kicked for cheat.");
/* 159 */         this.main.getConfig().set("anti-sneak-sprint.kick-message", kick_message);
/* 160 */         String ban_message = this.main.getConfig().getString("You have been banned for Cheat.");
/* 161 */         this.main.getConfig().set("anti-sneak-sprint.ban-message", ban_message);
/*     */         
/* 163 */         String report1 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to anti potion. You will be a kicker and report to the staff.");
/* 164 */         this.main.getConfig().set("verification-potion.broadcast", report1);
/* 165 */         String broadcast1 = this.main.getConfig().getString(" is using a cheat anti potion.");
/* 166 */         this.main.getConfig().set("verification-potion.broadcast", broadcast1);
/* 167 */         String kick_message1 = this.main.getConfig().getString("You were kicked for cheat.");
/* 168 */         this.main.getConfig().set("verification-potion.kick-message", kick_message1);
/* 169 */         String ban_message1 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 170 */         this.main.getConfig().set("verification-potion.ban-message", ban_message1);
/*     */         
/* 172 */         String report2 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to anti fall. You will be a kicker and report to the staff.");
/* 173 */         this.main.getConfig().set("verification-fall.broadcast", report2);
/* 174 */         String broadcast2 = this.main.getConfig().getString(" is using a cheat anti fall.");
/* 175 */         this.main.getConfig().set("verification-fall.broadcast", broadcast2);
/* 176 */         String kick_message2 = this.main.getConfig().getString("You were kicked for cheat.");
/* 177 */         this.main.getConfig().set("verification-fall.kick-message", kick_message2);
/* 178 */         String ban_message2 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 179 */         this.main.getConfig().set("verification-fall.ban-message", ban_message2);
/*     */         
/* 181 */         String report3 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to anti knock back. You will be a kicker and report to the staff.");
/* 182 */         this.main.getConfig().set("verification-knockback.broadcast", report3);
/* 183 */         String broadcast3 = this.main.getConfig().getString(" is using a cheat anti knock back.");
/* 184 */         this.main.getConfig().set("verification-knockback.broadcast", broadcast3);
/* 185 */         String kick_message3 = this.main.getConfig().getString("You were kicked for cheat.");
/* 186 */         this.main.getConfig().set("verification-knockback.kick-message", kick_message3);
/* 187 */         String ban_message3 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 188 */         this.main.getConfig().set("verification-knockback.ban-message", ban_message3);
/*     */         
/* 190 */         String report4 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to kill aura. You will be a kicker and report to the staff.");
/* 191 */         this.main.getConfig().set("anti-killaura.broadcast", report4);
/* 192 */         String broadcast4 = this.main.getConfig().getString(" is using a cheat kill aura.");
/* 193 */         this.main.getConfig().set("anti-killaura.broadcast", broadcast4);
/* 194 */         String kick_message4 = this.main.getConfig().getString("You were kicked for cheat.");
/* 195 */         this.main.getConfig().set("anti-killaura.kick-message", kick_message4);
/* 196 */         String ban_message4 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 197 */         this.main.getConfig().set("anti-killaura.ban-message", ban_message4);
/*     */         
/* 199 */         String report5 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to aimassist. You will be a kicker and report to the staff.");
/* 200 */         this.main.getConfig().set("anti-aimassist.broadcast", report5);
/* 201 */         String broadcast5 = this.main.getConfig().getString(" is using a cheat aimassist.");
/* 202 */         this.main.getConfig().set("anti-aimassist.broadcast", broadcast5);
/* 203 */         String kick_message5 = this.main.getConfig().getString("You were kicked for cheat.");
/* 204 */         this.main.getConfig().set("anti-aimassist.kick-message", kick_message5);
/* 205 */         String ban_message5 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 206 */         this.main.getConfig().set("anti-aimassist.ban-message", ban_message5);
/*     */         
/* 208 */         String report6 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to speed. You will be a kicker and report to the staff.");
/* 209 */         this.main.getConfig().set("anti-speed.broadcast", report6);
/* 210 */         String broadcast6 = this.main.getConfig().getString(" is using a cheat speed.");
/* 211 */         this.main.getConfig().set("anti-speed.broadcast", broadcast6);
/* 212 */         String kick_message6 = this.main.getConfig().getString("You were kicked for cheat.");
/* 213 */         this.main.getConfig().set("anti-speed.kick-message", kick_message6);
/* 214 */         String ban_message6 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 215 */         this.main.getConfig().set("anti-speed.ban-message", ban_message6);
/*     */         
/* 217 */         String report7 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to jesus. You will be a kicker and report to the staff.");
/* 218 */         this.main.getConfig().set("anti-jesus.broadcast", report7);
/* 219 */         String broadcast7 = this.main.getConfig().getString(" is using a cheat jesus.");
/* 220 */         this.main.getConfig().set("anti-jesus.broadcast", broadcast7);
/* 221 */         String kick_message7 = this.main.getConfig().getString("You were kicked for cheat.");
/* 222 */         this.main.getConfig().set("anti-jesus.kick-message", kick_message7);
/* 223 */         String ban_message7 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 224 */         this.main.getConfig().set("anti-jesus.ban-message", ban_message7);
/*     */         
/* 226 */         String report8 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to walk on the lava. You will be a kicker and report to the staff.");
/* 227 */         this.main.getConfig().set("anti-walk-lava.broadcast", report8);
/* 228 */         String broadcast8 = this.main.getConfig().getString(" is using a cheat walk on the lava.");
/* 229 */         this.main.getConfig().set("anti-walk-lava.broadcast", broadcast8);
/* 230 */         String kick_message8 = this.main.getConfig().getString("You were kicked for cheat.");
/* 231 */         this.main.getConfig().set("anti-walk-lava.kick-message", kick_message8);
/* 232 */         String ban_message8 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 233 */         this.main.getConfig().set("anti-walk-lava.ban-message", ban_message8);
/*     */         
/* 235 */         String report9 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to fast eat. You will be a kicker and report to the staff.");
/* 236 */         this.main.getConfig().set("anti-fast-eat.broadcast", report9);
/* 237 */         String broadcast9 = this.main.getConfig().getString(" is using a cheat fast eat.");
/* 238 */         this.main.getConfig().set("anti-fast-eat.broadcast", broadcast9);
/* 239 */         String kick_message9 = this.main.getConfig().getString("You were kicked for cheat.");
/* 240 */         this.main.getConfig().set("anti-fast-eat.kick-message", kick_message9);
/* 241 */         String ban_message9 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 242 */         this.main.getConfig().set("anti-fast-eat.ban-message", ban_message9);
/*     */         
/* 244 */         String report10 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to fast bow. You will be a kicker and report to the staff.");
/* 245 */         this.main.getConfig().set("anti-fast-bow.broadcast", report10);
/* 246 */         String broadcast10 = this.main.getConfig().getString(" is using a cheat fast bow.");
/* 247 */         this.main.getConfig().set("anti-fast-bow.broadcast", broadcast10);
/* 248 */         String kick_message10 = this.main.getConfig().getString("You were kicked for cheat.");
/* 249 */         this.main.getConfig().set("anti-fast-bow.kick-message", kick_message10);
/* 250 */         String ban_message10 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 251 */         this.main.getConfig().set("anti-fast-bow.ban-message", ban_message10);
/*     */         
/* 253 */         String report11 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to fly. You will be a kicker and report to the staff.");
/* 254 */         this.main.getConfig().set("anti-fly.broadcast", report11);
/* 255 */         String broadcast11 = this.main.getConfig().getString(" is using a cheat fly.");
/* 256 */         this.main.getConfig().set("anti-fly.broadcast", broadcast11);
/* 257 */         String kick_message11 = this.main.getConfig().getString("You were kicked for cheat.");
/* 258 */         this.main.getConfig().set("anti-fly.kick-message", kick_message11);
/* 259 */         String ban_message11 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 260 */         this.main.getConfig().set("anti-fly.ban-message", ban_message11);
/*     */         
/* 262 */         String report12 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to headroll. You will be a kicker and report to the staff.");
/* 263 */         this.main.getConfig().set("anti-headroll.broadcast", report12);
/* 264 */         String broadcast12 = this.main.getConfig().getString(" is using a cheat headroll.");
/* 265 */         this.main.getConfig().set("anti-headroll.broadcast", broadcast12);
/* 266 */         String kick_message12 = this.main.getConfig().getString("You were kicked for cheat.");
/* 267 */         this.main.getConfig().set("anti-headroll.kick-message", kick_message12);
/* 268 */         String ban_message12 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 269 */         this.main.getConfig().set("anti-headroll.ban-message", ban_message12);
/*     */         
/* 271 */         String report13 = this.main.getConfig().getString("It looks like you are using a cheat that allows you to reach hack. You will be a kicker and report to the staff.");
/* 272 */         this.main.getConfig().set("reach-hack.broadcast", report13);
/* 273 */         String broadcast13 = this.main.getConfig().getString(" is using a cheat reach hack.");
/* 274 */         this.main.getConfig().set("reach-hack.broadcast", broadcast13);
/* 275 */         String kick_message13 = this.main.getConfig().getString("You were kicked for cheat.");
/* 276 */         this.main.getConfig().set("reach-hack.kick-message", kick_message13);
/* 277 */         String ban_message13 = this.main.getConfig().getString("You have been banned for Cheat.");
/* 278 */         this.main.getConfig().set("reach-hack.ban-message", ban_message13);
/* 279 */       } else if (this.main.getConfig().getString("config.lang") == fr) {
/* 280 */         String report = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de sprinter et de vous faufiler. Vous serez expulsé et signalé au personnel.");
/* 281 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", report);
/* 282 */         String broadcast = this.main.getConfig().getString(" utilise une triche pour sprinter et se faufiler.");
/* 283 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", broadcast);
/* 284 */         String kick_message = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 285 */         this.main.getConfig().set("anti-sneak-sprint.kick-message", kick_message);
/* 286 */         String ban_message = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 287 */         this.main.getConfig().set("anti-sneak-sprint.ban-message", ban_message);
/*     */         
/* 289 */         String report1 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'anti-potion. Vous serez expulsé et signalé au personnel.");
/* 290 */         this.main.getConfig().set("verification-potion.broadcast", report1);
/* 291 */         String broadcast1 = this.main.getConfig().getString(" utilise une triche pour anti-potion.");
/* 292 */         this.main.getConfig().set("verification-potion.broadcast", broadcast1);
/* 293 */         String kick_message1 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 294 */         this.main.getConfig().set("verification-potion.kick-message", kick_message1);
/* 295 */         String ban_message1 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 296 */         this.main.getConfig().set("verification-potion.ban-message", ban_message1);
/*     */         
/* 298 */         String report2 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'anti-fall. Vous serez expulsé et signalé au personnel.");
/* 299 */         this.main.getConfig().set("verification-fall.broadcast", report2);
/* 300 */         String broadcast2 = this.main.getConfig().getString(" utilise une triche pour anti-fall.");
/* 301 */         this.main.getConfig().set("verification-fall.broadcast", broadcast2);
/* 302 */         String kick_message2 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 303 */         this.main.getConfig().set("verification-fall.kick-message", kick_message2);
/* 304 */         String ban_message2 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 305 */         this.main.getConfig().set("verification-fall.ban-message", ban_message2);
/*     */         
/* 307 */         String report3 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'anti-knockback. Vous serez expulsé et signalé au personnel.");
/* 308 */         this.main.getConfig().set("verification-knockback.broadcast", report3);
/* 309 */         String broadcast3 = this.main.getConfig().getString(" utilise une triche pour anti-knockback.");
/* 310 */         this.main.getConfig().set("verification-knockback.broadcast", broadcast3);
/* 311 */         String kick_message3 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 312 */         this.main.getConfig().set("verification-knockback.kick-message", kick_message3);
/* 313 */         String ban_message3 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 314 */         this.main.getConfig().set("verification-knockback.ban-message", ban_message3);
/*     */         
/* 316 */         String report4 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de kill aura. Vous serez expulsé et signalé au personnel.");
/* 317 */         this.main.getConfig().set("anti-killaura.broadcast", report4);
/* 318 */         String broadcast4 = this.main.getConfig().getString(" utilise une triche pour kill aura.");
/* 319 */         this.main.getConfig().set("anti-killaura.broadcast", broadcast4);
/* 320 */         String kick_message4 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 321 */         this.main.getConfig().set("anti-killaura.kick-message", kick_message4);
/* 322 */         String ban_message4 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 323 */         this.main.getConfig().set("anti-killaura.ban-message", ban_message4);
/*     */         
/* 325 */         String report5 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'aimassist. Vous serez expulsé et signalé au personnel.");
/* 326 */         this.main.getConfig().set("anti-aimassist.broadcast", report5);
/* 327 */         String broadcast5 = this.main.getConfig().getString(" utilise une triche pour aimassist.");
/* 328 */         this.main.getConfig().set("anti-aimassist.broadcast", broadcast5);
/* 329 */         String kick_message5 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 330 */         this.main.getConfig().set("anti-aimassist.kick-message", kick_message5);
/* 331 */         String ban_message5 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 332 */         this.main.getConfig().set("anti-aimassist.ban-message", ban_message5);
/*     */         
/* 334 */         String report6 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de speed. Vous serez expulsé et signalé au personnel.");
/* 335 */         this.main.getConfig().set("anti-speed.broadcast", report6);
/* 336 */         String broadcast6 = this.main.getConfig().getString(" utilise une triche pour speed.");
/* 337 */         this.main.getConfig().set("anti-speed.broadcast", broadcast6);
/* 338 */         String kick_message6 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 339 */         this.main.getConfig().set("anti-speed.kick-message", kick_message6);
/* 340 */         String ban_message6 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 341 */         this.main.getConfig().set("anti-speed.ban-message", ban_message6);
/*     */         
/* 343 */         String report7 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de jesus. Vous serez expulsé et signalé au personnel.");
/* 344 */         this.main.getConfig().set("anti-jesus.broadcast", report7);
/* 345 */         String broadcast7 = this.main.getConfig().getString(" utilise une triche pour jesus.");
/* 346 */         this.main.getConfig().set("anti-jesus.broadcast", broadcast7);
/* 347 */         String kick_message7 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 348 */         this.main.getConfig().set("anti-jesus.kick-message", kick_message7);
/* 349 */         String ban_message7 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 350 */         this.main.getConfig().set("anti-jesus.ban-message", ban_message7);
/*     */         
/* 352 */         String report8 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de marcher sur la lave. Vous serez expulsé et signalé au personnel.");
/* 353 */         this.main.getConfig().set("anti-walk-lava.broadcast", report8);
/* 354 */         String broadcast8 = this.main.getConfig().getString(" utilise une triche pour marcher sur la lave.");
/* 355 */         this.main.getConfig().set("anti-walk-lava.broadcast", broadcast8);
/* 356 */         String kick_message8 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 357 */         this.main.getConfig().set("anti-walk-lava.kick-message", kick_message8);
/* 358 */         String ban_message8 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 359 */         this.main.getConfig().set("anti-walk-lava.ban-message", ban_message8);
/*     */         
/* 361 */         String report9 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de manger rapidement. Vous serez expulsé et signalé au personnel.");
/* 362 */         this.main.getConfig().set("anti-fast-eat.broadcast", report9);
/* 363 */         String broadcast9 = this.main.getConfig().getString(" utilise une triche pour manger rapidement.");
/* 364 */         this.main.getConfig().set("anti-fast-eat.broadcast", broadcast9);
/* 365 */         String kick_message9 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 366 */         this.main.getConfig().set("anti-fast-eat.kick-message", kick_message9);
/* 367 */         String ban_message9 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 368 */         this.main.getConfig().set("anti-fast-eat.ban-message", ban_message9);
/*     */         
/* 370 */         String report10 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de tirer rapidement à l'arc. Vous serez expulsé et signalé au personnel.");
/* 371 */         this.main.getConfig().set("anti-fast-bow.broadcast", report10);
/* 372 */         String broadcast10 = this.main.getConfig().getString(" utilise une triche pour tirer rapidement à l'arc.");
/* 373 */         this.main.getConfig().set("anti-fast-bow.broadcast", broadcast10);
/* 374 */         String kick_message10 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 375 */         this.main.getConfig().set("anti-fast-bow.kick-message", kick_message10);
/* 376 */         String ban_message10 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 377 */         this.main.getConfig().set("anti-fast-bow.ban-message", ban_message10);
/*     */         
/* 379 */         String report11 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de voler. Vous serez expulsé et signalé au personnel.");
/* 380 */         this.main.getConfig().set("anti-fly.broadcast", report11);
/* 381 */         String broadcast11 = this.main.getConfig().getString(" utilise une triche pour voler.");
/* 382 */         this.main.getConfig().set("anti-fly.broadcast", broadcast11);
/* 383 */         String kick_message11 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 384 */         this.main.getConfig().set("anti-fly.kick-message", kick_message11);
/* 385 */         String ban_message11 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 386 */         this.main.getConfig().set("anti-fly.ban-message", ban_message11);
/*     */         
/* 388 */         String report12 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de faire des roulades de tête. Vous serez expulsé et signalé au personnel.");
/* 389 */         this.main.getConfig().set("anti-headroll.broadcast", report12);
/* 390 */         String broadcast12 = this.main.getConfig().getString(" utilise une triche pour faire des roulades de tête.");
/* 391 */         this.main.getConfig().set("anti-headroll.broadcast", broadcast12);
/* 392 */         String kick_message12 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 393 */         this.main.getConfig().set("anti-headroll.kick-message", kick_message12);
/* 394 */         String ban_message12 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 395 */         this.main.getConfig().set("anti-headroll.ban-message", ban_message12);
/*     */         
/* 397 */         String report13 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'atteindre le hack. Vous serez expulsé et signalé au personnel.");
/* 398 */         this.main.getConfig().set("reach-hack.broadcast", report13);
/* 399 */         String broadcast13 = this.main.getConfig().getString(" utilise une triche pour reach hack.");
/* 400 */         this.main.getConfig().set("reach-hack.broadcast", broadcast13);
/* 401 */         String kick_message13 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 402 */         this.main.getConfig().set("reach-hack.kick-message", kick_message13);
/* 403 */         String ban_message13 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 404 */         this.main.getConfig().set("reach-hack.ban-message", ban_message13);
/* 405 */       } else if (this.main.getConfig().getString("config.lang") == es) {
/* 406 */         String report = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de sprinter et de vous faufiler. Vous serez expulsé et signalé au personnel.");
/* 407 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", report);
/* 408 */         String broadcast = this.main.getConfig().getString(" utilise une triche pour sprinter et se faufiler.");
/* 409 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", broadcast);
/* 410 */         String kick_message = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 411 */         this.main.getConfig().set("anti-sneak-sprint.kick-message", kick_message);
/* 412 */         String ban_message = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 413 */         this.main.getConfig().set("anti-sneak-sprint.ban-message", ban_message);
/*     */         
/* 415 */         String report1 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'anti-potion. Vous serez expulsé et signalé au personnel.");
/* 416 */         this.main.getConfig().set("verification-potion.broadcast", report1);
/* 417 */         String broadcast1 = this.main.getConfig().getString(" utilise une triche pour anti-potion.");
/* 418 */         this.main.getConfig().set("verification-potion.broadcast", broadcast1);
/* 419 */         String kick_message1 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 420 */         this.main.getConfig().set("verification-potion.kick-message", kick_message1);
/* 421 */         String ban_message1 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 422 */         this.main.getConfig().set("verification-potion.ban-message", ban_message1);
/*     */         
/* 424 */         String report2 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'anti-fall. Vous serez expulsé et signalé au personnel.");
/* 425 */         this.main.getConfig().set("verification-fall.broadcast", report2);
/* 426 */         String broadcast2 = this.main.getConfig().getString(" utilise une triche pour anti-fall.");
/* 427 */         this.main.getConfig().set("verification-fall.broadcast", broadcast2);
/* 428 */         String kick_message2 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 429 */         this.main.getConfig().set("verification-fall.kick-message", kick_message2);
/* 430 */         String ban_message2 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 431 */         this.main.getConfig().set("verification-fall.ban-message", ban_message2);
/*     */         
/* 433 */         String report3 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'anti-knockback. Vous serez expulsé et signalé au personnel.");
/* 434 */         this.main.getConfig().set("verification-knockback.broadcast", report3);
/* 435 */         String broadcast3 = this.main.getConfig().getString(" utilise une triche pour anti-knockback.");
/* 436 */         this.main.getConfig().set("verification-knockback.broadcast", broadcast3);
/* 437 */         String kick_message3 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 438 */         this.main.getConfig().set("verification-knockback.kick-message", kick_message3);
/* 439 */         String ban_message3 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 440 */         this.main.getConfig().set("verification-knockback.ban-message", ban_message3);
/*     */         
/* 442 */         String report4 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de kill aura. Vous serez expulsé et signalé au personnel.");
/* 443 */         this.main.getConfig().set("anti-killaura.broadcast", report4);
/* 444 */         String broadcast4 = this.main.getConfig().getString(" utilise une triche pour kill aura.");
/* 445 */         this.main.getConfig().set("anti-killaura.broadcast", broadcast4);
/* 446 */         String kick_message4 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 447 */         this.main.getConfig().set("anti-killaura.kick-message", kick_message4);
/* 448 */         String ban_message4 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 449 */         this.main.getConfig().set("anti-killaura.ban-message", ban_message4);
/*     */         
/* 451 */         String report5 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'aimassist. Vous serez expulsé et signalé au personnel.");
/* 452 */         this.main.getConfig().set("anti-aimassist.broadcast", report5);
/* 453 */         String broadcast5 = this.main.getConfig().getString(" utilise une triche pour aimassist.");
/* 454 */         this.main.getConfig().set("anti-aimassist.broadcast", broadcast5);
/* 455 */         String kick_message5 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 456 */         this.main.getConfig().set("anti-aimassist.kick-message", kick_message5);
/* 457 */         String ban_message5 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 458 */         this.main.getConfig().set("anti-aimassist.ban-message", ban_message5);
/*     */         
/* 460 */         String report6 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de speed. Vous serez expulsé et signalé au personnel.");
/* 461 */         this.main.getConfig().set("anti-speed.broadcast", report6);
/* 462 */         String broadcast6 = this.main.getConfig().getString(" utilise une triche pour speed.");
/* 463 */         this.main.getConfig().set("anti-speed.broadcast", broadcast6);
/* 464 */         String kick_message6 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 465 */         this.main.getConfig().set("anti-speed.kick-message", kick_message6);
/* 466 */         String ban_message6 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 467 */         this.main.getConfig().set("anti-speed.ban-message", ban_message6);
/*     */         
/* 469 */         String report7 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de jesus. Vous serez expulsé et signalé au personnel.");
/* 470 */         this.main.getConfig().set("anti-jesus.broadcast", report7);
/* 471 */         String broadcast7 = this.main.getConfig().getString(" utilise une triche pour jesus.");
/* 472 */         this.main.getConfig().set("anti-jesus.broadcast", broadcast7);
/* 473 */         String kick_message7 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 474 */         this.main.getConfig().set("anti-jesus.kick-message", kick_message7);
/* 475 */         String ban_message7 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 476 */         this.main.getConfig().set("anti-jesus.ban-message", ban_message7);
/*     */         
/* 478 */         String report8 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de marcher sur la lave. Vous serez expulsé et signalé au personnel.");
/* 479 */         this.main.getConfig().set("anti-walk-lava.broadcast", report8);
/* 480 */         String broadcast8 = this.main.getConfig().getString(" utilise une triche pour marcher sur la lave.");
/* 481 */         this.main.getConfig().set("anti-walk-lava.broadcast", broadcast8);
/* 482 */         String kick_message8 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 483 */         this.main.getConfig().set("anti-walk-lava.kick-message", kick_message8);
/* 484 */         String ban_message8 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 485 */         this.main.getConfig().set("anti-walk-lava.ban-message", ban_message8);
/*     */         
/* 487 */         String report9 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de manger rapidement. Vous serez expulsé et signalé au personnel.");
/* 488 */         this.main.getConfig().set("anti-fast-eat.broadcast", report9);
/* 489 */         String broadcast9 = this.main.getConfig().getString(" utilise une triche pour manger rapidement.");
/* 490 */         this.main.getConfig().set("anti-fast-eat.broadcast", broadcast9);
/* 491 */         String kick_message9 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 492 */         this.main.getConfig().set("anti-fast-eat.kick-message", kick_message9);
/* 493 */         String ban_message9 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 494 */         this.main.getConfig().set("anti-fast-eat.ban-message", ban_message9);
/*     */         
/* 496 */         String report10 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de tirer rapidement à l'arc. Vous serez expulsé et signalé au personnel.");
/* 497 */         this.main.getConfig().set("anti-fast-bow.broadcast", report10);
/* 498 */         String broadcast10 = this.main.getConfig().getString(" utilise une triche pour tirer rapidement à l'arc.");
/* 499 */         this.main.getConfig().set("anti-fast-bow.broadcast", broadcast10);
/* 500 */         String kick_message10 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 501 */         this.main.getConfig().set("anti-fast-bow.kick-message", kick_message10);
/* 502 */         String ban_message10 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 503 */         this.main.getConfig().set("anti-fast-bow.ban-message", ban_message10);
/*     */         
/* 505 */         String report11 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de voler. Vous serez expulsé et signalé au personnel.");
/* 506 */         this.main.getConfig().set("anti-fly.broadcast", report11);
/* 507 */         String broadcast11 = this.main.getConfig().getString(" utilise une triche pour voler.");
/* 508 */         this.main.getConfig().set("anti-fly.broadcast", broadcast11);
/* 509 */         String kick_message11 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 510 */         this.main.getConfig().set("anti-fly.kick-message", kick_message11);
/* 511 */         String ban_message11 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 512 */         this.main.getConfig().set("anti-fly.ban-message", ban_message11);
/*     */         
/* 514 */         String report12 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet de faire des roulades de tête. Vous serez expulsé et signalé au personnel.");
/* 515 */         this.main.getConfig().set("anti-headroll.broadcast", report12);
/* 516 */         String broadcast12 = this.main.getConfig().getString(" utilise une triche pour faire des roulades de tête.");
/* 517 */         this.main.getConfig().set("anti-headroll.broadcast", broadcast12);
/* 518 */         String kick_message12 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 519 */         this.main.getConfig().set("anti-headroll.kick-message", kick_message12);
/* 520 */         String ban_message12 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 521 */         this.main.getConfig().set("anti-headroll.ban-message", ban_message12);
/*     */         
/* 523 */         String report13 = this.main.getConfig().getString("Il semble que vous utilisiez une triche qui vous permet d'atteindre le hack. Vous serez expulsé et signalé au personnel.");
/* 524 */         this.main.getConfig().set("reach-hack.broadcast", report13);
/* 525 */         String broadcast13 = this.main.getConfig().getString(" utilise une triche pour reach hack.");
/* 526 */         this.main.getConfig().set("reach-hack.broadcast", broadcast13);
/* 527 */         String kick_message13 = this.main.getConfig().getString("Vous avez été expulsé pour triche.");
/* 528 */         this.main.getConfig().set("reach-hack.kick-message", kick_message13);
/* 529 */         String ban_message13 = this.main.getConfig().getString("Vous avez été banni pour triche.");
/* 530 */         this.main.getConfig().set("reach-hack.ban-message", ban_message13);
/*     */       }
/* 532 */       else if (this.main.getConfig().getString("config.lang") == de) {
/* 533 */         String report = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht zu sprinten und dich zu schleichen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 534 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", report);
/* 535 */         String broadcast = this.main.getConfig().getString(" benutzt einen Cheat, um zu sprinten und sich zu schleichen.");
/* 536 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", broadcast);
/* 537 */         String kick_message = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 538 */         this.main.getConfig().set("anti-sneak-sprint.kick-message", kick_message);
/* 539 */         String ban_message = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 540 */         this.main.getConfig().set("anti-sneak-sprint.ban-message", ban_message);
/*     */         
/* 542 */         String report1 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, Tränke zu verhindern. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 543 */         this.main.getConfig().set("verification-potion.broadcast", report1);
/* 544 */         String broadcast1 = this.main.getConfig().getString(" benutzt einen Cheat, um Tränke zu verhindern.");
/* 545 */         this.main.getConfig().set("verification-potion.broadcast", broadcast1);
/* 546 */         String kick_message1 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 547 */         this.main.getConfig().set("verification-potion.kick-message", kick_message1);
/* 548 */         String ban_message1 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 549 */         this.main.getConfig().set("verification-potion.ban-message", ban_message1);
/*     */         
/* 551 */         String report2 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, nicht zu fallen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 552 */         this.main.getConfig().set("verification-fall.broadcast", report2);
/* 553 */         String broadcast2 = this.main.getConfig().getString(" benutzt einen Cheat, um nicht zu fallen.");
/* 554 */         this.main.getConfig().set("verification-fall.broadcast", broadcast2);
/* 555 */         String kick_message2 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 556 */         this.main.getConfig().set("verification-fall.kick-message", kick_message2);
/* 557 */         String ban_message2 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 558 */         this.main.getConfig().set("verification-fall.ban-message", ban_message2);
/*     */         
/* 560 */         String report3 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, den Rückstoß zu verhindern. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 561 */         this.main.getConfig().set("verification-knockback.broadcast", report3);
/* 562 */         String broadcast3 = this.main.getConfig().getString(" benutzt einen Cheat, um den Rückstoß zu verhindern.");
/* 563 */         this.main.getConfig().set("verification-knockback.broadcast", broadcast3);
/* 564 */         String kick_message3 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 565 */         this.main.getConfig().set("verification-knockback.kick-message", kick_message3);
/* 566 */         String ban_message3 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 567 */         this.main.getConfig().set("verification-knockback.ban-message", ban_message3);
/*     */         
/* 569 */         String report4 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, KillAura zu haben. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 570 */         this.main.getConfig().set("anti-killaura.broadcast", report4);
/* 571 */         String broadcast4 = this.main.getConfig().getString(" benutzt einen Cheat, um KillAura zu haben.");
/* 572 */         this.main.getConfig().set("anti-killaura.broadcast", broadcast4);
/* 573 */         String kick_message4 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 574 */         this.main.getConfig().set("anti-killaura.kick-message", kick_message4);
/* 575 */         String ban_message4 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 576 */         this.main.getConfig().set("anti-killaura.ban-message", ban_message4);
/*     */         
/* 578 */         String report5 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, AimAssist zu haben. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 579 */         this.main.getConfig().set("anti-aimassist.broadcast", report5);
/* 580 */         String broadcast5 = this.main.getConfig().getString(" benutzt einen Cheat, um AimAssist zu haben.");
/* 581 */         this.main.getConfig().set("anti-aimassist.broadcast", broadcast5);
/* 582 */         String kick_message5 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 583 */         this.main.getConfig().set("anti-aimassist.kick-message", kick_message5);
/* 584 */         String ban_message5 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 585 */         this.main.getConfig().set("anti-aimassist.ban-message", ban_message5);
/*     */         
/* 587 */         String report6 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, schnell zu rennen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 588 */         this.main.getConfig().set("anti-speed.broadcast", report6);
/* 589 */         String broadcast6 = this.main.getConfig().getString(" benutzt einen Cheat, um schnell zu rennen.");
/* 590 */         this.main.getConfig().set("anti-speed.broadcast", broadcast6);
/* 591 */         String kick_message6 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 592 */         this.main.getConfig().set("anti-speed.kick-message", kick_message6);
/* 593 */         String ban_message6 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 594 */         this.main.getConfig().set("anti-speed.ban-message", ban_message6);
/*     */         
/* 596 */         String report7 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, auf Wasser zu laufen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 597 */         this.main.getConfig().set("anti-jesus.broadcast", report7);
/* 598 */         String broadcast7 = this.main.getConfig().getString(" benutzt einen Cheat, um auf Wasser zu laufen.");
/* 599 */         this.main.getConfig().set("anti-jesus.broadcast", broadcast7);
/* 600 */         String kick_message7 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 601 */         this.main.getConfig().set("anti-jesus.kick-message", kick_message7);
/* 602 */         String ban_message7 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 603 */         this.main.getConfig().set("anti-jesus.ban-message", ban_message7);
/*     */         
/* 605 */         String report8 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, auf Lava zu laufen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 606 */         this.main.getConfig().set("anti-walk-lava.broadcast", report8);
/* 607 */         String broadcast8 = this.main.getConfig().getString(" benutzt einen Cheat, um auf Lava zu laufen.");
/* 608 */         this.main.getConfig().set("anti-walk-lava.broadcast", broadcast8);
/* 609 */         String kick_message8 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 610 */         this.main.getConfig().set("anti-walk-lava.kick-message", kick_message8);
/* 611 */         String ban_message8 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 612 */         this.main.getConfig().set("anti-walk-lava.ban-message", ban_message8);
/*     */         
/* 614 */         String report9 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, schnell zu essen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 615 */         this.main.getConfig().set("anti-fast-eat.broadcast", report9);
/* 616 */         String broadcast9 = this.main.getConfig().getString(" benutzt einen Cheat, um schnell zu essen.");
/* 617 */         this.main.getConfig().set("anti-fast-eat.broadcast", broadcast9);
/* 618 */         String kick_message9 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 619 */         this.main.getConfig().set("anti-fast-eat.kick-message", kick_message9);
/* 620 */         String ban_message9 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 621 */         this.main.getConfig().set("anti-fast-eat.ban-message", ban_message9);
/*     */         
/* 623 */         String report10 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, mit dem Bogen schnell zu schießen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 624 */         this.main.getConfig().set("anti-fast-bow.broadcast", report10);
/* 625 */         String broadcast10 = this.main.getConfig().getString(" benutzt einen Cheat, um mit dem Bogen schnell zu schießen.");
/* 626 */         this.main.getConfig().set("anti-fast-bow.broadcast", broadcast10);
/* 627 */         String kick_message10 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 628 */         this.main.getConfig().set("anti-fast-bow.kick-message", kick_message10);
/* 629 */         String ban_message10 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 630 */         this.main.getConfig().set("anti-fast-bow.ban-message", ban_message10);
/*     */         
/* 632 */         String report11 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht zu fliegen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 633 */         this.main.getConfig().set("anti-fly.broadcast", report11);
/* 634 */         String broadcast11 = this.main.getConfig().getString(" benutzt einen Cheat, um zu fliegen.");
/* 635 */         this.main.getConfig().set("anti-fly.broadcast", broadcast11);
/* 636 */         String kick_message11 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 637 */         this.main.getConfig().set("anti-fly.kick-message", kick_message11);
/* 638 */         String ban_message11 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 639 */         this.main.getConfig().set("anti-fly.ban-message", ban_message11);
/*     */         
/* 641 */         String report12 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, Kopfrollen zu machen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 642 */         this.main.getConfig().set("anti-headroll.broadcast", report12);
/* 643 */         String broadcast12 = this.main.getConfig().getString(" benutzt einen Cheat, um Kopfrollen zu machen.");
/* 644 */         this.main.getConfig().set("anti-headroll.broadcast", broadcast12);
/* 645 */         String kick_message12 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 646 */         this.main.getConfig().set("anti-headroll.kick-message", kick_message12);
/* 647 */         String ban_message12 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 648 */         this.main.getConfig().set("anti-headroll.ban-message", ban_message12);
/*     */         
/* 650 */         String report13 = this.main.getConfig().getString("Es scheint, als ob du einen Cheat benutzt, der es dir ermöglicht, weit zu schlagen. Du wirst rausgeworfen und dem Personal gemeldet.");
/* 651 */         this.main.getConfig().set("reach-hack.broadcast", report13);
/* 652 */         String broadcast13 = this.main.getConfig().getString(" benutzt einen Cheat, um weit zu schlagen.");
/* 653 */         this.main.getConfig().set("reach-hack.broadcast", broadcast13);
/* 654 */         String kick_message13 = this.main.getConfig().getString("Du wurdest für Cheating rausgeworfen.");
/* 655 */         this.main.getConfig().set("reach-hack.kick-message", kick_message13);
/* 656 */         String ban_message13 = this.main.getConfig().getString("Du wurdest für Cheating gebannt.");
/* 657 */         this.main.getConfig().set("reach-hack.ban-message", ban_message13);
/* 658 */       } else if (this.main.getConfig().getString("config.lang") == it) {
/* 659 */         String report = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di correre e di muoverti furtivamente. Sarai espulso e segnalato al personale.");
/* 660 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", report);
/* 661 */         String broadcast = this.main.getConfig().getString(" sta usando una truffa per correre e muoversi furtivamente.");
/* 662 */         this.main.getConfig().set("anti-sneak-sprint.broadcast", broadcast);
/* 663 */         String kick_message = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 664 */         this.main.getConfig().set("anti-sneak-sprint.kick-message", kick_message);
/* 665 */         String ban_message = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 666 */         this.main.getConfig().set("anti-sneak-sprint.ban-message", ban_message);
/*     */         
/* 668 */         String report1 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di annullare gli effetti delle pozioni. Sarai espulso e segnalato al personale.");
/* 669 */         this.main.getConfig().set("verification-potion.broadcast", report1);
/* 670 */         String broadcast1 = this.main.getConfig().getString(" sta usando una truffa per annullare gli effetti delle pozioni.");
/* 671 */         this.main.getConfig().set("verification-potion.broadcast", broadcast1);
/* 672 */         String kick_message1 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 673 */         this.main.getConfig().set("verification-potion.kick-message", kick_message1);
/* 674 */         String ban_message1 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 675 */         this.main.getConfig().set("verification-potion.ban-message", ban_message1);
/*     */         
/* 677 */         String report2 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di annullare la caduta. Sarai espulso e segnalato al personale.");
/* 678 */         this.main.getConfig().set("verification-fall.broadcast", report2);
/* 679 */         String broadcast2 = this.main.getConfig().getString(" sta usando una truffa per annullare la caduta.");
/* 680 */         this.main.getConfig().set("verification-fall.broadcast", broadcast2);
/* 681 */         String kick_message2 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 682 */         this.main.getConfig().set("verification-fall.kick-message", kick_message2);
/* 683 */         String ban_message2 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 684 */         this.main.getConfig().set("verification-fall.ban-message", ban_message2);
/*     */         
/* 686 */         String report3 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di annullare il respingimento. Sarai espulso e segnalato al personale.");
/* 687 */         this.main.getConfig().set("verification-knockback.broadcast", report3);
/* 688 */         String broadcast3 = this.main.getConfig().getString(" sta usando una truffa per annullare il respingimento.");
/* 689 */         this.main.getConfig().set("verification-knockback.broadcast", broadcast3);
/* 690 */         String kick_message3 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 691 */         this.main.getConfig().set("verification-knockback.kick-message", kick_message3);
/* 692 */         String ban_message3 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 693 */         this.main.getConfig().set("verification-knockback.ban-message", ban_message3);
/*     */         
/* 695 */         String report4 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di avere l'aura di uccisione. Sarai espulso e segnalato al personale.");
/* 696 */         this.main.getConfig().set("anti-killaura.broadcast", report4);
/* 697 */         String broadcast4 = this.main.getConfig().getString(" sta usando una truffa per avere l'aura di uccisione.");
/* 698 */         this.main.getConfig().set("anti-killaura.broadcast", broadcast4);
/* 699 */         String kick_message4 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 700 */         this.main.getConfig().set("anti-killaura.kick-message", kick_message4);
/* 701 */         String ban_message4 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 702 */         this.main.getConfig().set("anti-killaura.ban-message", ban_message4);
/*     */         
/* 704 */         String report5 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di avere l'assistenza di mira. Sarai espulso e segnalato al personale.");
/* 705 */         this.main.getConfig().set("anti-aimassist.broadcast", report5);
/* 706 */         String broadcast5 = this.main.getConfig().getString(" sta usando una truffa per avere l'assistenza di mira.");
/* 707 */         this.main.getConfig().set("anti-aimassist.broadcast", broadcast5);
/* 708 */         String kick_message5 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 709 */         this.main.getConfig().set("anti-aimassist.kick-message", kick_message5);
/* 710 */         String ban_message5 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 711 */         this.main.getConfig().set("anti-aimassist.ban-message", ban_message5);
/*     */         
/* 713 */         String report6 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di correre velocemente. Sarai espulso e segnalato al personale.");
/* 714 */         this.main.getConfig().set("anti-speed.broadcast", report6);
/* 715 */         String broadcast6 = this.main.getConfig().getString(" sta usando una truffa per correre velocemente.");
/* 716 */         this.main.getConfig().set("anti-speed.broadcast", broadcast6);
/* 717 */         String kick_message6 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 718 */         this.main.getConfig().set("anti-speed.kick-message", kick_message6);
/* 719 */         String ban_message6 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 720 */         this.main.getConfig().set("anti-speed.ban-message", ban_message6);
/*     */         
/* 722 */         String report7 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di camminare sull'acqua. Sarai espulso e segnalato al personale.");
/* 723 */         this.main.getConfig().set("anti-jesus.broadcast", report7);
/* 724 */         String broadcast7 = this.main.getConfig().getString(" sta usando una truffa per camminare sull'acqua.");
/* 725 */         this.main.getConfig().set("anti-jesus.broadcast", broadcast7);
/* 726 */         String kick_message7 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 727 */         this.main.getConfig().set("anti-jesus.kick-message", kick_message7);
/* 728 */         String ban_message7 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 729 */         this.main.getConfig().set("anti-jesus.ban-message", ban_message7);
/*     */         
/* 731 */         String report8 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di camminare sulla lava. Sarai espulso e segnalato al personale.");
/* 732 */         this.main.getConfig().set("anti-walk-lava.broadcast", report8);
/* 733 */         String broadcast8 = this.main.getConfig().getString(" sta usando una truffa per camminare sulla lava.");
/* 734 */         this.main.getConfig().set("anti-walk-lava.broadcast", broadcast8);
/* 735 */         String kick_message8 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 736 */         this.main.getConfig().set("anti-walk-lava.kick-message", kick_message8);
/* 737 */         String ban_message8 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 738 */         this.main.getConfig().set("anti-walk-lava.ban-message", ban_message8);
/*     */         
/* 740 */         String report9 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di mangiare rapidamente. Sarai espulso e segnalato al personale.");
/* 741 */         this.main.getConfig().set("anti-fast-eat.broadcast", report9);
/* 742 */         String broadcast9 = this.main.getConfig().getString(" sta usando una truffa per mangiare rapidamente.");
/* 743 */         this.main.getConfig().set("anti-fast-eat.broadcast", broadcast9);
/* 744 */         String kick_message9 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 745 */         this.main.getConfig().set("anti-fast-eat.kick-message", kick_message9);
/* 746 */         String ban_message9 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 747 */         this.main.getConfig().set("anti-fast-eat.ban-message", ban_message9);
/*     */         
/* 749 */         String report10 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di usare l'arco rapidamente. Sarai espulso e segnalato al personale.");
/* 750 */         this.main.getConfig().set("anti-fast-bow.broadcast", report10);
/* 751 */         String broadcast10 = this.main.getConfig().getString(" sta usando una truffa per usare l'arco rapidamente.");
/* 752 */         this.main.getConfig().set("anti-fast-bow.broadcast", broadcast10);
/* 753 */         String kick_message10 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 754 */         this.main.getConfig().set("anti-fast-bow.kick-message", kick_message10);
/* 755 */         String ban_message10 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 756 */         this.main.getConfig().set("anti-fast-bow.ban-message", ban_message10);
/*     */         
/* 758 */         String report11 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di volare. Sarai espulso e segnalato al personale.");
/* 759 */         this.main.getConfig().set("anti-fly.broadcast", report11);
/* 760 */         String broadcast11 = this.main.getConfig().getString(" sta usando una truffa per volare.");
/* 761 */         this.main.getConfig().set("anti-fly.broadcast", broadcast11);
/* 762 */         String kick_message11 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 763 */         this.main.getConfig().set("anti-fly.kick-message", kick_message11);
/* 764 */         String ban_message11 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 765 */         this.main.getConfig().set("anti-fly.ban-message", ban_message11);
/*     */         
/* 767 */         String report12 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di fare capriole con la testa. Sarai espulso e segnalato al personale.");
/* 768 */         this.main.getConfig().set("anti-headroll.broadcast", report12);
/* 769 */         String broadcast12 = this.main.getConfig().getString(" sta usando una truffa per fare capriole con la testa.");
/* 770 */         this.main.getConfig().set("anti-headroll.broadcast", broadcast12);
/* 771 */         String kick_message12 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 772 */         this.main.getConfig().set("anti-headroll.kick-message", kick_message12);
/* 773 */         String ban_message12 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 774 */         this.main.getConfig().set("anti-headroll.ban-message", ban_message12);
/*     */         
/* 776 */         String report13 = this.main.getConfig().getString("Sembra che tu stia usando una truffa che ti consente di colpire lontano. Sarai espulso e segnalato al personale.");
/* 777 */         this.main.getConfig().set("reach-hack.broadcast", report13);
/* 778 */         String broadcast13 = this.main.getConfig().getString(" sta usando una truffa per colpire lontano.");
/* 779 */         this.main.getConfig().set("reach-hack.broadcast", broadcast13);
/* 780 */         String kick_message13 = this.main.getConfig().getString("Sei stato espulso per truffa.");
/* 781 */         this.main.getConfig().set("reach-hack.kick-message", kick_message13);
/* 782 */         String ban_message13 = this.main.getConfig().getString("Sei stato bannato per truffa.");
/* 783 */         this.main.getConfig().set("reach-hack.ban-message", ban_message13);
/*     */       } 
/*     */     }
/*     */     
/* 787 */     this.main.saveConfig();
/*     */   }
/*     */ }


/* Location:              C:\Users\Toni\Downloads\Oxa-AntiCheat-1.3.5.jar!\fr\aorux\oxa\langage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */