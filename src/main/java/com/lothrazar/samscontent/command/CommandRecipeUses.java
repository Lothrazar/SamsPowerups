package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.util.Util;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
		Util.addChatMessage(player, "0 1 2");
		Util.addChatMessage(player, "3 4 5");
		Util.addChatMessage(player, "6 7 8");
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

		ArrayList<Item> displayed = new ArrayList<Item>();
		
		ItemStack recipeResult;
		for (IRecipe recipe : recipes)
		{
		    recipeResult = recipe.getRecipeOutput();

		    //compare ignoring stack size. not null, and the same item
			if( recipeResult == null || recipeResult.getItem() == null){continue;} 
		    if(held.getItem() == recipeResult.getItem() == false){continue;}
	    	
    		ItemStack is;
    		
		    if(recipe instanceof ShapedRecipes)
		    {
		    	ShapedRecipes r = (ShapedRecipes)recipe;
		    	
		    	//System.out.println("recipeItems  "+r.recipeItems.length);

	    		Util.addChatMessage(player, recipeResult.getDisplayName());
		    	for(int i = 0; i < r.recipeItems.length; i++)
		    	{
		    		is = r.recipeItems[i];
		    		if(is != null)
		    			Util.addChatMessage(player, i+" : "+is.getDisplayName());
		    	}
		    }
		    else if(recipe instanceof ShapelessRecipes)
			{
		    	ShapelessRecipes r = (ShapelessRecipes)recipe;
 
	    		Util.addChatMessage(player, recipeResult.getDisplayName());
	    		
		    	for(int i = 0; i < r.recipeItems.size(); i++) 
		    	{
		    		Object o = r.recipeItems.get(i);
		    		if(o == null || !(o instanceof ItemStack)){continue;}
		    		
		    		is = (ItemStack)o;//insde the ShapelessRecipes class, they always cast it
		    		
		    		Util.addChatMessage(player, is.getDisplayName());
		    		
		    	}
			}
		    
    		Util.addChatMessage(player, " --- ");
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
