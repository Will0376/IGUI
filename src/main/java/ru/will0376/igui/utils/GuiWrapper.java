package ru.will0376.igui.utils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import ru.will0376.igui.buttons.IButton;
import ru.will0376.igui.buttons.MTextField;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@SideOnly(Side.CLIENT)
public class GuiWrapper extends GuiScreen {
	public CopyOnWriteArrayList<IButton> buttons = new CopyOnWriteArrayList<>();
	boolean rightClick = false;
	public boolean enableKeyControl = true;


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		buttons.forEach(button -> {
			button.draw(mc, mouseX, mouseY, partialTicks);
			button.mouseAction(mouseX, mouseY);
			if (button.getStaticId() == MTextField.TextField) {
				if (((MTextField) button).getResponse().getAndToggle()) {
					deSelectAllButtons();
					button.setSelected(true);
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
		clearList();
	}

	public void clearList() {
		buttons.clear();
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

	public void closeGui() {
		mc.displayGuiScreen(null);
	}

	public void openGui(GuiScreen screen) {
		mc.displayGuiScreen(screen);
	}

	public void updateThisScreen() {
		mc.displayGuiScreen(this);
	}
}
