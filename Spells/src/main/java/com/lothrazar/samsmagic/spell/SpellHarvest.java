package com.lothrazar.samsmagic.spell;

import com.lothrazar.samsmagic.entity.projectile.EntityHarvestbolt;
import com.lothrazar.samsmagic.spell.ISpell;
import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry; 
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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
		//int y = (int)player.posY;
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
		ModSpells.playSoundAt(player, "random.bow");

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
