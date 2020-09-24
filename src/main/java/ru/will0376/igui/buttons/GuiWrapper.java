package ru.will0376.igui.buttons;

import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

public class GuiWrapper extends GuiScreen {
	ArrayList<IButton> buttons = new ArrayList<>();

	@Override
	public void initGui() {
		super.initGui();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		buttons.forEach(e -> {
			e.draw(mc, mouseX, mouseY, partialTicks);
			e.mouseAction(mouseX, mouseY);
		});
	}

	public IButton add(IButton button) {
		buttons.add(button);
		return button;
	}
}
