package mc.thelblack.custominventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mc.thelblack.custominventory.CustomInventory.Type;
import mc.thelblack.custominventory.api.CInventoryAction;
import mc.thelblack.custominventory.api.CInventoryBuilder;

public final class CInventoryManager {

	private static final Function<InventoryEvent, Player> INTERACT_PL = ev -> (Player) ((InventoryInteractEvent) ev).getWhoClicked();
	private static final Function<InventoryEvent, Boolean> INTERACT_CAN = ev -> ((InventoryInteractEvent) ev).isCancelled();
	
	private static final Function<InventoryEvent, Player> OPEN_PL = ev -> (Player) ((InventoryOpenEvent) ev).getPlayer();
	private static final Function<InventoryEvent, Boolean> OPEN_CAN = ev -> ((InventoryOpenEvent) ev).isCancelled();
	
	private static final Function<InventoryEvent, Player> CLOSE_PL = ev -> (Player) ((InventoryCloseEvent) ev).getPlayer();
	private static final Function<InventoryEvent, Boolean> CLOSE_CAN = ev -> false;
	
	private JavaPlugin instance;
	private UUID id = UUID.randomUUID();

	public CInventoryManager(JavaPlugin mainInstance) {
		this.instance = mainInstance;
		
		this.registerEvent(InventoryClickEvent.class, CInventoryManager.INTERACT_PL, CInventoryManager.INTERACT_CAN);
		this.registerEvent(InventoryDragEvent.class, CInventoryManager.INTERACT_PL, CInventoryManager.INTERACT_CAN);
		this.registerEvent(InventoryOpenEvent.class, CInventoryManager.OPEN_PL, CInventoryManager.OPEN_CAN);
		this.registerEvent(InventoryCloseEvent.class, CInventoryManager.CLOSE_PL, CInventoryManager.CLOSE_CAN);
	}
	
	private void registerEvent(Class<? extends InventoryEvent> clazz, Function<InventoryEvent, Player> pl, Function<InventoryEvent, Boolean> can) {
		Stream.of(EventPriority.values()).forEach(prio -> {
			CInventoryExecutor exec = (li, ev) -> {
				InventoryEvent invev = (InventoryEvent) ev;
				if (invev.getInventory().getHolder() instanceof CustomInventory) {
					CustomInventory cinv = (CustomInventory) invev.getInventory().getHolder();
	
					if (cinv.getId().equals(CInventoryManager.this.id)) cinv.process(prio, can.apply(invev), pl.apply(invev), invev);
				}
			};
			
			Bukkit.getPluginManager().registerEvent(clazz, exec, prio, exec, this.getInstance());
		});
	}

	public CInventoryBuilder builder() {
		return new CInventoryManager.CInventoryBuilderInv();
	}
	
	private JavaPlugin getInstance() {
		return this.instance;
	}

	private <T extends InventoryEvent> HashMap<EventPriority, List<CInventoryEvent<T>>> sortOut(List<CInventoryEvent<T>> list) {
		HashMap<EventPriority, List<CInventoryEvent<T>>> po = new HashMap<>();
		list.forEach(a -> po.compute(a.getPriority(), (k, v) -> {
			List<CInventoryEvent<T>> events = (v == null ? new ArrayList<>() : v);
			events.add(a);

			return events;
		}));

		return po;
	}
	
	private static final String NOCLASS = "Method CInventoryBuilder#setTitle(paperKyoriComponent) can be used only when the server engine is paper-based";
	private static final String NOCAST = "Method CInventoryBuilder#setTitle(paperKyoriComponent) works only with kyoriAdventure library's Components, not any objects";
	
	private static final Inventory createPaper(InventoryHolder holder, int size, Object titleKyori) {
		try {
			return PaperInventory.create(holder, size, titleKyori);
		}
		catch (NoClassDefFoundError | NoSuchMethodException e) {
			throw new UnsupportedOperationException(CInventoryManager.NOCLASS);
		}
		catch (ClassCastException e) {
			throw new IllegalArgumentException(CInventoryManager.NOCAST);
		}
	}
	
	private static final Inventory createPaper(InventoryHolder holder, InventoryType type, Object titleKyori) {
		try {
			return PaperInventory.create(holder, type, titleKyori);
		}
		catch (NoClassDefFoundError | NoSuchMethodException e) {
			throw new UnsupportedOperationException(CInventoryManager.NOCLASS);
		}
		catch (ClassCastException e) {
			throw new IllegalArgumentException(CInventoryManager.NOCAST);
		}
	}

