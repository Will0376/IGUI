package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import ru.will0376.igui.utils.Mouses;

import java.util.stream.Stream;

public interface IButton {
	int getStaticId();

	IButton setEnabled(boolean bool);

	void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks);

	void mouseAction(int mouseX, int mouseY);

	boolean mouseInArea(Minecraft mc, int mouseX, int mouseY);

	IButton action(Mouses click);

	void setAction(Runnable action);

	void click(SoundHandler soundHandlerIn);

	default Stream<? extends IButton> getStream() {
		return Stream.of(this);
	}

	Mouses getClick();

	boolean isRight();
}
