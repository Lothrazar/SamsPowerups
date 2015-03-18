package com.lothrazar.samscontent.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.proxy.ClientProxy;
import com.lothrazar.samskeyslider.MessageKeyPressed;

public class HandlerKeyInput 
{ 
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
        if(ClientProxy.keyShiftUp.isPressed() )
        { 	     
        	 ModLoader.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftUp.getKeyCode()));  
        }        
        else if(ClientProxy.keyShiftDown.isPressed() )
        { 	      
        	 ModLoader.network.sendToServer( new MessageKeyPressed(ClientProxy.keyShiftDown.getKeyCode()));  
        }      
        else if(ClientProxy.keyBarDown.isPressed() )
        { 	      
        	 ModLoader.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBarDown.getKeyCode()));  
        }  
        else if(ClientProxy.keyBarUp.isPressed() )
        { 	      
        	 ModLoader.network.sendToServer( new MessageKeyPressed(ClientProxy.keyBarUp.getKeyCode()));  
        }  
    } 
}
