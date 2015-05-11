package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandRecipeUses  implements ICommand
{
	public static boolean REQUIRES_OP;  
	public static int XP_COST_PER_PLACE; 
	private ArrayList<String> aliases = new ArrayList<String>();
	
	public CommandRecipeUses()
	{
		this.aliases.add(getName().toUpperCase());
	}
	@Override
	public int compareTo(Object o)
	{
		return 0;
	}

	@Override
	public String getName()
	{
		return "recipes";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/"+getName();
	}

	@Override
	public List getAliases()
	{
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)		throws CommandException
	{ 
		World world = sender.getEntityWorld();
		if(! (sender instanceof EntityPlayer)){return;}//does not work from command blocks and such
		
		EntityPlayer player = (EntityPlayer)sender;
		
		ItemStack held = player.inventory.getCurrentItem();
		
		if(held == null && world.isRemote)
		{
			Util.addChatMessage(player, "command.recipes.empty");
		}

    	System.out.println("...searching  ");
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		IRecipe recipe;
		ItemStack recipeResult;
		for (int i = 0; i < recipes.size(); i++)
		{
		    recipe = recipes.get(i);
        
		    recipeResult = recipe.getRecipeOutput();
		    
			if( recipeResult == null){continue;}
		    
		   // System.out.println( recipeResult.getDisplayName());
		 
		    //if(ItemStack.areItemStacksEqual(held, recipeResult) == false) {continue;}
		 
		    //compare ignoring stack size
		    if(held.getItem() == recipeResult.getItem() == false){continue;}
	    	//TODO: need a Util.toRecipeString(tmpRecipe);...perhaps...


    		Util.addChatMessage(player, recipeResult.getDisplayName());
	    	//System.out.println(recipeResult.getDisplayName());
	 
		    if(recipe instanceof ShapedRecipes)
		    {
		    	ShapedRecipes r = (ShapedRecipes)recipe;
		    	
		    	 
		    	//System.out.println("recipeHeight "+r.recipeHeight);
		    	//System.out.println("recipeWidth "+r.recipeWidth);
		    	System.out.println("recipeItems  "+r.recipeItems.length);
		    	
		    	for(ItemStack is : r.recipeItems)
		    	{

		    		Util.addChatMessage(player, is.getDisplayName());
		    	}
		    }
		    else if(recipe instanceof ShapelessRecipes)
			{
		    	ShapelessRecipes r = (ShapelessRecipes)recipe;

		    	//System.out.println("shapeless");
		    	
		    	System.out.println("SHAPELESS recipeItems  "+r.recipeItems.size());
		    	
		    	for(Object o : r.recipeItems)
		    	{
		    		if(!(o instanceof ItemStack)){continue;}
		    		
		    		ItemStack is = (ItemStack)o;//insde the ShapelessRecipes class, they always cast it
		    		
		    		Util.addChatMessage(player, is.getDisplayName());
		    		
		    	}
			}
		   // break;//because it was found
	    }
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender)
	{ 
		return (REQUIRES_OP) ? sender.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args,	BlockPos pos)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

}
