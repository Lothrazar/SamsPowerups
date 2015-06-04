package com.lothrazar.carbonpaper;
 
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation; 

public class ClientProxy extends CommonProxy 
{   
    @Override
    public void registerRenderers() 
    {  
    	ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name =  ModCarbon.MODID + ":" + ModCarbon.carbon_paper.getUnlocalizedName().replaceAll("item.", "");

      	mesher.register(ModCarbon.carbon_paper, 0, new ModelResourceLocation( name , "inventory"));	 
          
    }
  
}
