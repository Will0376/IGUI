package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class MButton extends Gui implements IButton {
	public static int BUTTON = 0;
	protected static final ResourceLocation BUTTON_DEFAULT_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
	public ResourceLocation firstTexture;
	public ResourceLocation secondTexture;
	public int width;
	public int height;
	public int x;
	public int y;
	public boolean enabled;
	public boolean visible;
	public String buttonText;
	private Runnable action = () -> {
	};
	private boolean mouseButton1 = false;

	private MButton() {
		//Use builer.
	}

	public static MButton builder(int x, int y, String buttonText) {
		return builder(x, y, 200, 20, buttonText, BUTTON_DEFAULT_TEXTURES, null);
	}

	public static MButton builder(int x, int y, String buttonText, ResourceLocation firstTexture) {
		return builder(x, y, 200, 20, buttonText, firstTexture, null);
	}

	public static MButton builder(int x, int y, String buttonText, ResourceLocation firstTexture, ResourceLocation secondTexture) {
		return builder(x, y, 200, 20, buttonText, firstTexture, secondTexture);
	}

	public static MButton builder(int x, int y, int width, int height, String buttonText, ResourceLocation firstTexture, ResourceLocation secondTexture) {
		MButton mb = new MButton();
		mb.width = width;
		mb.height = height;
		mb.enabled = true;
		mb.x = x;
		mb.y = y;
		mb.buttonText = buttonText;
		mb.firstTexture = firstTexture;
		mb.secondTexture = secondTexture;
		mb.visible = true;
		return mb;
	}

	@Override
	public int getStaticId() {
		return BUTTON;
	}

	@Override
	public IButton setEnabled(boolean bool) {
		enabled = bool;
		return this;
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			int j = this.enabled ? 14737632 : 10526880;
			boolean mouseOver = mouseInArea(mc, mouseX, mouseY);

			if (mouseOver && secondTexture != null) mc.getTextureManager().bindTexture(secondTexture);
			else mc.getTextureManager().bindTexture(firstTexture);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

			if (secondTexture == null) {
				int i = this.getHoverState(mouseOver);
				this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
				this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
			}
			this.drawCenteredString(mc.fontRenderer, this.buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
		}
	}

	protected int getHoverState(boolean mouseOver) {
		int i = 1;

		if (!this.enabled) i = 0;
		else if (mouseOver) i = 2;

		return i;
	}

	@Override
	public void mouseAction(int mouseX, int mouseY) {
		if (mouseInArea(Minecraft.getMinecraft(), mouseX, mouseY) && Mouse.isButtonDown(0) && !mouseButton1) {
			action();
		}
		mouseButton1 = Mouse.isButtonDown(0);
	}

	@Override
	public boolean mouseInArea(Minecraft mc, int mouseX, int mouseY) {
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}

	@Override
	public IButton action() {
		action.run();
		return this;
	}

	@Override
	public void click(SoundHandler soundHandlerIn) {
		soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	public void setAction(Runnable action) {
		this.action = action;
	}
}
