package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModMain;
import com.lothrazar.samsmagic.SpellRegistry;
import com.lothrazar.samsmagic.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samsmagic.entity.projectile.EntityWaterBolt; 

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
		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();

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
		ModMain.playSoundAt(player, "random.bow");

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
