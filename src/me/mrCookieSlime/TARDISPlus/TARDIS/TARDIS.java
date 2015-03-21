package me.mrCookieSlime.TARDISPlus.TARDIS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class TARDIS {
	
	public static final Set<TARDIS> tardises = new HashSet<TARDIS>();
	public static final Map<Block, TARDIS> blocks = new HashMap<Block, TARDIS>();
	public static final Map<String, TARDIS> players = new HashMap<String, TARDIS>();
	
	String id;
	Set<Location> exterior;
	World interior;
	Location location;
	Set<String> doctors;
	Set<String> companions;
	
	TARDISMode mode;

}
