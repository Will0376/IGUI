package ru.will0376.igui.utils;

import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import ru.will0376.igui.buttons.IButton;
import ru.will0376.igui.buttons.MTextField;

import java.io.IOException;
import java.util.ArrayList;

public class GuiWrapper extends GuiScreen {
	public ArrayList<IButton> buttons = new ArrayList<>();
	boolean rightClick = false;
	public boolean enableKeyControl = true;
	public boolean updateScreen = false;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!updateScreen) buttons.forEach(e -> {
			e.draw(mc, mouseX, mouseY, partialTicks);
			e.mouseAction(mouseX, mouseY);
			if (e.getStaticId() == MTextField.TextField) {
				if (((MTextField) e).getResponse().getAndToggle()) {
					deSelectAllButtons();
					e.setSelected(true);
				}
			}
		});
	}

	public void deSelectAllButtons() {
		buttons.forEach(e -> e.setSelected(false));
	}

	@Override
	public void initGui() {
		super.initGui();
		buttons.clear();
		updateScreen = false;

	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		buttons.forEach(e -> {
			try {
				if (e.isSelected()) {
					int i = buttons.indexOf(e);
					if (enableKeyControl) switch (keyCode) {
						case Keyboard.KEY_LEFT:
							if (e.canMove(keyCode)) {
								if (i - 1 >= 0) {
									e.setSelected(false);
									buttons.get(i - 1).setSelected(true);
								}
							}
							break;
						case Keyboard.KEY_RIGHT:
							if (e.canMove(keyCode) && !rightClick) {
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

	public void setSelected(int index) {
		try {
			buttons.get(index).setSelected(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateThisScreen() {
		updateScreen = true;
		mc.displayGuiScreen(this);
	}
}