	public class CInventoryBuilderInv implements CInventoryBuilder {
		private int size = 9, maxStackSize = 64;
		private String title = "default title";
		private Object titleKyori = null;
		private CustomInventory.Type type = Type.CHEST;
		private ItemStack[] items = new ItemStack[this.size];
		
		private List<CInventoryEvent<InventoryClickEvent>> eclick = new ArrayList<>();
		private List<CInventoryEvent<InventoryDragEvent>> edrag = new ArrayList<>();
		private List<CInventoryEvent<InventoryOpenEvent>> eopen = new ArrayList<>();
		private List<CInventoryEvent<InventoryCloseEvent>> eclose = new ArrayList<>();
		
		protected CInventoryBuilderInv() {}

		public Inventory build() {
			Inventory binv;
			CustomInventory cinv = new CustomInventory(CInventoryManager.this.id, Map.of(InventoryClickEvent.class, CInventoryManager.this.sortOut(this.eclick), InventoryDragEvent.class, CInventoryManager.this.sortOut(this.edrag), InventoryOpenEvent.class, CInventoryManager.this.sortOut(this.eopen), InventoryCloseEvent.class, CInventoryManager.this.sortOut(this.eclose)));
			if (this.type == CustomInventory.Type.CHEST) binv = this.titleKyori == null ? Bukkit.createInventory(cinv, this.size, this.title) : CInventoryManager.createPaper(cinv, this.size, this.titleKyori);
			else binv = this.titleKyori == null ? Bukkit.createInventory(cinv, this.type.getBukkitType()) : CInventoryManager.createPaper(cinv, this.type.getBukkitType(), this.titleKyori);
			
			binv.setMaxStackSize(this.maxStackSize);
			binv.setContents(this.items);

			return binv;
		}
		
		public CInventoryBuilder addEventInventoryClick(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryClickEvent> action) {
			this.eclick.add(new CInventoryEvent<>(priority, ignoreCancelled, action));

			return this;
		}

		public CInventoryBuilder addEventInventoryClick(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryClickEvent> action) {
			this.addEventInventoryClick(priority, false, action);
			
			return this;
		}
		
		public CInventoryBuilder addEventInventoryClick(@NotNull CInventoryAction<InventoryClickEvent> action) {
			this.addEventInventoryClick(EventPriority.NORMAL, action);
			
			return this;
		}

		public CInventoryBuilder addFullEventInventoryClick(@NotNull CInventoryEvent<InventoryClickEvent> event) {
			Validate.notNull(event, "Event cannot be null");
			this.eclick.add(event);

			return this;
		}
		
		public CInventoryBuilder addEventInventoryDrag(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryDragEvent> action) {
			this.edrag.add(new CInventoryEvent<>(priority, ignoreCancelled, action));

			return this;
		}

		public CInventoryBuilder addEventInventoryDrag(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryDragEvent> action) {
			this.addEventInventoryDrag(priority, false, action);
			
			return this;
		}
		
		public CInventoryBuilder addEventInventoryDrag(@NotNull CInventoryAction<InventoryDragEvent> action) {
			this.addEventInventoryDrag(EventPriority.NORMAL, action);
			
			return this;
		}

		public CInventoryBuilder addFullEventInventoryDrag(@NotNull CInventoryEvent<InventoryDragEvent> event) {
			Validate.notNull(event, "Event cannot be null");
			this.edrag.add(event);

			return this;
		}
		
		public CInventoryBuilder addEventInventoryOpen(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryOpenEvent> action) {
			this.eopen.add(new CInventoryEvent<>(priority, ignoreCancelled, action));

			return this;
		}

		public CInventoryBuilder addEventInventoryOpen(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryOpenEvent> action) {
			this.addEventInventoryOpen(priority, false, action);
			
			return this;
		}
		
		public CInventoryBuilder addEventInventoryOpen(@NotNull CInventoryAction<InventoryOpenEvent> action) {
			this.addEventInventoryOpen(EventPriority.NORMAL, action);
			
			return this;
		}

