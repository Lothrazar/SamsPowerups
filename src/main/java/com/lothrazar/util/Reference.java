package com.lothrazar.util;

import java.util.ArrayList;

import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;

public class Reference 
{  
	public static final String MODID = "samscontent";
	public static final String TEXTURE_LOCATION = Reference.MODID + ":";
	public static final String VERSION = "1.8.1_1.0-dev";
	public static final String NAME = "Sam's Content";

	public static final String keyUpName = "key.columnshiftup";
	public static final String keyDownName = "key.columnshiftdown"; 
	public static final String keyCategory = "key.categories.inventory";
	
	public static final int TICKS_PER_SEC = 20;
	public static long ticksPerDay = 24000 ;
	 
	public static final int face_bottom = 0;	
	public static final int face_top = 1;
	public static final int face_north = 2;
	public static final int face_south = 3;
	public static final int face_west = 4;
	public static final int face_east = 5;
	  
	public static final int skull_skeleton = 0;	
	public static final int skull_wither = 1;
	public static final int skull_zombie = 2;
	public static final int skull_player = 3;
	public static final int skull_creeper = 4;
	 
	public static final int dye_incsac = 0;
	public static final int dye_red = 1;
	public static final int dye_cactus = 2;
	public static final int dye_cocoa = 3;
	public static final int dye_lapis = 4;
	public static final int dye_purple = 5;
	public static final int dye_cyan = 6;
	public static final int dye_lightgray = 7;
	public static final int dye_gray = 8;
	public static final int dye_pink = 9;
	public static final int dye_lime = 10;
	public static final int dye_dandelion = 11;
	public static final int dye_lightblue = 12;
	public static final int dye_magenta = 13;
	public static final int dye_orange = 14;
	public static final int dye_bonemeal = 15;
	
 
	public static final int CHEST_RARITY_COMMON = 100; 
	public static final int CHEST_RARITY_REDSTONE = 50;
	public static final int CHEST_RARITY_RECORD = 5;
	public static final int CHEST_RARITY_GAPPLE = 1;
	  
	//import net.minecraftforge.common.ChestGenHooks;
	public static String chest_mineshaftCorridor = "mineshaftCorridor"; 
	public static String chest_pyramidJungleChest= "pyramidJungleChest"; 
	public static String chest_pyramidDesertyChest= "pyramidDesertyChest"; 
	public static String chest_pyramidJungleDispenser= "pyramidJungleDispenser"; 
	public static String chest_strongholdCorridor= "strongholdCorridor"; 
	public static String chest_strongholdLibrary= "strongholdLibrary"; 
	public static String chest_strongholdCrossing= "strongholdCrossing";  
	public static String chest_villageBlacksmith= "villageBlacksmith"; 
	public static String chest_bonusChest= "bonusChest"; 
	public static String chest_dungeonChest= "dungeonChest";
	public static String toolClassShovel = "shovel";
	public static String toolClassAxe = "axe";
 
 
 
	public static final int stone_slab_stone = 0;
	public static final int stone_slab_sandstone = 1;
	public static final int stone_slab_oldwood = 2;
	public static final int stone_slab_cobble = 3;
	public static final int stone_slab_brickred = 4;
	public static final int stone_slab_stonebrick = 5;
	public static final int stone_slab_netehrbrick = 6;
	public static final int stone_slab_quartz = 7;
	public static final int stone_slab_flat = 8;
	public static final int stone_slab_sandflat = 9; 
	
	public static final int stonebrick_stone = 0;
	public static final int stonebrick_mossy = 1;
	public static final int stonebrick_cracked = 2;
	public static final int stonebrick_chisel = 3;
	
	public static final int planks_oak = 0;
	public static final int planks_spruce = 1;
	public static final int planks_birch = 2;
	public static final int planks_jungle = 3;
	public static final int planks_acacia = 4;
	public static final int planks_darkoak = 5;
	 
	public static final int log_oak = 0;
	public static final int log_spruce = 1;
	public static final int log_birch = 2;
	public static final int log_jungle = 3;
	public static final int log2_acacia = 0;
	public static final int log2_darkoak = 5;
	
	public static final int sapling_oak = 0;
	public static final int sapling_spruce = 1;
	public static final int sapling_birch = 2;
	public static final int sapling_jungle = 3;
	public static final int sapling_acacia = 4;
	public static final int sapling_darkoak = 5;
	 
	public static final int quartz_block = 0;
	public static final int quartz_chiselled = 1;
	public static final int quartz_pillar = 2;
	
	public static final int cobblestone_wall_plain = 0;
	public static final int cobblestone_wall_mossy = 1;
	
