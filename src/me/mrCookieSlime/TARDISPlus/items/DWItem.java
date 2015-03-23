package me.mrCookieSlime.TARDISPlus.items;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomArmor;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.Item.CustomItem;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

@SuppressWarnings("deprecation")
public enum DWItem {
	
	SONIC_SCREWDRIVER(new CustomItem(Material.BLAZE_ROD, "&6Sonic Screwdriver", 0, new String[] {"", "&7&o> Point and think"}), false),
	FEZ(new CustomItem(new MaterialData(Material.CARPET, (byte) 1), "&rFez"), true),
	STETSON(new CustomItem(new MaterialData(Material.CARPET, (byte) 2), "&rStetson"), true),
	SEVENTH_DOCTORS_HAT(new CustomItem(new MaterialData(Material.CARPET, (byte) 3), "&r7th Doctor's Hat"), true),
	GAS_MASK(new CustomItem(new MaterialData(Material.CARPET, (byte) 4), "&rGas Mask"), true),
	THREE_D_GLASSES(new CustomItem(new ItemStack(Material.LEATHER_HELMET), "&r3D Glasses"), false),
	BOW_TIE(new CustomArmor(new CustomItem(new ItemStack(Material.LEATHER_CHESTPLATE), "&rBow Tie"), Color.fromRGB(99, 33, 33)), false);
	
	ItemStack item;
	boolean accessory;
	
	DWItem(ItemStack item, boolean accessory) {
		this.item = item;
		this.accessory= accessory;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public boolean isWearable() {
		return accessory;
	}
	
}
