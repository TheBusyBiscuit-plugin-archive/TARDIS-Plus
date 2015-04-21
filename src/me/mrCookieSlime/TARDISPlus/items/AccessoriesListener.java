package me.mrCookieSlime.TARDISPlus.items;

import java.util.HashSet;
import java.util.Set;

import me.mrCookieSlime.CSCoreLibPlugin.general.Player.PlayerInventory;
import me.mrCookieSlime.TARDISPlus.TARDISPlus;
import me.mrCookieSlime.TARDISPlus.TARDIS.TARDISUtil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class AccessoriesListener extends TARDISUtil implements Listener {
	
	Set<ItemStack> accessories;
	
	public AccessoriesListener(TARDISPlus plugin) {
		accessories = new HashSet<ItemStack>();
		for (DWItem item: DWItem.values()) {
			if (item.isWearable()) accessories.add(item.getItem());
		}
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onArmor(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.ARMOR && e.getCursor() != null) {
            for (ItemStack accessory : this.accessories) {
                if (isItemSimiliar(e.getCursor(), accessory, true)) {
                    ItemStack item = e.getCurrentItem();
                    e.setCurrentItem(e.getCursor());
                    e.setCursor(item);
                    e.setCancelled(true);
                    PlayerInventory.update((Player)e.getWhoClicked());
                    break;
                }
            }
        }
    }
}