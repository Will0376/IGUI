package ru.will0376.igui.utils;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import ru.will0376.igui.buttons.IButton;

import java.io.IOException;
import java.util.ArrayList;

public class GuiWrapper extends GuiScreen {
	ArrayList<IButton> buttons = new ArrayList<>();
	boolean rightClick = false;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		buttons.forEach(e -> {
			e.draw(mc, mouseX, mouseY, partialTicks);
			e.mouseAction(mouseX, mouseY);
		});
	}

	@Override
	public void initGui() {
		if (!buttons.isEmpty()) buttons.get(0).setSelected(true);
		super.initGui();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		buttons.forEach(e -> {
			try {
				if (e.isSelected()) {
					int i = buttons.indexOf(e);
					System.out.println("now: " + i);
					switch (keyCode) {
						case Keyboard.KEY_LEFT:
							if (e.canMove()) {
								if (i - 1 >= 0) {
									e.setSelected(false);
									buttons.get(i - 1).setSelected(true);
								}
							}
							break;
						case Keyboard.KEY_RIGHT:
							if (e.canMove() && !rightClick) {
								if (i + 1 < buttons.size()) {
									e.setSelected(false);
									buttons.get(i + 1).setSelected(true);
								}
							}
							rightClick = !rightClick;
							break;
					}
					e.keyInput(typedChar, keyCode);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		});
	}

	public <T extends IButton> T add(T buttonIn) {
		buttons.add(buttonIn);
		return buttonIn;
	}
}
