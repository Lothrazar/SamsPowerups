package com.lothrazar.samsmagic.potion;

import com.lothrazar.samsmagic.ModSpells;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCustom extends Potion
{ 
	protected PotionCustom(int potionID, ResourceLocation location,	boolean badEffect, int potionColor) 
	{
		super(potionID, location, badEffect, potionColor); 
	}
	@Override
	public Potion setIconIndex(int par1, int par2) 
    {
        super.setIconIndex(par1, par2);
        return this;
    }
	private static final ResourceLocation field_110839_f = new ResourceLocation(ModSpells.MODID,"/textures/items/apple_ender.png");
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() 
	{
	    Minecraft.getMinecraft().renderEngine.getTexture(field_110839_f);
	   //Minecraft.getMinecraft().renderEngine.getTexture(field_110839_f);
	    return false;
	}
}
