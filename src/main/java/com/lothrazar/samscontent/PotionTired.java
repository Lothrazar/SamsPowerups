package com.lothrazar.samscontent;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionTired extends Potion
{

	protected PotionTired(int potionID, ResourceLocation location,	boolean badEffect, int potionColor) 
	{
		super(potionID, location, badEffect, potionColor); 
	}
	//TODO: events: 
	/*@ForgeSubscribe
public void onEntityUpdate(LivingUpdateEvent event) 
{
     //entityLiving in fact refers to EntityLivingBase so to understand everything about this part go to EntityLivingBase instead
     if (event.entityLiving.isPotionActive(ModName.potionName)) 
     {
     //they have the potion on, so do what we need to
     flag = is block directly under player a water block?
if (flag && player.motionY < 0.0D) {
	player.posY += -player.motionY; // player is falling, so motionY is negative, but we want to reverse that
	player.motionY = 0.0D; // no longer falling
	player.fallDistance = 0.0F; // otherwise I believe it adds up, which may surprise you when you come down
}
*/
 

	public Potion setIconIndex(int par1, int par2) 
    {
        super.setIconIndex(par1, par2);
        return this;
    }
}
