package me.mrCookieSlime.TARDISPlus.TARDIS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

public class TARDIS extends TARDISUtil {
	
	public static final Set<TARDIS> tardises = new HashSet<TARDIS>();
	public static final Map<Block, TARDIS> blocks = new HashMap<Block, TARDIS>();
	public static final Map<String, TARDIS> ids = new HashMap<String, TARDIS>();
	
	String id;
	Set<Location> exterior;
	List<Location> door;
	World interior;
	Location location;
	Location spawn;
	Location home;
	List<String> doctors;
	List<String> companions;
	
	TARDISMode mode;
	Rotation direction;
	
	public TARDIS(File file) {
		Config cfg = new Config(file);
		
		this.id = cfg.getString("id");
		this.location = decodeLocation(cfg.getString("position"));
		this.spawn = decodeLocation(cfg.getString("spawn"));
		this.home = decodeLocation(cfg.getString("home"));
		this.doctors = cfg.getStringList("owners");
		this.companions = cfg.getStringList("members");
		
		this.mode = TARDISMode.valueOf(cfg.getString("mode"));
		this.direction = Rotation.valueOf(cfg.getString("direction"));
		
		this.exterior = new HashSet<Location>();
		
		for (String location: cfg.getStringList("exterior")) {
			exterior.add(decodeLocation(location));
			blocks.put(decodeLocation(location).getBlock(), this);
		}
		
		this.door = new ArrayList<Location>();
		
		for (String block: cfg.getStringList("door")) {
			door.add(decodeLocation(block));
		}
		
		ChunkGenerator generator = new TARDISWorldGenerator();
		WorldCreator creator = new WorldCreator("TARDIS+/worlds/" + id)
		.environment(Environment.THE_END)
		.generateStructures(false)
		.generator(generator);
		
		this.interior = Bukkit.createWorld(creator);
		
		tardises.add(this);
		ids.put(id, this);
	}
	
	public TARDIS(Player p, Location l) {
		this.id = String.valueOf(System.currentTimeMillis());
		
		this.location = l;
		this.home = l;
//		this.spawn = 
		this.doctors = Arrays.asList(p.getUniqueId().toString());
		this.companions = new ArrayList<String>();
		this.mode = TARDISMode.LANDED;
		this.direction = getFacing(p);
		
		land(l);
		
		ChunkGenerator generator = new TARDISWorldGenerator();
		WorldCreator creator = new WorldCreator("TARDIS+/worlds/" + id)
		.environment(Environment.THE_END)
		.generateStructures(false)
		.generator(generator);
		
		this.interior = Bukkit.createWorld(creator);
		
		tardises.add(this);
		ids.put(id, this);
		
		save();
	}
	
	private void save() {
		Config cfg = new Config(new File("TARDIS+/TARDIS/" + id + ".TARDIS"));
		
		cfg.setValue("id", id);
		cfg.setValue("position", encodeLocation(location));
//		cfg.setValue("spawn", encodeLocation(spawn));
		cfg.setValue("home", encodeLocation(home));
		cfg.setValue("owners", doctors);
		cfg.setValue("members", companions);
		cfg.setValue("mode", mode.toString());
		cfg.setValue("direction", direction.toString());
		
		Set<String> blocks = new HashSet<String>();
		for (Location block: exterior) {
			blocks.add(encodeLocation(block));
		}
		cfg.setValue("exterior", blocks);
		
		Set<String> doors = new HashSet<String>();
		for (Location block: door) {
			doors.add(encodeLocation(block));
		}
		cfg.setValue("door", doors);
		
		cfg.save();
	}

	public void land(Location l) {
		this.location = l;
		try {
			this.exterior = Schematic.loadSchematic(new File("TARDIS+/schematics/" + direction.toString() + ".schematic")).pasteSchematic(l);
			this.door = new ArrayList<Location>();
			this.door.add(l.getBlock().getRelative(direction.toFace()).getLocation());
			this.door.add(l.getBlock().getRelative(BlockFace.UP).getRelative(direction.toFace()).getLocation());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void rotate(BlockFace face) {
		try {
			this.direction = Rotation.valueOf(face.toString());
			this.exterior = Schematic.loadSchematic(new File("TARDIS+/schematics/" + direction.toString() + ".schematic")).pasteSchematic(location);
			this.door = new ArrayList<Location>();
			this.door.add(location.getBlock().getRelative(direction.toFace()).getLocation());
			this.door.add(location.getBlock().getRelative(BlockFace.UP).getRelative(direction.toFace()).getLocation());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enter(Player p) {
		p.teleport(spawn);
	}
	
	public void leave(Player p) {
		p.teleport(door.get(1).getBlock().getRelative(direction.toFace()).getRelative(direction.toFace()).getLocation());
	}

}
