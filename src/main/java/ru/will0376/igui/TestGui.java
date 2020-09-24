package ru.will0376.igui;

import ru.will0376.igui.buttons.GuiWrapper;
import ru.will0376.igui.buttons.MButton;


public class TestGui extends GuiWrapper {
	@Override
	public void initGui() {
		super.initGui();
		add(MButton.builder(10, 10, "0")).getStream().forEach(e -> {
			MButton button = (MButton) e;
			button.setAction(() -> {
				int buttonText = Integer.parseInt(button.buttonText);
				if (button.isRight()) button.buttonText = String.valueOf(buttonText + 1);
				else button.buttonText = String.valueOf(buttonText - 1);
			});
		});
		add(MButton.builder(10, 30, "10")).getStream().forEach(e -> {
			((MButton) e).setAction(() -> {
				int buttonText = Integer.parseInt(((MButton) e).buttonText);
				((MButton) e).buttonText = String.valueOf(buttonText + 12);
			});
		});
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
