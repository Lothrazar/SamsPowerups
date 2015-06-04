package com.lothrazar.samshorsefood;

import  net.minecraft.item.Item; 
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ClientProxy extends CommonProxy 
{   
    @Override
    public void registerRenderers() 
    {   
		//More info on proxy rendering
        //http://www.minecraftforge.net/forum/index.php?topic=27684.0
       //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
   
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name;
        Item item;
	  
        for(Item i : ItemRegistry.items)
        {  
        	name = TEXTURE_LOCATION + i.getUnlocalizedName().replaceAll("item.", "");

   			mesher.register(i, 0, new ModelResourceLocation( name , "inventory"));	 
        } 
    }

	public static final String TEXTURE_LOCATION = ModHorseFood.MODID + ":";
 
}
