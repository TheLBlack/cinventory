package mc.thelblack.custominventory.api;

import java.util.List;

import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mc.thelblack.custominventory.CInventoryEvent;
import mc.thelblack.custominventory.CustomInventory;

/**
 * Class used to build final inventory object.
 */
public interface CInventoryBuilder {

    /**
     * Builds and returns the final inventory object with your events inside. You can open it and treat like a regular inventory.
     *
     * @return Bukkit ready-to-open inventory.
     * 
     */
	public Inventory build();
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryClickEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param ignoreCancelled do not execute the event if the events with a lower priority have already canceled it.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryClickEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryClick(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryClickEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryClickEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryClickEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryClick(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryClickEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryClickEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>priority</i> is set to {@code EventPriority.NORMAL} by default.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     *
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryClickEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryClick(@NotNull CInventoryAction<InventoryClickEvent> action);
	
    /**
     * Adds already prepared event object related to the <b>InventoryClickEvent</b>. These objects may be stored as static and be added to chosen inventories.
     * 
     * @param event immutable event object
     * @see org.bukkit.event.inventory.InventoryClickEvent
     * @see mc.thelblack.custominventory.CInventoryEvent
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addFullEventInventoryClick(@NotNull CInventoryEvent<InventoryClickEvent> event);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryDragEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param ignoreCancelled do not execute the event if the events with a lower priority have already canceled it.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryDragEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryDrag(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryDragEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryDragEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryDragEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryDrag(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryDragEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryDragEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>priority</i> is set to {@code EventPriority.NORMAL} by default.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     *
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryDragEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryDrag(@NotNull CInventoryAction<InventoryDragEvent> action);
	
    /**
     * Adds already prepared event object related to the <b>InventoryDragEvent</b>. These objects may be stored as static and be added to chosen inventories.
     * 
     * @param event immutable event object
     * @see org.bukkit.event.inventory.InventoryDragEvent
     * @see mc.thelblack.custominventory.CInventoryEvent
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addFullEventInventoryDrag(@NotNull CInventoryEvent<InventoryDragEvent> event);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryOpenEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param ignoreCancelled do not execute the event if the events with a lower priority have already canceled it.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryOpenEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryOpen(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryOpenEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryOpenEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryOpenEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryOpen(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryOpenEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryOpenEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>priority</i> is set to {@code EventPriority.NORMAL} by default.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     *
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryOpenEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryOpen(@NotNull CInventoryAction<InventoryOpenEvent> action);
	
    /**
     * Adds already prepared event object related to the <b>InventoryOpenEvent</b>. These objects may be stored as static and be added to chosen inventories.
     * 
     * @param event the immutable event object
     * @see org.bukkit.event.inventory.InventoryOpenEvent
     * @see mc.thelblack.custominventory.CInventoryEvent
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addFullEventInventoryOpen(@NotNull CInventoryEvent<InventoryOpenEvent> event);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryCloseEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param ignoreCancelled do not execute the event if the events with a lower priority have already canceled it.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryCloseEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryClose(@NotNull EventPriority priority, boolean ignoreCancelled, @NotNull CInventoryAction<InventoryCloseEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryCloseEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     * 
     * @param priority a priority defining the order in which events take place. Works the same as in bukkit.
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryCloseEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryClose(@NotNull EventPriority priority, @NotNull CInventoryAction<InventoryCloseEvent> action);
	
    /**
     * Adds a behavior to the inventory related to the <b>InventoryCloseEvent</b>.
     * <p>
     * The first parameters come from the bukkit {@code EventHandler}.
     * <p>
     * <i>priority</i> is set to {@code EventPriority.NORMAL} by default.
     * <p>
     * <i>ignoreCancelled</i> is set to false by default.
     *
     * @param action an action to be performed when the event is executed. The event object is from bukkit and behaves in the same way.
     * @see org.bukkit.event.inventory.InventoryCloseEvent
     * @see org.bukkit.event.EventHandler
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addEventInventoryClose(@NotNull CInventoryAction<InventoryCloseEvent> action);
	
    /**
     * Adds already prepared event object related to the <b>InventoryCloseEvent</b>. These objects may be stored as static and be added to chosen inventories.
     * 
     * @param event the immutable event object
     * @see org.bukkit.event.inventory.InventoryCloseEvent
     * @see mc.thelblack.custominventory.CInventoryEvent
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addFullEventInventoryClose(@NotNull CInventoryEvent<InventoryCloseEvent> event);
	
    /**
     * Sets a title for an inventory. Standard coloring is supported (&).
     * 
     * @param title the title to be set.
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setTitle(@NotNull String title);
	
    /**
     * Sets a title for an inventory using {@code net.kyori} library for RGB support. No other object can be passed. Library is present by default in papermc.
     * <p>
     * If you use this method, the plugin <b>have to be lunched on papermc</b> version of the server.
     * 
     * @param paperKyoriComponent the title to be set with kyori Component.
     * @throws IllegalArgumentException When other object than kyori Component is passed as a parameter.
     * @throws UnsupportedOperationException When method is used while server engine is not papermc.
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setTitle(@Nullable Object paperKyoriComponent);
	
    /**
     * Defines new number of slots for the inventory.
     * 
     * @param size a multiple of 9 as the size of inventory
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setSize(int size);
	
    /**
     * Defines new number of slots for the inventory.
     * 
     * @param rows a number of rows to be present
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setRows(int rows);
	
    /**
     * Changes the type of the inventory. Make sure your server version supports chosen inventory type.
     * 
     * @param type the type of an inventory.
     * @see mc.thelblack.custominventory.CustomInventory.Type
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setType(@NotNull CustomInventory.Type type);
	
    /**
     * Change the maximum stack size for an inventory.
     * <p>
     * Works the same as in {@link org.bukkit.inventory.Inventory#setMaxStackSize(int)}
     * 
     * @param stack the stack size.
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setMaxStackSize(int stack);
	
    /**
     * Fills the entire inventory with given instance of ItemStack. Use null to empty.
     * 
     * @param filler an ItemStack used to fill
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder fill(@Nullable ItemStack filler);
	
    /**
     * Puts an ItemStack(s) on the specified index(es). Use null to empty.
     * <p>
     * Works the same as in {@link org.bukkit.inventory.Inventory#setItem(int, ItemStack)}
     *
     * @param index a place to put (starting from 0)
     * @param item an ItemStack to be placed
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setItem(int index, @Nullable ItemStack item);
	
    /**
     * Extended method {@link mc.thelblack.custominventory.api.CInventoryBuilder#setItem(int, ItemStack)}
     *
     * @param index a place to put (starting from 0)
     * @param item an ItemStack to be placed
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2);
	
    /**
     * Extended method {@link mc.thelblack.custominventory.api.CInventoryBuilder#setItem(int, ItemStack)}
     *
     * @param index a place to put (starting from 0)
     * @param item an ItemStack to be placed
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2, int index3, @Nullable ItemStack item3);
	
    /**
     * Extended method {@link mc.thelblack.custominventory.api.CInventoryBuilder#setItem(int, ItemStack)}
     *
     * @param index a place to put (starting from 0)
     * @param item an ItemStack to be placed
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2, int index3, @Nullable ItemStack item3, int index4, @Nullable ItemStack item4);
	
    /**
     * Extended method {@link mc.thelblack.custominventory.api.CInventoryBuilder#setItem(int, ItemStack)}
     *
     * @param index a place to put (starting from 0)
     * @param item an ItemStack to be placed
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setItem(int index, @Nullable ItemStack item, int index2, @Nullable ItemStack item2, int index3, @Nullable ItemStack item3, int index4, @Nullable ItemStack item4, int index5, @Nullable ItemStack item5);
	
    /**
     * Puts given ItemStacks on the first free slot each.
     *
     * @param items ItemStacks to add
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addItem(@NotNull ItemStack... items);
	
    /**
     * Puts given ItemStacks on the first free slot each.
     *
     * @param items ItemStacks to add
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder addItem(@NotNull List<ItemStack> items);
	
    /**
     * Overrides the entire inventory content with the new one. 
     * <p>
     * Works the same as in {@link org.bukkit.inventory.Inventory#setContents(ItemStack[])}
     * 
     * @param items the array of items to replace
     * @throws IllegalArgumentException When the array has more items than the inventory.
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setContents(@NotNull ItemStack[] items);
	
    /**
     * Puts the ItemStacks in the slots from the array.
     * <p>
     * Works the same as in {@link org.bukkit.inventory.Inventory#setStorageContents(ItemStack[])}
     * 
     * @param items the array of items to place
     * @throws IllegalArgumentException When the array has more items than the inventory.
     * @return Builder for further inventory creation. Use {@code build()} to get the finished object.
     * 
     */
	public CInventoryBuilder setStorageContents(@NotNull ItemStack[] items);
}
