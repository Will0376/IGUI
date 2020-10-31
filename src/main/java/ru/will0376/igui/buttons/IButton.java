package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import ru.will0376.igui.utils.Mouses;

import java.util.stream.Stream;

public interface IButton {
	int getStaticId();

	IButton setEnabled(boolean bool);

	IButton setVisible(boolean bool);

	void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks);

	void drawText(Minecraft mc, int mouseX, int mouseY, float partialTicks);

	void mouseAction(int mouseX, int mouseY);

	boolean mouseInArea(Minecraft mc, int mouseX, int mouseY);

	IButton action(Mouses click);

	IButton setAction(Runnable action);

	void click(SoundHandler soundHandlerIn);

	default Stream<? extends IButton> stream() {
		return Stream.of(this);
	}

	Mouses getClick();

	boolean isRight();

	boolean isSelected();

	void setSelected(boolean in);

	void keyInput(char typedChar, int keyCode);

	boolean canMove(int keyCode);

	IButton setZLevel(int z);
}
