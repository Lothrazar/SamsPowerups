 package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.SpellRegistry;
import com.lothrazar.samsmagic.potion.PotionRegistry;
import com.lothrazar.samsmagic.ModMain; 

public class SpellHaste extends BaseSpellExp implements ISpell
{
	private static int seconds = 20* 10; 
	 
	@Override
	public String getSpellID()
	{
		return "haste";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		ModMain.addOrMergePotionEffect(player,new PotionEffect(Potion.digSpeed.id,seconds,PotionRegistry.II));
		 
	}

	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		ModMain.playSoundAt(player, "random.drink");

		super.onCastSuccess(world, player, pos);
	}
	@Override
	public int getExpCost()
	{
		return 5;
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_haste_dummy);
	} 
	@Override
	public ISpell left()
	{
		return SpellRegistry.chest;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.waterwalk;
	}
}
