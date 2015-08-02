package com.lothrazar.powerinventory;
 
/** 
 * @author Sam Bassett aka Lothrazar
 */
public class Const
{ 
    public static final String MODID = "powerinventory"; 
   // public static final String INVENTORY_TEXTURE = "textures/gui/inventory_12x18.png";
    public static final String getInventoryTexture()
    {
    	if(ALL_ROWS == 12 && ALL_COLS == 18)
    		return "textures/gui/inventory_12x18.png";//216
    	else if(ALL_ROWS == 15 && ALL_COLS == 25)
    		return "textures/gui/inventory_15x25.png";//375
    	else 
    		return null;//a non supported resolution
    }
	public static final String NBT_SLOT = "Slot";
	public static final String NBT_PLAYER = "Player";
	public static final String NBT_WORLD = "World";
	public static final String NBT_ID = "ID";
	public static final String NBT_Settings = "Settings";
	public static final String NBT_Unlocked = "Unlocked";
	public static final String NBT_INVENTORY = "Inventory";
	public static final String NBT_INVOSIZE = "invoSize";

	public static final int sq = 18;
	public final static int HOTBAR_SIZE = 9;
	public final static int ARMOR_SIZE = 4; 
 //TODO: what else needs to change to get to 12 18
	public static int ALL_ROWS = 3 + 12;//3+12=15
	public static int ALL_COLS = 9 + 16;//9+16=25
	//public final static int INV_SIZE  = ALL_ROWS * ALL_COLS;//15*25=375 
//375;//
	//these are slot indices. different than slot numbers (important)
    public static final int enderPearlSlot = 777777; 
    public static final int enderChestSlot = enderPearlSlot+1;
    public static final int clockSlot = enderPearlSlot+2;
    public static final int compassSlot = enderPearlSlot+3;
    public static final int bottleSlot = enderPearlSlot+4;
    
     
	public final static int INV_ENDER = 1;
	public final static int INV_PLAYER = 2;
	
	public final static int SORT_LEFT = 1;
	public final static int SORT_RIGHT = 2;
	public final static int SORT_LEFTALL = -1;
	public final static int SORT_RIGHTALL = -2;
}
