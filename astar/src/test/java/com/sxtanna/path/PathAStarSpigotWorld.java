package com.sxtanna.path;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * An example of implementing the abstract a* pathfinder within minecraft using the spigot api
 */
public final class PathAStarSpigotWorld extends PathAStar
{

	@NotNull
	private final World                 world;
	@NotNull
	private final Map<Block, NodeAStar> cache = new HashMap<>();


	public PathAStarSpigotWorld(@NotNull final Heuristic heuristic, @NotNull final Block s, @NotNull final Block e)
	{
		super(heuristic,
			  s.getX(), s.getY(), s.getZ(),
			  e.getX(), e.getY(), e.getZ());

		if (!s.getWorld().equals(e.getWorld()))
		{
			throw new IllegalArgumentException("Start and End Blocks must be in the same world!");
		}


		this.world = s.getWorld();

		this.cache.put(s, this.s);
		this.cache.put(e, this.e);
	}


	@Override
	public void kill()
	{
		super.kill();

		cache.clear();
	}


	@NotNull
	@Override
	public NodeAStar createNode(final int x, final int y, final int z)
	{
		return cache.computeIfAbsent(world.getBlockAt(x, y, z), block -> NodeAStar.of(block.getX(), block.getY(), block.getZ()));
	}


	@Override
	protected boolean checkNode(@NotNull final NodeAStar node)
	{
		final Block block = world.getBlockAt(node.getX(), node.getY(), node.getZ());
		final Block below = block.getRelative(BlockFace.DOWN);

		return CAN_WALK_THROUGH.contains(block.getType()) &&
			   CAN_WALK_OVERTOP.contains(below.getType()) || below.getType().isSolid();
	}


	private static final EnumSet<Material> CAN_WALK_THROUGH;
	private static final EnumSet<Material> CAN_WALK_OVERTOP;

	static
	{
		CAN_WALK_THROUGH = EnumSet.of(
				// basic
				Material.AIR,
				Material.CAVE_AIR,

				// crops
				Material.WHEAT,
				Material.SUGAR_CANE,
				Material.PUMPKIN_STEM,
				Material.ATTACHED_PUMPKIN_STEM,
				Material.MELON_STEM,
				Material.ATTACHED_MELON_STEM,
				Material.CARROTS,
				Material.POTATOES,
				Material.BEETROOTS,

				// grasses
				Material.GRASS,
				Material.TALL_GRASS,
				Material.FERN,
				Material.LARGE_FERN,
				Material.DEAD_BUSH,

				// saplings
				Material.OAK_SAPLING,
				Material.SPRUCE_SAPLING,
				Material.BIRCH_SAPLING,
				Material.JUNGLE_SAPLING,
				Material.ACACIA_SAPLING,
				Material.DARK_OAK_SAPLING,

				// flowers
				Material.DANDELION,
				Material.POPPY,
				Material.BLUE_ORCHID,
				Material.ALLIUM,
				Material.AZURE_BLUET,
				Material.RED_TULIP,
				Material.ORANGE_TULIP,
				Material.WHITE_TULIP,
				Material.PINK_TULIP,
				Material.OXEYE_DAISY,
				Material.CORNFLOWER,
				Material.LILY_OF_THE_VALLEY,
				Material.BROWN_MUSHROOM,
				Material.RED_MUSHROOM,
				Material.LILY_PAD,
				Material.LILAC,
				Material.ROSE_BUSH,
				Material.PEONY,

				// signs
				Material.OAK_SIGN,
				Material.SPRUCE_SIGN,
				Material.BIRCH_SIGN,
				Material.JUNGLE_SIGN,
				Material.ACACIA_SIGN,
				Material.DARK_OAK_SIGN,
				Material.OAK_WALL_SIGN,
				Material.SPRUCE_WALL_SIGN,
				Material.BIRCH_WALL_SIGN,
				Material.JUNGLE_WALL_SIGN,
				Material.ACACIA_WALL_SIGN,
				Material.DARK_OAK_WALL_SIGN,

				// carpets
				Material.WHITE_CARPET,
				Material.ORANGE_CARPET,
				Material.MAGENTA_CARPET,
				Material.LIGHT_BLUE_CARPET,
				Material.YELLOW_CARPET,
				Material.LIME_CARPET,
				Material.PINK_CARPET,
				Material.GRAY_CARPET,
				Material.LIGHT_GRAY_CARPET,
				Material.CYAN_CARPET,
				Material.PURPLE_CARPET,
				Material.BLUE_CARPET,
				Material.BROWN_CARPET,
				Material.GREEN_CARPET,
				Material.RED_CARPET,
				Material.BLACK_CARPET,

				// torches
				Material.TORCH,
				Material.WALL_TORCH,
				Material.REDSTONE_TORCH,
				Material.REDSTONE_WALL_TORCH
		);

		CAN_WALK_OVERTOP = EnumSet.of(
				// weird blocks
				Material.FARMLAND,
				Material.GRASS_PATH
		);
	}

}
