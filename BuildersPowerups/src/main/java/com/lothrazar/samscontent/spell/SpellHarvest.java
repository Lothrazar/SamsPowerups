package com.lothrazar.samscontent.spell;

import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.entity.projectile.EntityHarvestbolt;
import com.lothrazar.samscontent.entity.projectile.EntityWaterBolt;
import com.lothrazar.samscontent.spell.ISpell;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.util.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper; 
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class SpellHarvest extends BaseSpellExp implements ISpell
{
	@Override
	public String getSpellID()
	{ 
		return "harvest";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityHarvestbolt(world,player)); 
	}

	public static int harvestArea(World world, EntityPlayer player, BlockPos pos, int radius)
	{
		int x = (int)player.posX;
		int y = (int)player.posY;
		int z = (int)player.posZ;
		
		//search in a cube
		int xMin = x - radius;
		int xMax = x + radius; 
		int zMin = z - radius;
		int zMax = z + radius;
		
		int eventy = pos.getY();
		
		BlockPos posCurrent;
		
		int countHarvested = 0;
		
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				posCurrent = new BlockPos(xLoop, eventy, zLoop);
				IBlockState bs = world.getBlockState(posCurrent);
				Block blockCheck = bs.getBlock(); 

				if(blockCheck instanceof IGrowable)
				{ 
					IGrowable plant = (IGrowable) blockCheck;

					if(plant.canGrow(world, posCurrent, bs, world.isRemote) == false)
					{  
						if(world.isRemote == false)  //only drop items in serverside
							world.destroyBlock(posCurrent, true);
						//break fully grown, plant new seed
						world.setBlockState(posCurrent, blockCheck.getDefaultState());//this plants a seed. it is not 'hay_block'
					
						countHarvested++;
					} 
				} 
			}  
		} //end of the outer loop
		return countHarvested;
	}

 
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.bowtoss);

	//	if(world.isRemote) //client side 
			//Util.spawnParticle(world, EnumParticleTypes.VILLAGER_HAPPY, pos);//cant find the Bonemeal particles 
		 
		super.onCastSuccess(world, player, pos);
	}
	
	@Override
	public ItemStack getIconDisplay()
	{ 
		return new ItemStack(ItemRegistry.spell_harvest_dummy);
	}

	@Override
	public int getExpCost()
	{
		return 75;
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.soulstone;
	}

	@Override
	public ISpell right()
	{
		return null;
	}
}
