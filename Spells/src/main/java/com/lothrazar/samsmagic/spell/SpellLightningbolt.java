package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry;
import com.lothrazar.samsmagic.entity.projectile.EntityLightningballBolt; 

public class SpellLightningbolt extends BaseSpellExp implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "lightningbolt";
	}

	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		ModSpells.playSoundAt(player, "random.bow");

		super.onCastSuccess(world, player, pos);
	}
	
	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityLightningballBolt(world,player 	 ));
	}

	@Override
	public int getExpCost()
	{
		return ModSpells.cfg.lightningbolt;
	}
 
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_lightning_dummy);
	}
}
