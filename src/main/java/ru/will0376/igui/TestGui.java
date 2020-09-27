package ru.will0376.igui;

import ru.will0376.igui.buttons.MButton;
import ru.will0376.igui.buttons.MCheckBox;
import ru.will0376.igui.buttons.MSlider;
import ru.will0376.igui.utils.GuiWrapper;

import java.text.DecimalFormat;


public class TestGui extends GuiWrapper {
	@Override
	public void initGui() {
		add(MButton.builder(10, 10, "0")).stream().forEach(e -> {
			MButton button = (MButton) e;
			button.setAction(() -> {
				int buttonText = Integer.parseInt(button.buttonText);
				if (button.isRight()) button.buttonText = String.valueOf(buttonText - 1);
				else button.buttonText = String.valueOf(buttonText + 1);
			});
		});

		add(MCheckBox.builder(10, 34, "O")).addOffsetTextY(1).addOffsetTextX(0).setScaledTextX(3.1f).setScaledTextY(3).stream().forEach(e -> {
			MCheckBox box = (MCheckBox) e;
			box.setAction(box::toggleIsChecked);
		});

		add(MSlider.builder(50, 50, 0.5f)).stream().forEach(e -> {
			MSlider sl = (MSlider) e;
			sl.setAction(() -> {
				sl.name = new DecimalFormat("##.##").format(sl.sliderPosition);
				if (sl.isRight()) sl.setDefault();
			});
		});
		super.initGui();

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
