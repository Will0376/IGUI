package ru.will0376.igui.obj.armor.loader;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public enum AdvancedModelLoaderRegistry {
	ARMOR("models/armor");

	private final Map<String, IModelCustom> models = new HashMap<>();
	private final String path;

	AdvancedModelLoaderRegistry(String path) {
		this.path = path;
	}

	public IModelCustom getModel(ResourceLocation location) {
		if (!this.models.containsKey(location.getPath())) {
			IModelCustom model = AdvancedModelLoader.loadModel(location);
			if (model == null) {
				FMLLog.log.error(String.format("Could not load model %s, skipping", location.toString()));
				return null;
			} else {
				this.models.put(location.getPath(), model);
				return model;
			}
		} else {
			return this.models.get(location.getPath());
		}
	}

	public IModelCustom getModel(String modid, String name) {
		return this.getModel(this.getResource(modid, name));
	}

	protected ResourceLocation getResource(String modid, String name) {
		return new ResourceLocation(modid, String.format(this.path + "/%s", name));
	}
}
