package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ModMain;
import com.lothrazar.samsmagic.SpellRegistry;
import com.lothrazar.samsmagic.potion.PotionRegistry; 

public class SpellSlowfall extends BaseSpellExp implements ISpell
{ 
	private static int seconds = 20 * 10;
	
	@Override
	public String getSpellID()
	{
		return "slowfall";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		ModMain.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.slowfall.id,seconds,0));
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
		return new ItemStack(Items.feather);
	}

	@Override
	public ISpell left()
	{
		return SpellRegistry.waterwalk;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.jump;
	}
}
