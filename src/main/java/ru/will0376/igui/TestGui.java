package ru.will0376.igui;

import ru.will0376.igui.buttons.MButton;
import ru.will0376.igui.buttons.MCheckBox;
import ru.will0376.igui.utils.GuiWrapper;


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
		add(MCheckBox.builder(10, 34, "O")).addOffsetTextY(1).addOffsetTextX(0).setScaledTextX(3.1f).setScaledTextY(3).getStream().forEach(e -> {
			MCheckBox box = (MCheckBox) e;
			box.setAction(box::toggleIsChecked);
		});
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