		public CInventoryBuilder addFullEventInventoryOpen(@NotNull CInventoryEvent<InventoryOpenEvent> event) {
			Validate.notNull(event, "Event cannot be null");
			this.eopen.add(event);

			return this;
		}
		
		public CInventoryBuilder addEventInventoryClose(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryCloseEvent> action) {
			this.eclose.add(new CInventoryEvent<>(priority, ignoreCancelled, action));

			return this;
		}

		public CInventoryBuilder addEventInventoryClose(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryCloseEvent> action) {
			this.addEventInventoryClose(priority, false, action);
			
			return this;
		}
		
		public CInventoryBuilder addEventInventoryClose(@NotNull CInventoryAction<InventoryCloseEvent> action) {
			this.addEventInventoryClose(EventPriority.NORMAL, action);
			
			return this;
		}

		public CInventoryBuilder addFullEventInventoryClose(@NotNull CInventoryEvent<InventoryCloseEvent> event) {
			Validate.notNull(event, "Event cannot be null");
			this.eclose.add(event);

			return this;
		}
		
		public CInventoryBuilder setTitle(@NotNull String title) {
			Validate.notNull(type, "Title cannot be null");
			this.title = title;
			
			return this;
		}
		
		public CInventoryBuilder setTitle(@Nullable Object paperKyoriComponent) {
			this.titleKyori = paperKyoriComponent;
			
			return this;
		}
		
		public CInventoryBuilder setSize(int size) {
			this.size = size;
			
			List<ItemStack> newcon = Arrays.asList(this.items);
			this.items = newcon.toArray(new ItemStack[this.size]);
			
			return this;
		}
		
		public CInventoryBuilder setRows(int rows) {
			this.setSize(rows*9);
			
			return this;
		}

		public CInventoryBuilder setType(@NotNull CustomInventory.Type type) {
			Validate.notNull(type, "Type cannot be null");
			this.type = type;

			return this;
		}
		
		public CInventoryBuilder setMaxStackSize(int stack) {
			this.maxStackSize = stack;
			
			return this;
		}
		
		public CInventoryBuilder fill(@Nullable ItemStack item) {
			Arrays.fill(this.items, item);
			
			return this;
		}
		
		public CInventoryBuilder setItem(int index, @Nullable ItemStack item) {
			this.items[index] = item;
			
			return this;
		}
		
		public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2) {
			this.setItem(index, item);
			this.items[index2] = item2;
			
			return this;
		}
		
		public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2, int index3, @Nullable ItemStack item3) {
			this.setItem(index, item, index2, item2);
			this.items[index3] = item3;
			
			return this;
		}
		
		public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2, int index3, @Nullable ItemStack item3, int index4, @Nullable ItemStack item4) {
			this.setItem(index, item, index2, item2, index3, item3);
			this.items[index4] = item4;
			
			return this;
		}
		
		public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2, int index3, @Nullable ItemStack item3, int index4, @Nullable ItemStack item4, int index5, @Nullable ItemStack item5) {
			this.setItem(index, item, index2, item2, index3, item3, index4, item4);
			this.items[index5] = item5;
			
			return this;
		}

		public CInventoryBuilder addItem(@NotNull ItemStack... items) {
			return this.addItem(Arrays.asList(items));
		}
		
		public CInventoryBuilder addItem(@NotNull List<ItemStack> items) {
			Validate.notNull(items, "Items cannot be null");
			List<ItemStack> it = List.copyOf(items);
			for (int i = 0; i < this.items.length && !items.isEmpty(); i++) if (this.items[i] == null) this.items[i] = it.remove(0);

			return this;
		}
		
		public CInventoryBuilder setContents(@NotNull ItemStack[] items) {
			Validate.notNull(type, "Items cannot be null");
			if (this.size < items.length) throw new IllegalArgumentException(String.format("Invalid inventory size; expected array of %s or less", this.size));
			this.items = Arrays.asList(items).toArray(new ItemStack[this.size]);
			
			return this;
		}
		
		public CInventoryBuilder setStorageContents(@NotNull ItemStack[] items) {
			Validate.notNull(type, "Items cannot be null");
			if (this.size < items.length) throw new IllegalArgumentException(String.format("Invalid inventory size; expected array of %s or less", this.size));
			for (int i = 0; i < items.length; i++) this.items[i] = items[i];
				
			return this;
		}
	}
}
