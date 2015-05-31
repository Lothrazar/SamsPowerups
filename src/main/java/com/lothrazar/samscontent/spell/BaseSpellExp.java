package com.lothrazar.samscontent.spell;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public abstract class BaseSpellExp implements ISpell
{
	public abstract ISpell left();
	public abstract ISpell right();
	public abstract String getSpellID();
	public abstract void cast(World world, EntityPlayer player, BlockPos pos);
	public abstract ItemStack getIconDisplay();
	public abstract int getExpCost();
	
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		player.swingItem();
		
		Util.spawnParticle(world, EnumParticleTypes.CRIT, pos);

		Util.drainExp(player, getExpCost());
	}

	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{
		Util.playSoundAt(player, Reference.sounds.wood_click);

		Util.addChatMessage(player, Util.lang("spell.exp.missing")+this.getExpCost());
	}
	
	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		return (getExpCost() <= Util.getExpTotal(player)); 
	}
	
	@Override
	public ItemStack getIconDisplayHeader()
	{
		return new ItemStack(ItemRegistry.exp_cost_dummy);
	}
}
