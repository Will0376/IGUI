package ru.will0376.igui.buttons;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiResponse {
	boolean bool = false;

	public boolean isBool() {
		return bool;
	}

	public GuiResponse setBool(boolean bool) {
		this.bool = bool;
		return this;
	}

	public boolean getAndToggle() {
		boolean btmp = bool;
		if (bool) {
			bool = false;
		}
		return btmp;
	}
}
