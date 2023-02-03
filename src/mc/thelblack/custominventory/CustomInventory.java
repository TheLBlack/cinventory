package mc.thelblack.custominventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import mc.thelblack.custominventory.api.CInventoryAction;

@SuppressWarnings("unchecked")
public final class CustomInventory implements InventoryHolder {

	private static final HashMap<EventPriority, List<CInventoryEvent<?>>> EMPTY_EVENTS = new HashMap<>();
	private static final List<? extends CInventoryEvent<?>> EMPTY_EVENT = new ArrayList<>();

	public enum Type {
		CHEST("CHEST"),
		HOPPER("HOPPER"),
		BARREL("BARREL"),
		COMPOSTER("COMPOSTER"),
		DISPENSER("DISPENSER"),
		DROPPER("DROPPER"),
		ENDER_CHEST("ENDER_CHEST"),
		PLAYER("PLAYER"),
		SHULKER_BOX("SHULKER_BOX");

		private String type;

		private Type(String type) {
			this.type = type;
		}

		public InventoryType getBukkitType() {
			return InventoryType.valueOf(this.type);
		}
		
		public boolean isSupportedByMyVersion() {
			try {
				this.getBukkitType();
				return true;
			}
			catch (IllegalArgumentException e) {
				return false;
			}
		}
	}

	public static final CInventoryAction<InventoryClickEvent> DENY_ANY_CLICK = (pl, event) -> event.setCancelled(true);
	public static final CInventoryAction<InventoryDragEvent> DENY_ANY_DRAG = (pl, event) -> event.setCancelled(true);
	
	public static final CInventoryAction<InventoryDragEvent> DENY_nonPLAYER_DRAG = (pl, event) -> {if (event.getRawSlots().stream().anyMatch(a -> a > 36)) event.setCancelled(true);};
	
	public static final CInventoryEvent<InventoryClickEvent> DENY_NULL_CLICK = new CInventoryEvent<>(EventPriority.LOWEST, false, (pl, event) -> {if (event.getClickedInventory() == null) event.setCancelled(true);});
	public static final CInventoryEvent<InventoryDragEvent> DENY_NULL_DRAG = new CInventoryEvent<>(EventPriority.LOWEST, false, (pl, event) -> {if (event.getInventory() == null) event.setCancelled(true);});

	private Map<Class<?>, HashMap<EventPriority, ? extends List<? extends CInventoryEvent<?>>>> cevents = new HashMap<>();
	private UUID id;
	
	protected CustomInventory(UUID id, Map<Class<?>, HashMap<EventPriority, ? extends List<? extends CInventoryEvent<?>>>> cevents) {
		this.id = id;
		this.cevents = cevents;
	}

	private <T extends InventoryEvent> List<CInventoryEvent<T>> getEvents(Class<T> clazz, EventPriority priority) {
		List<CInventoryEvent<T>> li = (List<CInventoryEvent<T>>) this.cevents.getOrDefault(clazz, CustomInventory.EMPTY_EVENTS).get(priority);
		return li != null ? li : (List<CInventoryEvent<T>>) CustomInventory.EMPTY_EVENT;
	}

	protected <T extends InventoryEvent> void process(EventPriority priority, boolean isCancelled, Player player, T event) {
		for (CInventoryEvent<T> invevent : (List<CInventoryEvent<T>>) this.getEvents((Class<T>) event.getClass(), priority)) if (!invevent.isIgnoreCancelled() || !isCancelled) invevent.getAction().action(player, event);
	}
	
	protected UUID getId() {
		return this.id;
	}
	
	@Override
	public Inventory getInventory() {
		return null;
	}
}
