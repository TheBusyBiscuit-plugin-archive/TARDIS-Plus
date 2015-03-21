package me.mrCookieSlime.TARDISPlus.TARDIS;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class TARDISUtil {
	
	public String encodeLocation(Location l) {
		return l.getWorld().getName() + "  ;  " + l.getBlockX() + "  ;  " + l.getBlockY() + "  ;  " + l.getBlockZ();
	}
	
	public Location decodeLocation(String s) {
		String[] parts = s.split("  ;  ");
		return new Location(Bukkit.getWorld(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
	}

}
