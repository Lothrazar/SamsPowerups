package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import scala.actors.threadpool.Arrays;
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
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CommandRecipe  implements ICommand
{
	public static boolean REQUIRES_OP;  
	public static int XP_COST_PER_PLACE; 
	private ArrayList<String> aliases = new ArrayList<String>();
	
	public CommandRecipe()
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
		return "recipe";
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
			return;
		}

    	//System.out.println("...searching  ");
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

		boolean wasShaped = false;
		ArrayList<Item> displayed = new ArrayList<Item>();
		
		ItemStack recipeResult;
		boolean isFirst = true;
		for (IRecipe recipe : recipes)
		{
		    recipeResult = recipe.getRecipeOutput();
		   
		    //compare ignoring stack size. not null, and the same item
			if( recipeResult == null || recipeResult.getItem() == null){continue;} 
		    if(held.getItem() == recipeResult.getItem() == false){continue;}
	    	
		    System.out.println("Found a matching recipe for this");
    		ItemStack is;
    		
    		if(isFirst == false)Util.addChatMessage(player, " --- ");
    		
		    if(recipe instanceof ShapedRecipes)
		    {
			    System.out.println("Printing ShapedRecipes  ");
		    	wasShaped=true;
		    	Util.addChatShapedRecipe(player,((ShapedRecipes)recipe).recipeItems);
		    }
		    else if(recipe instanceof ShapedOreRecipe)
		    {
		    	ShapedOreRecipe r = (ShapedOreRecipe)recipe;
			    System.out.println("Printing ShapedOreRecipe  "+r.getInput().length);

			    ItemStack[] recipeItems = new ItemStack[9];
		    	
		    	for(int i = 0; i < r.getInput().length; i++) 
		    	{
		    		Object o = r.getInput()[i];
		    		if(o == null){continue;}
		    		if(o instanceof ItemStack)
		    		{
		    			recipeItems[i] = (ItemStack)o;
		    		}
		    		else
		    		{
					    List<ItemStack> c = (List<ItemStack>)o;
					    	
					    if(c != null && c.size() > 0)
					    {
					    	recipeItems[i] = c.get(0);
					    }
					    else System.out.println("  cast failed its not an item stack list");
		    		}
		    	}
		    	
		    	Util.addChatShapedRecipe(player,recipeItems);
		    } 
		    else if(recipe instanceof ShapelessRecipes)
			{
			    System.out.println("Printing Shapeless");
		    	ShapelessRecipes r = (ShapelessRecipes)recipe;
 
		    	//ArrayList<String> list = new ArrayList<String>();
		    	ArrayList<ItemStack> recipeItems = new ArrayList<ItemStack>();
	    		
		    	for(int i = 0; i < r.recipeItems.size(); i++) 
		    	{
		    		Object o = r.recipeItems.get(i);
		    		if(o != null && o instanceof ItemStack)
		    		{
		    			recipeItems.add((ItemStack)o);
		    		}
		    	}
		    	

		    	Util.addChatShapelessRecipe(player,recipeItems);
	    		//Util.addChatMessage(player, "SHAPELESS " +String.join(" + ", list));
			}
		    else if(recipe instanceof ShapelessOreRecipe)
		    {
		    	ShapelessOreRecipe r = (ShapelessOreRecipe)recipe;
			    System.out.println("Printing ShapelessOreRecipe  "+r.getInput().size());
			    
			    ArrayList<ItemStack> recipeItems = new ArrayList<ItemStack>();
	    		
		    	for(int i = 0; i < r.getInput().size(); i++) 
		    	{
		    		Object o = r.getInput().get(i);
		    		if(o != null && o instanceof ItemStack)
		    		{
		    			recipeItems.add((ItemStack)o);
		    		}
		    		else
		    		{
					    List<ItemStack> c = (List<ItemStack>)o;
					    	
					    if(c != null && c.size() > 0)
					    {
					    	recipeItems.add(c.get(0));
					    }
					    else System.out.println("  cast failed its not an item stack list");
		    		}
		    	}

		    	Util.addChatShapelessRecipe(player,recipeItems);
		    }
		    else 
		    {
		    	
		    	Util.addChatMessage(player, "Recipe not found, class = " + recipe.getClass().getName());
		    	
		    }
		    
		    
	    	isFirst = false;
	    }//end main recipe loop
		if(wasShaped)
		{

			Util.addChatMessage(player, "0 1 2");
			Util.addChatMessage(player, "3 4 5");
			Util.addChatMessage(player, "6 7 8");
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
