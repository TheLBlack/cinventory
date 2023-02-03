package mc.thelblack.custominventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.kyori.adventure.text.Component;

public final class PaperInventory {

	protected static Inventory create(InventoryHolder holder, int size, Object titleKyori) throws NoSuchMethodException {
		return Bukkit.createInventory(holder, size, (Component) titleKyori);
	}
	
	protected static Inventory create(InventoryHolder holder, InventoryType type, Object titleKyori) throws NoSuchMethodException {
		return Bukkit.createInventory(holder, type, (Component) titleKyori);
	}
}
