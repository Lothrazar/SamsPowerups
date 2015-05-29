package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellHaste extends BaseSpell implements ISpell
{
	private static int fiveSeconds = Reference.TICKS_PER_SEC * 5;//TODO : config? reference? cost?
	 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.haste;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		Util.addOrMergePotionEffect(player,new PotionEffect(Potion.digSpeed.id,fiveSeconds,0));
		 
	}
 
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(Items.sugar);
	} 
}
