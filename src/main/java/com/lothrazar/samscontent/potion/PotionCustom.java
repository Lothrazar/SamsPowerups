package com.lothrazar.samscontent.potion;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionCustom extends Potion
{ 
	protected PotionCustom(int potionID, ResourceLocation location,	boolean badEffect, int potionColor) 
	{
		super(potionID, location, badEffect, potionColor); 
	}
	
	public Potion setIconIndex(int par1, int par2) 
    {
        super.setIconIndex(par1, par2);
        return this;
    }
}
