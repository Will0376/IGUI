package ru.will0376.igui;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = IGui.MOD_ID, name = IGui.MOD_NAME, version = IGui.VERSION)
public class IGui {

	public static final String MOD_ID = "igui";
	public static final String MOD_NAME = "IGui";
	public static final String VERSION = "0.0.7A";

	@Mod.Instance(MOD_ID)
	public static IGui INSTANCE;

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event) {

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}

	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event) {
	}

	@Mod.EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandTest());
	}
}