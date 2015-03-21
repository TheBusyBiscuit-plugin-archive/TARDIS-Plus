package me.mrCookieSlime.TARDISPlus.TARDIS;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class TARDISWorldGenerator extends ChunkGenerator {
	
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, 0, 0);
	}
	
	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return new ArrayList<BlockPopulator>();
	}
	
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkY, BiomeGrid biomeGrid) {
		return new byte[world.getMaxHeight() / 16][];
	}

}
