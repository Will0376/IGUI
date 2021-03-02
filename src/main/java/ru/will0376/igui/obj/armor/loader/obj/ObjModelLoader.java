package ru.will0376.igui.obj.armor.loader.obj;

import net.minecraft.util.ResourceLocation;
import ru.will0376.igui.obj.armor.loader.IModelCustom;
import ru.will0376.igui.obj.armor.loader.IModelCustomLoader;
import ru.will0376.igui.obj.armor.loader.ModelFormatException;


public class ObjModelLoader implements IModelCustomLoader {
	private static final String[] types = new String[]{"obj"};

	public String getType() {
		return "OBJ model";
	}

	public String[] getSuffixes() {
		return types;
	}

	public IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException {
		return new WavefrontObject(resource);
	}
}
