package ru.will0376.igui.utils;

import net.minecraft.client.gui.ScaledResolution;

public abstract class NewGuiWrapper extends GuiWrapper {
	public ScaledResolution resolution;

	public abstract void init();

	public abstract void draw(int mouseX, int mouseY, float partialTicks);

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		draw(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		super.initGui();
		resolution = new ScaledResolution(mc);
		init();
	}
}
