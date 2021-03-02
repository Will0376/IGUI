package ru.will0376.igui.obj;

import net.minecraftforge.client.model.obj.OBJLoader;

public class OBJHelper {
	public static void registerOBJDomain(String in) {
		OBJLoader.INSTANCE.addDomain(in);
	}
}