	public static final int potion_SPEED = 1;   
	public static final int potion_SLOWNESS = 2;
	public static final int potion_HASTE = 3; //beacon
	public static final int potion_FATIGUE = 4; 
 	public static final int potion_STRENGTH = 5;     
	public static final int potion_instanthealth = 6;   
	public static final int potion_instantdamage = 7;
	public static final int potion_JUMP = 8;         
	public static final int potion_NAUSEA = 9;
	public static final int potion_REGEN = 10;         
	public static final int potion_RESISTANCE = 11;    //beacon
	public static final int potion_FIRERESIST = 12;
	public static final int potion_WATER_BREATHING = 13;   // ?? double check potion
	public static final int potion_INVIS = 14;
	public static final int potion_BLINDNESS = 15; 
	public static final int potion_NIGHT_VISION = 16;     
	public static final int potion_HUNGER = 17; 
	public static final int potion_WEAKNESS = 18;      
	public static final int potion_POISON = 19;
	public static final int potion_WITHER = 20;
	public static final int potion_HEALTH_BOOST = 21;      //My mod edibles
	public static final int potion_absorption = 22;// is absorption; like 21 but they vanish like gold apple hearts
	public static final int potion_SATURATION = 23;//fills hunger instantly, kind of like peaceful
	
	public static final int entity_cow = 92;
	public static final int entity_pig = 90;
	public static final int entity_sheep = 91;
	public static final int entity_chicken = 93;
	public static final int entity_mooshroom = 96;
	public static final int entity_bat = 65;

	public static final int BROWN = 0;
	public static final int WHITE = 1;
	public static final int PURPLE = 2;
	public static final int BLACK = 3;
	public static final int WHITEAPRON = 4;
  
	public class Dimension
	{
		public static final int overworld = 0;
		public static final int end = 1;
		public static final int nether = -1;
	}
	//100 to 103 is the armor
	public class PlayerInventory
	{
		public static final int ROWS = 3;
		public static final int COLS = 9;
		public static final int SIZE = ROWS*COLS;
		public static final int START = 9;//top left
		public static final int END = START + SIZE;
	}
  
	public class FurnaceBurnTime // inner class
	{
		public static final int Sticks = 100;
		public static final int WoodenSlabs = 150;
		public static final int WoodenTools = 200;
		public static final int WoodStuff = 300;
		public static final int Coal = 1600; 
		public static final int LavaBucket = 20000;
		public static final int Sapling = 100;
		public static final int BlazeRod = 2400;
				
	}
	public static class gamerule
	{
		public final static String commandBlockOutput  = "commandBlockOutput";
		public static final String doDaylightCycle = "doDaylightCycle";
		public static final String doFireTick = "doFireTick";
		public static final String doMobLoot = "doMobLoot";
		public static final String doMobSpawning = "doMobSpawning";
		public static final String doTileDrops = "doTileDrops";
		public static final String keepInventory = "keepInventory";
		public static final String mobGriefing = "mobGriefing";
		public static final String naturalRegeneration = "naturalRegeneration";
	}
 
	public static class grassplant
	{
		public final static int deadbush=0;
		public final static int grass=1;
		public final static int fern=2;
	} 
  
	public static class tallgrass
	{
		public final static int sunflower = 0;
		public final static int lilac=1;
		public final static int grass=2;
		public final static int fern=3;
		public final static int rosebush=4;
		public final static int peony=5; 
	}  
	
	
	
	
	
	
	
	
	
