package ru.will0376.igui.buttons;

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
