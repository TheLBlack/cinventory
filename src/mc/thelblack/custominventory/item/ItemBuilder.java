package mc.thelblack.custominventory.item;

import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {

	public static ItemBuilder builder() {
		return new ItemBuilder();
	}
	
	private ItemStack item;
	private ItemMeta meta;
	
	private ItemBuilder() {
		this.item = new ItemStack(Material.STONE);
		this.meta = this.item.getItemMeta();
	}
	
	public ItemBuilder setType(Material material) {
		this.item.setType(material);
		
		return this;
	}
	
	public ItemBuilder setAmount(int amount) {
		this.item.setAmount(amount);
		
		return this;
	}
	
	public ItemBuilder setData(MaterialData data) {
		this.item.setData(data);
		
		return this;
	}
	
	public ItemBuilder setDurability(short durability) {
		this.item.setDurability(durability);

		return this;
	}
	
	public ItemBuilder addEnchantment(Enchantment ench, int level) {
		this.item.addEnchantment(ench, level);
		
		return this;
	}
	
	public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchs) {
		this.item.addEnchantments(enchs);
		
		return this;
	}
	
	public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
		this.item.addUnsafeEnchantment(ench, level);
		
		return this;
	}
	
	public ItemBuilder addUnsafeEnchantments(Map<Enchantment, Integer> enchs) {
		this.item.addUnsafeEnchantments(enchs);
	
		return this;
	}
	
	public ItemBuilder setItemMeta(ItemMeta meta) {
		this.meta = meta;
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ItemMeta> ItemBuilder accessItemMeta(Class<T> clazz, Consumer<T> meta) {
		meta.accept((T) this.meta);
		
		return this;
	}
	
	public ItemStack build() {
		this.item.setItemMeta(this.meta);
		
		return this.item;
	}
}
