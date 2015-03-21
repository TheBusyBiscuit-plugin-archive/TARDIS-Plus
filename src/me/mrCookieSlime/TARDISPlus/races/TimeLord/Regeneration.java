package me.mrCookieSlime.TARDISPlus.races.TimeLord;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Regeneration extends BukkitRunnable {
	
	RegenerationListener listener;
	int iterations = 0, cooldown;
	UUID uuid;
	int id;
	
	public Regeneration(RegenerationListener listener, Player p, int cooldown) {
		this.uuid = p.getUniqueId();
		this.cooldown = cooldown;
		this.listener = listener;
	}
	
	public void setID(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		iterations++;
		if (Bukkit.getPlayer(uuid) == null) {
			listener.regenerating.remove(uuid);
			Bukkit.getScheduler().cancelTask(id);
		}
		else if (Bukkit.getPlayer(uuid).isDead()) {
			listener.regenerating.remove(uuid);
			Bukkit.getScheduler().cancelTask(id);
		}
		else {
			Player p = Bukkit.getPlayer(uuid);
			
			p.getWorld().playEffect(p.getEyeLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			
			p.getWorld().playEffect(p.getEyeLocation(), Effect.STEP_SOUND, Material.STATIONARY_LAVA);
			p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.STATIONARY_LAVA);
			
			p.getWorld().playSound(p.getLocation(), Sound.GHAST_FIREBALL, 1F, 1F);
			for (Entity n: p.getNearbyEntities(5.0, 5.0, 5.0)) {
				if (n instanceof LivingEntity) {
					Vector vector = n.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(1.4);
					vector.setY(0.8);
					n.setVelocity(vector);
					if (p.getWorld().getPVP()) n.setFireTicks(400);
				}
			}
			if (iterations >= 30) {
				p.sendMessage("§7Your Regeneration has finished, you will not be able to regenerate for §6" + cooldown + " §7Seconds");
				listener.regenerating.remove(uuid);
				Bukkit.getScheduler().cancelTask(id);
			}
		}
	}

}
