package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ArmorRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
 

public class ItemEmeraldArmor extends ItemArmor 
{
	public String textureName;
	public ItemEmeraldArmor(int armorType) 
	{
		//int renderIndex = 0;
		super(ArmorRegistry.MATERIAL_EMERALD, 0, armorType);
		textureName = "emerald_armor";
		this.setCreativeTab(ModMain.tabSamsContent);
		/* public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
    this.textureName = textureName;
    this.setUnlocalizedName(unlocalizedName);
    this.setTextureName(Main.MODID + ":" + unlocalizedName);*/
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
	    return Reference.MODID + ":textures/models/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}
}
