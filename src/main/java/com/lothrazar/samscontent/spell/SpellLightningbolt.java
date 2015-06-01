package com.lothrazar.samscontent.spell;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellLightningbolt extends BaseSpellExp implements ISpell
{ 
	/*TODO: if i cast this too fast (spam) then i get 
	 * java.util.ConcurrentModificationException
	at java.util.HashMap$HashIterator.nextNode(Unknown Source) ~[?:1.8.0_45]
	at java.util.HashMap$KeyIterator.next(Unknown Source) ~[?:1.8.0_45]
	at net.minecraft.entity.EntityTracker.updateTrackedEntities(EntityTracker.java:270) ~[EntityTracker.class:?]
	at net.minecraft.server.MinecraftServer.updateTimeLightAndEntities(MinecraftServer.java:715) ~[MinecraftServer.class:?]
	at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:598) ~[MinecraftServer.class:?]
	at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:164) ~[IntegratedServer.class:?]
	at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:478) [MinecraftServer.class:?]
	at java.lang.Thread.run(Unknown Source) [?:1.8.0_45]
	
	is not bug in my code. spawning new entity makes the list update, so going to fast will do this
	
	
	TODO for fixing: add a global spell cooldown (possibly one per spell)
	and in the canCast, we check that the global cooldown is zero (0)
	if zero, cast
	if not zero, cancel with message
	
	need Tick handler to --1
	*/
	@Override
	public String getSpellID()
	{
		return "lightningbolt";
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityLightningballBolt(world,player 	 ));
	}

	@Override
	public int getExpCost()
	{
		return 300;
	}
 
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_lightning_dummy);
	}
	@Override
	public ISpell left()
	{
		return SpellRegistry.firebolt;
	}

	@Override
	public ISpell right()
	{
		return SpellRegistry.soulstone;
	}
}
