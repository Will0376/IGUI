package ru.will0376.igui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.will0376.igui.buttons.*;
import ru.will0376.igui.utils.GuiWrapper;

import java.text.DecimalFormat;

@SideOnly(Side.CLIENT)
public class TestGui extends GuiWrapper {
	@Override
	public void initGui() {
		super.initGui();
		enableKeyControl = true;
		add(MButton.builder(10, 10, "0")).stream().forEach(e -> {
			MButton button = (MButton) e;
			button.setAction(this::updateThisScreen);
		});

		add(MCheckBox.builder(10, 34, "O")).addOffsetTextY(1)
				.addOffsetTextX(0)
				.setScaledTextX(3.1f)
				.setScaledTextY(3)
				.stream()
				.forEach(e -> {
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

		add(new MTextField(50, 80, 100, 30, "test", new GuiResponse()));
		add(new MTextField(50, 130, 100, 30, "test2", new GuiResponse()));
		if (!this.buttons.isEmpty()) setSelected(0);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		try {
			drawDefaultBackground();
			super.drawScreen(mouseX, mouseY, partialTicks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
