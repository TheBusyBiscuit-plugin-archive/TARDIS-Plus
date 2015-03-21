package me.mrCookieSlime.TARDISPlus.TARDIS;

import org.bukkit.block.BlockFace;

public enum Rotation {
	
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	public BlockFace toFace() {
		return BlockFace.valueOf(toString());
	}

}
