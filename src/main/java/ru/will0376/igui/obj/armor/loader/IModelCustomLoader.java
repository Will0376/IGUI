package ru.will0376.igui.obj.armor.loader;

import net.minecraft.util.ResourceLocation;

public interface IModelCustomLoader {
	String getType();

	String[] getSuffixes();

	IModelCustom loadInstance(ResourceLocation var1) throws ModelFormatException;
}
