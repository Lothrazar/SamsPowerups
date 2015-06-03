package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModSpells;
import com.lothrazar.samsmagic.SpellRegistry;
import com.lothrazar.samsmagic.entity.projectile.EntitySnowballBolt; 

public class SpellFrostbolt extends BaseSpellItem implements ISpell
{ 
	@Override
	public String getSpellID()
	{
		return "frostbolt";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		//BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();

		world.spawnEntityInWorld(new EntitySnowballBolt(world,player));
	}
	
	@Override
	public Item getItemCost()
	{
		return Items.snowball;
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_frostbolt_dummy);
	}
	
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		ModSpells.playSoundAt(player, "random.bow");

		super.onCastSuccess(world, player, pos);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.waterbolt;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.firebolt;
	}
}
