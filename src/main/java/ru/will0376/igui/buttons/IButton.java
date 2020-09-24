package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;

import java.util.stream.Stream;

public interface IButton {
	int getStaticId();

	IButton setEnabled(boolean bool);

	void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks);

	void mouseAction(int mouseX, int mouseY);

	boolean mouseInArea(Minecraft mc, int mouseX, int mouseY);

	IButton action();

	void click(SoundHandler soundHandlerIn);

	default Stream<? extends IButton> getStream() {
		return Stream.of(this);
	}
}
