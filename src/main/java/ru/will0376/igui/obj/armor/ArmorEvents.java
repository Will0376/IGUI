package ru.will0376.igui.obj.armor;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

@Mod.EventBusSubscriber
public class ArmorEvents {

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerRenders(ModelRegistryEvent event) {
		for (Map.Entry<ResourceLocation, Item> entry : ForgeRegistries.ITEMS.getEntries())
			ArmorRegistry.Instance.getArmorMap().forEach((s, itemCustomArmor) -> {
				if (entry.getKey().getNamespace().equals(itemCustomArmor.getRegistryName().getNamespace()))
					registerModel(entry.getValue());
			});
	}

	private static void registerModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	@SubscribeEvent
	public static void onRegistryItem(RegistryEvent.Register<Item> e) {
		ArmorRegistry.Instance.registerAll(e.getRegistry());
	}
}
