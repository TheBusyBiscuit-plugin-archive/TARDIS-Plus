package me.mrCookieSlime.TARDISPlus.TARDIS;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TARDISUtil {
	
	public String encodeLocation(Location l) {
		return l.getWorld().getName() + "  ;  " + l.getBlockX() + "  ;  " + l.getBlockY() + "  ;  " + l.getBlockZ();
	}
	
	public Location decodeLocation(String s) {
		String[] parts = s.split("  ;  ");
		return new Location(Bukkit.getWorld(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
	}
	
	public Rotation getFacing(Player p) {
		Rotation[] faces = new Rotation[] {Rotation.NORTH, Rotation.EAST, Rotation.SOUTH, Rotation.WEST};
		return faces[(Math.round(p.getLocation().getYaw() / 90F) & 0x3)];
	}
	
	public boolean isItemSimiliar(ItemStack item, ItemStack item2, boolean lore) {
		if (item != null && item2 != null) {
			if (item.hasItemMeta() && item2.hasItemMeta()) {
				if (item.getItemMeta().hasDisplayName() && item2.getItemMeta().hasDisplayName()) {
					if (item.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName())) {
						if (lore) {
							if (item.getItemMeta().hasLore() && item2.getItemMeta().hasLore()) {
								if (item.getItemMeta().getLore().toString().equals(item2.getItemMeta().getLore().toString())) return true;
								else return false;
							}
							else if (!item.getItemMeta().hasLore() && !item2.getItemMeta().hasLore()) return true;
							else return false;
						}
						else return true;
					}
					else return false;
				}
				else if (!item.getItemMeta().hasDisplayName() && !item2.getItemMeta().hasDisplayName()) {
					if (lore) {
						if (item.getItemMeta().hasLore() && item2.getItemMeta().hasLore()) {
							if (item.getItemMeta().getLore().toString().equals(item2.getItemMeta().getLore().toString())) return true;
							else return true;
						}
						else if (!item.getItemMeta().hasLore() && !item2.getItemMeta().hasLore()) return true;
						else return false;
					}
					else return true;
				}
				else return false;
			} 
			else if (!item.hasItemMeta() && !item2.hasItemMeta()) return true;
			else return false;
		}
		else if (item == null && item2 == null) return true;
		else return false;
	}

}
