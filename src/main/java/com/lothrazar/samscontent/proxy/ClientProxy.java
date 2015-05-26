package com.lothrazar.samscontent.proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.input.Keyboard;   
import  net.minecraft.item.Item;
import com.lothrazar.samscontent.BlockRegistry;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy 
{  
	public static KeyBinding keyShiftUp;
	public static KeyBinding keyShiftDown; 
	public static KeyBinding keyBarUp;
	public static KeyBinding keyBarDown; 
	public static KeyBinding keyBindMacro1;
	public static KeyBinding keyBindMacro2;
	public static KeyBinding keyPush;
	public static KeyBinding keyPull;
	public static KeyBinding keyBindEnder;
	public static KeyBinding keyTransform;
	 
	public static KeyBinding keySpell1;
	public static KeyBinding keySpell2;
	public static KeyBinding keySpell3;
 
    @Override
    public void registerRenderers() 
    {  
    	registerKeyBindings(); 

        registerModels(); 
    }

	private void registerModels() 
	{
		//More info on proxy rendering
        //http://www.minecraftforge.net/forum/index.php?topic=27684.0
       //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
   
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name;
        Item item;
	 
        for(Block b : BlockRegistry.blocks)
        { 
        	item = Item.getItemFromBlock(b);
        	name = Reference.TEXTURE_LOCATION + b.getUnlocalizedName().replaceAll("tile.", "");

   			mesher.register(item, 0, new ModelResourceLocation( name , "inventory"));	 
        }
         
        for(Item i : ItemRegistry.items)
        {  
        	name = Reference.TEXTURE_LOCATION + i.getUnlocalizedName().replaceAll("item.", "");

   			mesher.register(i, 0, new ModelResourceLocation( name , "inventory"));	 
        }
        if(ModMain.cfg.respawn_egg)
        {
        	for(Object key : EntityList.entityEggs.keySet())
            {
            	mesher.register(ItemRegistry.respawn_egg, (Integer)key, new ModelResourceLocation(Reference.TEXTURE_LOCATION + "respawn_egg" , "inventory"));	 
            }
        }
	}

	private void registerKeyBindings() 
	{
		//bindings = new ArrayList<KeyBinding>();
		
		keyShiftUp = new KeyBinding(Reference.keyUpName, Keyboard.KEY_C, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftUp);
        //bindings.add(ClientProxy.keyShiftUp)
     
		keyShiftDown = new KeyBinding(Reference.keyDownName, Keyboard.KEY_V, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyShiftDown); 

        keyBarUp = new KeyBinding(Reference.keyBarUpName, Keyboard.KEY_N, Reference.keyCategoryInventory);
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarUp);
         
        keyBarDown = new KeyBinding(Reference.keyBarDownName, Keyboard.KEY_M, Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBarDown);

        keyBindEnder = new KeyBinding(Reference.keyBindEnderName, Keyboard.KEY_I,  Reference.keyCategoryInventory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindEnder);
        
        keyBindMacro1 = new KeyBinding(Reference.keyBind1Name, Keyboard.KEY_Y, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindMacro1);

        keyBindMacro2 = new KeyBinding(Reference.keyBind2Name, Keyboard.KEY_U, Reference.keyCategoryMacro); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindMacro2);

        keyPush = new KeyBinding(Reference.keyPushName, Keyboard.KEY_L, Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPush);

        keyPull = new KeyBinding(Reference.keyPullName, Keyboard.KEY_O,  Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPull);
         
        keyTransform = new KeyBinding(Reference.keyTransformName, Keyboard.KEY_P, Reference.keyCategoryBlocks); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyTransform);

        keySpell1 = new KeyBinding(Reference.keySpell1Name, Keyboard.KEY_H, Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keySpell1);

        keySpell2 = new KeyBinding(Reference.keySpell2Name, Keyboard.KEY_J, Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keySpell2);

        keySpell3 = new KeyBinding(Reference.keySpell3Name, Keyboard.KEY_K,  Reference.keyCategorySpell); 
        ClientRegistry.registerKeyBinding(ClientProxy.keySpell3);
        
         
	} 

	public static String getKeyDescription(int key)
	{
		//getKeyDescription gets something like 'key.macro1' like lang file data
		
		//thanks http://stackoverflow.com/questions/10893455/getting-keytyped-from-the-keycode
	 
		KeyBinding binding = null;
		switch(key)//TODO:...maybe find better way. switch for now
		{
		case 1:
			binding = keyBindMacro1;
			break;
		case 2:
			binding = keyBindMacro2;
			break;
		}
		
		 
		if(binding == null)
			return "";
		else
			return GameSettings.getKeyDisplayString(binding.getKeyCode());
			//return I18n.format(binding.getKeyDescription(), new Object[0]);
			//return java.awt.event.KeyEvent.getKeyText(binding.getKeyCode());
	}
}
