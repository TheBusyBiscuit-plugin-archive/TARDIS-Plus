package me.mrCookieSlime.TARDISPlus.races.TimeLord;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.mrCookieSlime.TARDISPlus.TARDISPlus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;

public class RegenerationListener implements Listener {
	
	TARDISPlus plugin;
	
	public Set<UUID> regenerating;
	public Map<UUID, Long> cooldown;
	
	public RegenerationListener(TARDISPlus plugin) {
		this.plugin = plugin;
		regenerating = new HashSet<UUID>();
		cooldown = new HashMap<UUID, Long>();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		Damageable p = (Damageable) e.getEntity();
		if (p instanceof Player) {
			if (regenerating.contains(p.getUniqueId())) e.setCancelled(true);
			else if (e.getDamage() >= p.getHealth()) {
				boolean cooldown = this.cooldown.containsKey(p.getUniqueId());
				if (!cooldown || (cooldown && this.cooldown.get(p.getUniqueId()) >= System.currentTimeMillis())) {
					if (cooldown) this.cooldown.remove(p.getUniqueId());
					e.setCancelled(true);
					p.setHealth(p.getMaxHealth());
					p.setFireTicks(0);
					for (PotionEffect effect: ((Player) p).getActivePotionEffects()) {
						((Player) p).removePotionEffect(effect.getType());
					}
					regenerating.add(p.getUniqueId());
					((Player) p).sendMessage("§7You start to regenerate");
					Regeneration regeneration = new Regeneration(this, (Player) p, plugin.getCfg().getInt("regeneration.cooldown"));
					regeneration.setID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, regeneration, 0L, 10L));
					this.cooldown.put(p.getUniqueId(), System.currentTimeMillis() + plugin.getCfg().getInt("regeneration.cooldown") * 1000 + 15 * 1000);
				}
			}
		}
	}

}
