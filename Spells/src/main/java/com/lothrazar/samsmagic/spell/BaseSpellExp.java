package com.lothrazar.samsmagic.spell;

import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModMain;
import com.lothrazar.samsmagic.SpellRegistry; 

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
		
		ModMain.spawnParticle(world, EnumParticleTypes.CRIT, pos);

		ModMain.drainExp(player, getExpCost());
	}

	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{
		ModMain.playSoundAt(player, "random.wood_click");

		ModMain.addChatMessage(player, ModMain.lang("spell.exp.missing")+this.getExpCost());
	}
	
	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		return (getExpCost() <= ModMain.getExpTotal(player)); 
	}
	
	@Override
	public ItemStack getIconDisplayHeader()
	{
		return new ItemStack(ItemRegistry.exp_cost_dummy);
	}
}