	/*
	 * http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571574-all-minecraft-playsound-file-names
	 * 
	 * 1 - ambient.cave.[1-13]
2 - ambient.weather.rain[1-4]
3 - ambient.weather.thunder[1-3]
------------------------------------------------
4 - damage.fallbig
5 - damage.fallsmall
6 - damage.hit[1-3]
------------------------------------------------
7 - dig.cloth[1-4]
8 - dig.grass[1-4]
9 - dig.gravel[1-4]
10 - dig.sand[1-4]
11 - dig.snow[1-4]
12 - dig.stone[1-4]
13 - dig.wood[1-4]
-------------------------------------------------
14 - fire.fire
15 - fire.ignite
-------------------------------------------------
16 - fireworks.blast_far1
17 - fireworks.blast1
18 - fireworks.largeBlast_far1
19 - fireworks.largeBlast1
20 - fireworks.launch1
21 - fireworks.twinkle_far1
22 - fireworks.twinkle1
-------------------------------------------------
23 - liquid.lava
24 - liquid.lavapop
25 - liquid.splash[1-2]
26 - liquid.swim[1-4]
27 - liquid.water
-------------------------------------------------
28 - minecart.base
29 - minecart.inside
-------------------------------------------------
30 - mob.bat.death
31 - mob.bat.hurt[1-4]
32 - mob.bat.idle[1-4]
33 - mob.bat.loop
34 - mob.bat.takeoff
-------------------------------------------------
35 - mob.blaze.breathe[1-4]
36 - mob.blaze.death
37 - mob.blaze.hit[1-4]
-------------------------------------------------
38 - mob.cat.hiss[1-3]
39 - mob.cat.hitt[1-3]
40 - mob.cat.meow[1-4]
41 - mob.cat.purr[1-3]
42 - mob.cat.purreow[1-2]
-------------------------------------------------
43 - mob.chicken.hurt[1-2]
44 - mob.chicken.plop
45 - mob.chicken.say[1-3]
46 - mob.chicken.step[1-2]
-------------------------------------------------
47 - mob.cow.hurt[1-3]
48 - mob.cow.say[1-4]
49 - mob.cow.step[1-4]
-------------------------------------------------
50 - mob.creeper.death
51 - mob.creeper.say[1-4]
-------------------------------------------------
52 - mob.enderdragon.end
53 - mob.enderdragon.growl[1-4]
54 - mob.enderdragon.hit[1-4]
55 - mob.enderdragon.wings[1-6]
-------------------------------------------------
56 - mob.endermen.death
57 - mob.endermen.hit[1-4]
58 - mob.endermen.idle[1-5]
59 - mob.endermen.portal[1-2]
60 - mob.endermen.scream[1-4]
61 - mob.endermen.stare
-------------------------------------------------
62 - mob.ghast.affectionate_scream
63 - mob.ghast.charge
64 - mob.ghast.death
65 - mob.ghast.fireball4
66 - mob.ghast.moan[1-7]
67 - mob.ghast.scream[1-5]
-------------------------------------------------
I DID NOT INCLUDE HORSE (TOO MANY SOUNDS) (149) :SSSS:
-------------------------------------------------
68 - mob.irongolem.death
69 - mob.irongolem.hit[1-4]
70 - mob.irongolem.throw
71 - mob.irongolem.walk[1-4]
-------------------------------------------------
72 - mob.magmacube.big[1-4]
73 - mob.magmacube.jump[1-4]
74 - mob.magmacube.small[1-5]
-------------------------------------------------
75 - mob.pig.death
76 - mob.pig.say[1-3]
77 - mob.pig.step[1-5]
-------------------------------------------------
78 - mob.sheep.say[1-3]
79 - mob.sheep.shear
80 - mob.sheep.step[1-5]
-------------------------------------------------
81 - mob.silverfish.hit[1-3]
82 - mob.silverfish.kill
83 - mob.silverfish.say[1-4]
84 - mob.silverfish.step[1-4]
-------------------------------------------------
85 - mob.skeleton.death
86 - mob.skeleton.hurt[1-4]
87 - mob.skeleton.say[1-3]
88 - mob.skeleton.step[1-4]
-------------------------------------------------
89 - mob.slime.attack[1-2]
90 - mob.slime.big[1-4]
91 - mob.slime.small[1-5]
-------------------------------------------------
92 - mob.spider.death
93 - mob.spider.say[1-4]
94 - mob.spider.step[1-4]
-------------------------------------------------
95 - mob.villager.death
96 - mob.villager.haggle[1-3]
97 - mob.villager.hit[1-4]
98 - mob.villager.idle[1-3]
99 - mob.villager.no[1-3]
100 - mob.villager.yes[1-3]
-------------------------------------------------
101 - mob.wither.death
102 - mob.wither.hurt[1-4]
103 - mob.wither.idle[1-4]
104 - mob.wither.shoot
105 - mob.wither.spawn
-------------------------------------------------
106 - mob.wolf.bark[1-3]
107 - mob.wolf.death
108 - mob.wolf.growl[1-3]
109 - mob.wolf.howl[1-2]
110 - mob.wolf.hurt[1-3]
111 - mob.wolf.panting
112 - mob.wolf.shake
113 - mob.wolf.step[1-5]
114 - mob.wolf.whine
-------------------------------------------------
115 - mob.zombie.death
116 - mob.zombie.hurt[1-2]
117 - mob.zombie.infect
118 - mob.zombie.metal[1-3]
119 - mob.zombie.say[1-3]
120 - mob.zombie.step[1-5]
121 - mob.zombie.remedy
122 - mob.zombie.unfect
123 - mob.zombie.wood[1-4]
124 - mob.zombie.woodbreak
-------------------------------------------------
125 - mob.zombiepig.zpig[1-4]
126 - mob.zombiepig.zpigangry[1-4]
127 - mob.zombiepig.zpigdeath
128 - mob.zombiepig.zpighurt[1-2]
-------------------------------------------------
129 - note.bass
130 - note.bassattack
131 - note.bd
132 - note.harp
133 - note.hat
134 - note.pling
135 - note.snare
-------------------------------------------------
136 - portal.portal
137 - portal.travel
138 - portal.trigger
-------------------------------------------------
139 - random.anvil_break
140 - random.anvil_land
141 - random.anvil_use
142 - random.bow
143 - random.bowhit[1-4]
144 - random.break
145 - random.breath
146 - random.burp
147 - random.chestclosed
148 - random.chestopen
149 - random.classic_hurt
150 - random.click
151 - random.door_close
152 - random.door_open
153 - random.drink
154 - random.eat[1-3]
155 - random.explode[1-4]
156 - random.fizz
157 - random.fuse
158 - random.glass[1-3]
159 - random.levelup
160 - random.orb
161 - random.pop
162 - random.successful_hit
163 - random.wood_click
-------------------------------------------------
164 - step.cloth[1-4]
165 - step.grass[1-6]
166 - step.gravel[1-4]
167 - step.ladder[1-5]
168 - step.sand[1-5]
169 - step.snow[1-4]
170 - step.stone[1-6]
171 - step.wood[1-6]
-------------------------------------------------
172 - tile.piston.in
173 - tile.piston.out*/
}//ends class reference