package com.lothrazar.event;

import java.util.ArrayList;
import java.util.List;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandlerSaplingDespawnGrowth
{  
	public List<Integer> oakBiomes = new ArrayList<Integer>();
	public List<Integer> spruceBiomes = new ArrayList<Integer>();
	public List<Integer> birchBiomes = new ArrayList<Integer>();
	public List<Integer> jungleBiomes = new ArrayList<Integer>();
	public List<Integer> darkoakBiomes = new ArrayList<Integer>();
	public List<Integer> acaciaBiomes = new ArrayList<Integer>();
	
	
	public HandlerSaplingDespawnGrowth()
	{
		oakBiomes.add(BiomeGenBase.forest.biomeID);
		oakBiomes.add(BiomeGenBase.forestHills.biomeID);
		oakBiomes.add(132);  //Flower Forest
		oakBiomes.add(BiomeGenBase.mesaPlateau.biomeID); //no trees in regular mesa, only plateaus 
		oakBiomes.add(BiomeGenBase.getBiome(166).biomeID) ;//mesa plateau F M 
		oakBiomes.add(BiomeGenBase.getBiome(167).biomeID) ;//mesa plateau M 
		oakBiomes.add(BiomeGenBase.jungle.biomeID);
		oakBiomes.add(BiomeGenBase.jungleEdge.biomeID) ;
		oakBiomes.add(BiomeGenBase.getBiome(151).biomeID) ;//jungle edge M
		oakBiomes.add(BiomeGenBase.getBiome(149).biomeID);//jungle M
		oakBiomes.add(BiomeGenBase.jungleHills.biomeID) ;
		oakBiomes.add(BiomeGenBase.swampland.biomeID) ;
		oakBiomes.add(BiomeGenBase.getBiome(134).biomeID) ;//Swampland M
		oakBiomes.add(BiomeGenBase.extremeHills.biomeID) ; 
		oakBiomes.add(BiomeGenBase.extremeHillsEdge.biomeID) ;
		oakBiomes.add(BiomeGenBase.extremeHillsPlus.biomeID) ;
		oakBiomes.add(BiomeGenBase.icePlains.biomeID); 
		
		acaciaBiomes.add(BiomeGenBase.savanna.biomeID);
		acaciaBiomes.add(BiomeGenBase.savannaPlateau.biomeID);
		acaciaBiomes.add(BiomeGenBase.mesaPlateau_F.biomeID);
		acaciaBiomes.add(BiomeGenBase.getBiome(163).biomeID); // Savanna M
		acaciaBiomes.add(BiomeGenBase.getBiome(164).biomeID);  // Savanna Plateau M
	  
			 
	}
	
	@SubscribeEvent
	public void onSaplingGrowTreeEvent(SaplingGrowTreeEvent event)
	{ 
		//IDEA: we could put biome/sapling pairs in config file and use 		BiomeGenBase.getBiome(int)
		//AND OR: put field for saplings/biomes added by other mods
		
		BiomeGenBase biome = event.world.getBiomeGenForCoords(event.pos);
		 
		Block b = event.world.getBlockState(event.pos).getBlock();
		
		boolean treeAllowedToGrow = false;
		
		if(b == Blocks.sapling)//this may not always be true: such as trees added by Mods, so not a vanilla tree, but throwing same event
		{
			int meta = Blocks.sapling.getMetaFromState(event.world.getBlockState(event.pos));

			int biomeID = event.world.getBiomeGenForCoords(event.pos).biomeID;
			
			int growth_data = 8;//0-5 is the type, then it adds on a 0x8  
			//and we know that it is always maxed out at ready to grow 8 since it is turning into a tree.
			
			int tree_type = meta - growth_data;
			 
			//IDS: http://www.minecraftforum.net/forums/minecraft-discussion/recent-updates-and-snapshots/381405-full-list-of-biome-ids-as-of-13w36b
			//as of 12 march 2015, it seems biome id 168 does not exist, so 167 is highest used (vanilla minecraft)
			switch(tree_type)
			{
			case Reference.sapling_acacia:
				
				treeAllowedToGrow = acaciaBiomes.contains(biome);
		
				break;
			case Reference.sapling_spruce:

				treeAllowedToGrow = (biome == BiomeGenBase.taiga) ||
					(biome == BiomeGenBase.taigaHills) ||
					(biome == BiomeGenBase.megaTaiga) ||
					(biome == BiomeGenBase.getBiome(160)) ||//megasprucetaiga
					(biome == BiomeGenBase.getBiome(161)) ||//megasprucetaiga M
					(biome == BiomeGenBase.megaTaigaHills) || 
					(biome == BiomeGenBase.coldTaiga) ||
					(biome == BiomeGenBase.coldTaigaHills) || 
					(biome == BiomeGenBase.getBiome(158)) || //?cold tagia M, 158?
					(biome == BiomeGenBase.extremeHills) || 
					(biome == BiomeGenBase.extremeHillsEdge) || 
					(biome == BiomeGenBase.extremeHillsPlus) || 
					(biome == BiomeGenBase.jungle) ||
					(biome == BiomeGenBase.icePlains) ||
					(biome == BiomeGenBase.iceMountains);
				break; 
			case Reference.sapling_oak:

				treeAllowedToGrow = (oakBiomes.contains(biome));
				/*(biome == BiomeGenBase.forest) ||
					(biome == BiomeGenBase.forestHills) ||
					(biome == BiomeGenBase.getBiome(132)) ||//Flower Forest
					(biome == BiomeGenBase.mesaPlateau) || //the only mesa with oak trees, not others
					(biome == BiomeGenBase.getBiome(166)) ||//mesa plateau F M 
					(biome == BiomeGenBase.getBiome(167)) ||//mesa plateau M 
					(biome == BiomeGenBase.jungle) ||
					(biome == BiomeGenBase.jungleEdge) ||
					(biome == BiomeGenBase.getBiome(151)) ||//jungle edge M
					(biome == BiomeGenBase.getBiome(149)) ||//jungle M
					(biome == BiomeGenBase.jungleHills) ||
					(biome == BiomeGenBase.swampland) ||
					(biome == BiomeGenBase.getBiome(134)) ||//Swampland M
					(biome == BiomeGenBase.extremeHills) || 
					(biome == BiomeGenBase.extremeHillsEdge) || 
					(biome == BiomeGenBase.extremeHillsPlus) || 
					(biome == BiomeGenBase.icePlains);*/
				break;
			case Reference.sapling_birch:

				treeAllowedToGrow = (biome == BiomeGenBase.birchForest)  || 
					(biome == BiomeGenBase.birchForestHills) || 
					(biome == BiomeGenBase.getBiome(155)) || //Birch forest M
					(biome == BiomeGenBase.getBiome(156)) || //Birch forest hills M
					(biome == BiomeGenBase.forest) ||
					(biome == BiomeGenBase.forestHills) ||
					(biome == BiomeGenBase.getBiome(132));//Flower Forest;
				
				break;
			case Reference.sapling_darkoak:

				treeAllowedToGrow = (biome == BiomeGenBase.roofedForest) || 
						(biome == BiomeGenBase.getBiome(157)); // Roofed Forest M
				break;
			case Reference.sapling_jungle:

				treeAllowedToGrow = (biome == BiomeGenBase.jungle) ||
					(biome == BiomeGenBase.jungleEdge) ||
					(biome == BiomeGenBase.getBiome(151)) ||//jungle edge M
					(biome == BiomeGenBase.getBiome(149)) ||//jungle M
					(biome == BiomeGenBase.jungleHills);
				break;
				
				//no saplings for:
				//frozen river 
				//ice plains spikes 
				//cold beach
				//stone beach
				//the end
				//plains
				//sunflower plains
				//river
				//beach
				//mooshroom island (14)
				//mooshroom island shore (15)
				//desert (2)
				//desert M (130)
				//mesa (37)
				// mesa bryce (165)
				// plateaus (36 38 39)
				//nether
				//sky (the end)
				//ocean
				//deepocean
				//frozen ocean(10N  
				
				
				//TODO: config file to add option for nether/the end trees, even if we DONT open up every biome on its own
				
			}
			
			//TODO: can we prevent cactus growth
			//TODO: can we prevent reeds growth
			//TODO: can we prevent vine growth
			//TODO: can we increase vine growth on swampland trees
			//TODO: can we generate mushroom/flower/lilypad growth
			 
			if(treeAllowedToGrow == false)
			{
				event.setResult(Result.DENY);
				 
				//overwrite the sapling. - we could set to Air first, but dont see much reason to
				event.world.setBlockState(event.pos, Blocks.deadbush.getDefaultState());
				
				SamsUtilities.dropItemStackInWorld(event.world, event.pos, new ItemStack(Blocks.sapling,1,tree_type));
			}  
		}//else a tree grew that was added by some mod
	}
	
	@SubscribeEvent
	public void onItemExpireEvent(ItemExpireEvent event)
	{  
		 if(ModLoader.configSettings.plantDespawningSaplings == false) {return;}
		 
		 ItemStack is = event.entityItem.getEntityItem();
		 if(is != null )
		 { 
			 Block blockhere = event.entity.worldObj.getBlockState(event.entityItem.getPosition()).getBlock(); 
			 Block blockdown = event.entity.worldObj.getBlockState(event.entityItem.getPosition().down()).getBlock();
			   
			if(blockhere == Blocks.air && 
				blockdown == Blocks.dirt || //includes podzol and such
				blockdown == Blocks.grass 
				)
			{
				//plant the sapling, replacing the air and on top of dirt/plantable
				
				if(Block.getBlockFromItem(is.getItem()) == Blocks.sapling)
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.sapling.getStateFromMeta(is.getItemDamage()));
				else if(Block.getBlockFromItem(is.getItem()) == Blocks.red_mushroom)	
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.red_mushroom.getDefaultState());
				else if(Block.getBlockFromItem(is.getItem()) == Blocks.brown_mushroom)	
					event.entity.worldObj.setBlockState(event.entityItem.getPosition(), Blocks.brown_mushroom.getDefaultState());
				
					
						
			
			}
		 }
	} 
}
