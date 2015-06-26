package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry;
/*
public class SpellSoulstone extends BaseSpellExp implements ISpell
{
	
	@Override
	public String getSpellID()
	{
		return "soulstone";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		world.spawnEntityInWorld(new EntitySoulstoneBolt(world,player 	 ));
	}
	

	
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		ModSpells.spawnParticle(world, EnumParticleTypes.PORTAL, pos);

		ModSpells.playSoundAt(player, "random.bow");

		super.onCastSuccess(world, player, pos);
	}
 
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.soulstone);
	}

	@Override
	public int getExpCost()
	{
		return ModSpells.cfg.soulstone;
	}
	
	public static void onLivingHurt(LivingHurtEvent event) 
	{
		//called from ModMain event handler
		//thanks for the help:
		//http://www.minecraftforge.net/forum/index.php?topic=7475.0
		
		if(event.entityLiving.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{  
			float amount = event.ammount;//yes there is a typo in the word 'amount' but it is not in my code  
			
			if(event.entityLiving.getHealth() - amount <= 0)
			{ 
				event.entityLiving.heal(40);
				
				//event.setCanceled(true);//this is possible but not needed
				
				ModSpells.teleportWallSafe(event.entityLiving, event.entity.worldObj,  event.entity.worldObj.getSpawnPoint());

				//boolean isPersist = event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_PERSIST;
				 
				if(event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_SINGLEUSE)
				{
					//if it does not persist, then consume this use
					event.entityLiving.getEntityData().setInteger(KEY_STONED, VALUE_EMPTY);
				} 
			}
		} 
	} 

}*/
