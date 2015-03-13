package com.lothrazar.samscontent;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionTired extends Potion
{ 
	protected PotionTired(int potionID, ResourceLocation location,	boolean badEffect, int potionColor) 
	{
		super(potionID, location, badEffect, potionColor); 
	}
	
	public Potion setIconIndex(int par1, int par2) 
    {
        super.setIconIndex(par1, par2);
        return this;
    }
}
