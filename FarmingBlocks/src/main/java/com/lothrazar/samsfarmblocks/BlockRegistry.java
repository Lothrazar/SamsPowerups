package com.lothrazar.samsfarmblocks;

import java.util.ArrayList;
 
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry; 
 
public class BlockRegistry 
{  
	public static BlockFishing block_fishing ; 
	public static BlockShearWool block_shear_sheep;
	public static BlockFragile block_fragile;
	//store blocks in a list - because this is used by the ModelMesher in the client proxy later
	public static ArrayList<Block> blocks = new ArrayList<Block>();
	
	public static void registerBlock(Block s, String name)
	{    
		s.setUnlocalizedName(name); 
		 
		GameRegistry.registerBlock(s, name);
		  
		blocks.add(s);
	}

	public static void registerBlocks() 
	{  
		block_fragile = new BlockFragile();
		BlockRegistry.registerBlock(block_fragile, "block_fragile"); 
		block_fragile.addRecipe();
  
		BlockRegistry.block_shear_sheep = new BlockShearWool(); 
		BlockRegistry.registerBlock(BlockRegistry.block_shear_sheep, "block_shear_sheep");
		BlockShearWool.addRecipe();
 
		BlockRegistry.block_fishing = new BlockFishing(); 
		registerBlock(BlockRegistry.block_fishing, "block_fishing");
		BlockFishing.addRecipe();
	}
}
