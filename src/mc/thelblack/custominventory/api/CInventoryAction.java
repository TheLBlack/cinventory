package mc.thelblack.custominventory.api;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;

public interface CInventoryAction<T extends InventoryEvent> {

    /**
     * Represents the action to be performed when the event is triggered.
     * 
     * @param player Player associated with the event.
     * @param action The object of the triggered event. It behaves like a bukkit one. You can do anything with it, including cancellation.
     * 
     */
	public void action(Player player, T action);
}
