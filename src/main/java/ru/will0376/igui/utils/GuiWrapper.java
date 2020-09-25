package ru.will0376.igui.utils;

import net.minecraft.client.gui.GuiScreen;
import ru.will0376.igui.buttons.IButton;

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

	public <T extends IButton> T add(T buttonIn) {
		buttons.add(buttonIn);
		return buttonIn;
	}
}
