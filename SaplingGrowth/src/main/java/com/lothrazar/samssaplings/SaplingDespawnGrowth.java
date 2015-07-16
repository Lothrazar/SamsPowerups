package com.lothrazar.samssaplings;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SaplingDespawnGrowth
{  
	public static List<Integer> oakBiomes = new ArrayList<Integer>();
	public static List<Integer> spruceBiomes = new ArrayList<Integer>();
	public static List<Integer> birchBiomes = new ArrayList<Integer>();
	public static List<Integer> jungleBiomes = new ArrayList<Integer>();
	public static List<Integer> darkoakBiomes = new ArrayList<Integer>();
	public static List<Integer> acaciaBiomes = new ArrayList<Integer>();

	public static boolean drop_on_failed_growth;
	public static boolean plantDespawningSaplings;
	public SaplingDespawnGrowth()
	{
	}

	
	private static final int sapling_oak = 0;
	private static final int sapling_spruce = 1;
	private static final int sapling_birch = 2;
	private static final int sapling_jungle = 3;
	private static final int sapling_acacia = 4;
	private static final int sapling_darkoak = 5;
	
	@SubscribeEvent
	public void onSaplingGrowTreeEvent(SaplingGrowTreeEvent event)
	{  
		Block b = event.world.getBlock(event.x,event.y,event.z);
		
		boolean treeAllowedToGrow = false;
		
		if(b == Blocks.sapling)//this may not always be true: such as trees added by Mods, so not a vanilla tree, but throwing same event
		{
			int meta = event.world.getBlockMetadata(event.x,event.y,event.z);//Blocks.sapling.getMetaFromState(event.world.getBlockState(event.pos));
			
			int biomeID = event.world.getBiomeGenForCoords(event.x,event.z).biomeID;

			int growth_data = 8;//0-5 is the type, then it adds on a 0x8  
			//and we know that it is always maxed out at ready to grow 8 since it is turning into a tree.
			
			int tree_type = meta - growth_data;
			 
			//IDS: http://www.minecraftforum.net/forums/minecraft-discussion/recent-updates-and-snapshots/381405-full-list-of-biome-ids-as-of-13w36b
			//as of 12 march 2015, it seems biome id 168 does not exist, so 167 is highest used (vanilla minecraft)
			switch(tree_type)
			{
			case sapling_acacia: 
				treeAllowedToGrow = acaciaBiomes.contains(biomeID); 
				break;
			case sapling_spruce: 
				treeAllowedToGrow = spruceBiomes.contains(biomeID); 
				break; 
			case sapling_oak: 
				treeAllowedToGrow = oakBiomes.contains(biomeID);  
				break;
			case sapling_birch: 
				treeAllowedToGrow = birchBiomes.contains(biomeID); 
				break;
			case sapling_darkoak: 
				treeAllowedToGrow = darkoakBiomes.contains(biomeID);
				break;
			case sapling_jungle: 
				treeAllowedToGrow = jungleBiomes.contains(biomeID);
				break;
				
			}
			
			if(treeAllowedToGrow == false)
			{
				event.setResult(Result.DENY);
				 
				//overwrite the sapling. - we could set to Air first, but dont see much reason to
				event.world.setBlock(event.x,event.y,event.z, Blocks.deadbush);//.getDefaultState()
				if(drop_on_failed_growth)
				{
					ModSaplings.dropItemStackInWorld(event.world, event.x,event.y,event.z, new ItemStack(Blocks.sapling,1,tree_type));
				}
				
			}  
		}//else a tree grew that was added by some mod
	}
	
	@SubscribeEvent
	public void onItemExpireEvent(ItemExpireEvent event)
	{  
		 if(plantDespawningSaplings == false) {return;}
		 
		 ItemStack is = event.entityItem.getEntityItem();
		 if(is == null ) {return;}//has not happened in the wild, yet

		 int x = (int)event.entityItem.posX,y=(int)event.entityItem.posY,z=(int)event.entityItem.posZ;
		 
		 Block blockhere = event.entity.worldObj.getBlock(x,y,z); 
		 Block blockdown = event.entity.worldObj.getBlock(x,y-1,z);
		   
		 if(blockhere == Blocks.air && 
			blockdown == Blocks.dirt || //includes podzol and such
			blockdown == Blocks.grass 
			)
		 {
			//plant the sapling, replacing the air and on top of dirt/plantable
			
			 if(Block.getBlockFromItem(is.getItem()) == Blocks.sapling)
			 {
				event.entity.worldObj.setBlock(x,y,z, Blocks.sapling);//.getStateFromMeta(is.getItemDamage())
					
				event.entity.worldObj.setBlockMetadataWithNotify(x,y,z, is.getItemDamage(), 2);
			 }
			 else if(Block.getBlockFromItem(is.getItem()) == Blocks.red_mushroom)	
				event.entity.worldObj.setBlock(x,y,z, Blocks.red_mushroom);
			 else if(Block.getBlockFromItem(is.getItem()) == Blocks.brown_mushroom)	
				event.entity.worldObj.setBlock(x,y,z, Blocks.brown_mushroom);
			 
		 } 
	} 
}
