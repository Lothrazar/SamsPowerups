package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.SpellRegistry.EnumHudType;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.common.PlayerPowerups;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellHud extends BaseSpell implements ISpell
{ 
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.hud;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		PlayerPowerups props = PlayerPowerups.get(player);
		
		String hudCurr = props.getStringHUD();
		//if(hudCurr == null || hudCurr=="") hudCurr = EnumHudType.none.name();
		
		EnumHudType hudCurrent = null;
		EnumHudType hudNew = null;
		
		try{
			hudCurrent = EnumHudType.valueOf(hudCurr);//always crashes if empty
		}
		catch(Exception e)
		{
			hudCurrent = EnumHudType.none;
		}
		
		switch(hudCurrent)
		{
		case none: 
			hudNew = EnumHudType.clock;
		break;

		case clock: 
			hudNew = EnumHudType.compass;
		break;

		case compass: 
			hudNew = EnumHudType.both;
		break;

		case both: 
			hudNew = EnumHudType.none;
		break;
		default:
			hudNew = EnumHudType.none;
			break;
		}
		
		props.setStringHUD(hudNew.name());
 
	}

	@Override
	public int getExpCost()
	{
		return 1;
	}
 
	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

	@Override
	public ItemStack getIconDisplay()
	{ 
		return new ItemStack(Items.map);
	}

}
