package com.lothrazar.samscontent.command;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier; 
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

		/*
		//TODO: a help/expl mode?
			Util.addChatMessage(player, "0 1 2");
			Util.addChatMessage(player, "3 4 5");
			Util.addChatMessage(player, "6 7 8");
		*/
		ItemStack held = player.inventory.getCurrentItem();
		
		if(held == null && world.isRemote)
		{
			Util.addChatMessage(player, "command.recipes.empty");
			return;
		}

		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();

		ItemStack recipeResult;

		for (IRecipe recipe : recipes)
		{
		    recipeResult = recipe.getRecipeOutput();
		   
		    //compare ignoring stack size. not null, and the same item
			if( recipeResult == null || recipeResult.getItem() == null){continue;} 
		    if(held.getItem() != recipeResult.getItem()){continue;}
		    if(held.getMetadata() != recipeResult.getMetadata()){continue;}

			//TODO  seperator btw recipes: one item can have multiple.
		    
		    //for each recipe, we need an array/list of the input, then we pass it off to get printed
		    //recipe is either shaped or shapeless
		    //on top of that, some use Forge ore dictionary, and some dont
		    //so 4 cases total
		    
		    //TODO: refactor and use  ItemStack[] ingred = Util.getRecipeInput(recipe);
		    //to save reuse
		    
		    if(recipe instanceof ShapedRecipes)
		    { 
		    	ShapedRecipes r = ((ShapedRecipes)recipe);
		    	boolean isInventory = (r.recipeHeight < 3 && r.recipeWidth < 3);
		    	
		    	//System.out.println("isInventory is from : "+r.recipeHeight+" "+r.recipeWidth);
		    	
		    	Util.addChatShapedRecipe(player, Util.getRecipeInput(recipe), isInventory);
		    }
		    else if(recipe instanceof ShapedOreRecipe)
		    {
		    	ShapedOreRecipe r = (ShapedOreRecipe)recipe;
			
			    ItemStack[] recipeItems = Util.getRecipeInput(recipe);// new ItemStack[r.getInput().length];
	
		    	//only because r.width//r.height is private
		    	boolean isInventory = false;
		    	for(Field f : ShapedOreRecipe.class.getDeclaredFields())
		    	{
			        f.setAccessible(true);
			        //works since we know that the only integers in the class are the width/height
			        if(f.getType() == int.class)
			        {
			        	try
						{
							isInventory = isInventory || f.getInt(r) < 3;
						}
						catch (IllegalArgumentException e)
						{
							e.printStackTrace();
						}
						catch (IllegalAccessException e)
						{
							e.printStackTrace();
						}
			        }
		    	}
		    	
		    	Util.addChatShapedRecipe(player,recipeItems, isInventory);
		    } 
		    else if(recipe instanceof ShapelessRecipes || recipe instanceof ShapelessOreRecipe)
			{
		    	Util.addChatShapelessRecipe(player,Util.getRecipeInput(recipe));
		    }
		    else 
		    {
		    	//TODO: furnace?
		    	//TODO: brewing stand?
		    	
		    	//for example, if its from some special crafting block/furnace from another mod
		    	Util.addChatMessage(player, "Recipe type not supported, class = " + recipe.getClass().getName());
		    	
		    }
	    }//end main recipe loop
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
