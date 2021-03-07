package ru.will0376.igui.obj.armor;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.registries.IForgeRegistry;
import ru.will0376.igui.obj.armor.model.ArmorBinding;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Getter
public enum ArmorRegistry {
	Instance;

	private final ItemArmor.ArmorMaterial DEFAULT_MATERIAL = generateArmorMaterial("igui", "default", 3.0F, 33, 1.5F, new int[]{
			3, 6, 8, 3}, 10, 2.0F);

	private final Map<String, ItemArmor.ArmorMaterial> armorMaterialMap = new HashMap<>();
	private final Map<String, ItemCustomArmor> armorMap = new HashMap<>();
	private final Map<String, ArmorBinding> bindingMap = new HashMap<>();

	/*public ItemCustomArmor MODEL_HEAD = ItemCustomArmor.create("model", DEFAULT_MATERIAL, 1, EntityEquipmentSlot.HEAD);
	public ItemCustomArmor MODEL_CHESTPLATE = ItemCustomArmor.create("model", DEFAULT_MATERIAL, 1, EntityEquipmentSlot.CHEST);
	public ItemCustomArmor MODEL_LEGGINS = ItemCustomArmor.create("model", DEFAULT_MATERIAL, 2, EntityEquipmentSlot.LEGS);
	public ItemCustomArmor MODEL_BOOTS = ItemCustomArmor.create("model", DEFAULT_MATERIAL, 1, EntityEquipmentSlot.FEET);*/


	ArmorRegistry() {
	}

	public ItemArmor.ArmorMaterial generateArmorMaterial(String modid, String name, float durabilityFactor, int baseDurability, float damageFactor, int[] baseDamageability, int enchantibility, float toughness) {
		return EnumHelper.addArmorMaterial(modid + ":" + name, modid + ":" + name, (int) ((float) baseDurability * durabilityFactor), new int[]{
				(int) ((float) baseDamageability[0] * damageFactor),
				(int) ((float) baseDamageability[1] * damageFactor),
				(int) ((float) baseDamageability[2] * damageFactor),
				(int) ((float) baseDamageability[3] * damageFactor)}, enchantibility, SoundEvents.ITEM_ARMOR_EQUIP_IRON, toughness);
	}

	public void registerAll(IForgeRegistry<Item> registry) {
		for (Map.Entry<String, ItemCustomArmor> entry : armorMap.entrySet()) {
			String s = entry.getKey();
			ItemCustomArmor itemCustomArmor = entry.getValue();
			registry.register(itemCustomArmor);
			if (FMLCommonHandler.instance().getSide().isClient()) {
				if (bindingMap.containsKey(s)) {
					itemCustomArmor.setArmorBinding(bindingMap.get(s));
				} else {
					log.error("Model for armor {} not found!", s);
				}
			}
		}
	}

	public ItemArmor.ArmorMaterial getNewArmorMaterial(String modid, String name, float durabilityFactor, int baseDurability, float damageFactor, int[] baseDamageability, int enchantibility, float toughness) {
		if (!armorMaterialMap.containsKey(modid))
			armorMaterialMap.put(modid, generateArmorMaterial(modid, name, durabilityFactor, baseDurability, damageFactor, baseDamageability, enchantibility, toughness));
		return armorMaterialMap.get(modid);
	}

	public void registerNewArmor(String name, ItemCustomArmor itemCustomArmor) {
		armorMap.put(name, itemCustomArmor);
	}

	public void registerModel(String name, ArmorBinding armorBinding) {
		bindingMap.put(name, armorBinding);
	}
}
