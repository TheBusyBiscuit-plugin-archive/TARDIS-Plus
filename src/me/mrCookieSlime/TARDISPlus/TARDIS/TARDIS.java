package me.mrCookieSlime.TARDISPlus.TARDIS;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class TARDIS extends TARDISUtil {
	
	public static final Set<TARDIS> tardises = new HashSet<TARDIS>();
	public static final Map<Block, TARDIS> blocks = new HashMap<Block, TARDIS>();
	public static final Map<String, TARDIS> ids = new HashMap<String, TARDIS>();
	
	String id;
	Set<Location> exterior;
	World interior;
	Location location;
	Location spawn;
	Location home;
	List<String> doctors;
	List<String> companions;
	
	TARDISMode mode;
	
	public TARDIS(File file) {
		Config cfg = new Config(file);
		
		this.id = cfg.getString("id");
		this.location = decodeLocation(cfg.getString("position"));
		this.spawn = decodeLocation(cfg.getString("spawn"));
		this.home = decodeLocation(cfg.getString("home"));
		this.doctors = cfg.getStringList("owners");
		this.companions = cfg.getStringList("members");
		this.mode = TARDISMode.valueOf(cfg.getString("mode"));
		
		this.exterior = new HashSet<Location>();
		
		for (String location: cfg.getStringList("exterior")) {
			exterior.add(decodeLocation(location));
			blocks.put(decodeLocation(location).getBlock(), this);
		}
		
		this.interior = Bukkit.createWorld(new WorldCreator(id));
		
		tardises.add(this);
	}
	
	public void enter(Player p) {
		p.teleport(spawn);
	}

}
