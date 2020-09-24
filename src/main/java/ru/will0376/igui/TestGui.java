package ru.will0376.igui;

import net.minecraft.client.gui.GuiScreen;
import ru.will0376.igui.buttons.IButton;
import ru.will0376.igui.buttons.MButton;

import java.util.ArrayList;

public class TestGui extends GuiScreen {
	ArrayList<IButton> buttons = new ArrayList<>();

	@Override
	public void initGui() {
		super.initGui();
		add(MButton.builder(10, 10, "0")).getStream().forEach(e -> {
			((MButton) e).setAction(() -> {
				int buttonText = Integer.parseInt(((MButton) e).buttonText);
				((MButton) e).buttonText = String.valueOf(buttonText + 1);
				System.out.println(((MButton) e).buttonText);
			});
		});
		add(MButton.builder(10, 30, "10")).getStream().forEach(e -> {
			((MButton) e).setAction(() -> {
				int buttonText = Integer.parseInt(((MButton) e).buttonText);
				((MButton) e).buttonText = String.valueOf(buttonText + 12);
				System.out.println(((MButton) e).buttonText);
			});
		});
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
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
