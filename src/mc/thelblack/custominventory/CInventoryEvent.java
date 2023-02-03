package mc.thelblack.custominventory;

import org.apache.commons.lang.Validate;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryEvent;
import org.jetbrains.annotations.NotNull;

import mc.thelblack.custominventory.api.CInventoryAction;

public final class CInventoryEvent<T extends InventoryEvent> {
	
	private EventPriority p;
	private boolean i;
	private CInventoryAction<T> a;
	
	public CInventoryEvent(@NotNull EventPriority pr, boolean ignoreCancelled, @NotNull CInventoryAction<T> ac) {
		Validate.notNull(pr, "EventPriority cannot be null");
		Validate.notNull(ac, "CInventoryAction cannot be null");
		
		this.p = pr;
		this.i = ignoreCancelled;
		this.a = ac;
	}
	
	@NotNull
	public EventPriority getPriority() {
		return this.p;
	}

	public boolean isIgnoreCancelled() {
		return this.i;
	}
	
	@NotNull
	public CInventoryAction<T> getAction() {
		return this.a;
	}
}
