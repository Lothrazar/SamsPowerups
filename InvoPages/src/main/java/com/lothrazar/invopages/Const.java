package com.lothrazar.invopages;
 
/** 
 * @author Sam Bassett aka Lothrazar
 */
public class Const
{ 
    public static final String MODID = "invopages";
    public static final String INVENTORY_TEXTURE = "textures/gui/inventory_12x18.png";
    public static final String getInventoryTexture()
    {
    	if(ALL_ROWS == 12)
    		return "textures/gui/inventory_12x18.png";
    	else if(ALL_ROWS == 15)
    		return "textures/gui/inventory_15x25.png";
    	else return null;
    }
    
	public static final String NBT_SLOT = "Slot";
	public static final String NBT_PLAYER = "Player";
	public static final String NBT_WORLD = "World";
	public static final String NBT_ID = "ID";
	public static final String NBT_Settings = "Settings";
	public static final String NBT_Unlocked = "Unlocked";
	public static final String NBT_INVENTORY = "Inventory";
	public static final String NBT_INVOSIZE = "invoSize";

	public static final int square = 18;
	public final static int hotbarSize = 9;
	public final static int armorSize = 4; 
 
//	public static int MORE_ROWS = 9;
	public static int ALL_ROWS = 15; //12
	//public static int MORE_COLS = 9;
	public static int ALL_COLS = 18; //18
	private final static int SLOTS_PER_PAGE = ALL_COLS * ALL_ROWS;
	private final static int PAGES = 1;
	public final static int INVO_SIZE  = PAGES * SLOTS_PER_PAGE; 

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
