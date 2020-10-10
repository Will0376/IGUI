package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ru.will0376.igui.utils.GuiHelper;
import ru.will0376.igui.utils.Mouses;

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
	public Minecraft mc = Minecraft.getMinecraft();
	private Runnable action = () -> {
	};
	private Mouses click;
	private boolean mouseButton1 = false;
	private boolean mouseButton2 = false;
	private boolean isSelected = false;

	public MButton(int x, int y, int width, int height, String buttonText, ResourceLocation firstTexture, ResourceLocation secondTexture) {
		this.width = width;
		this.height = height;
		this.enabled = true;
		this.x = x;
		this.y = y;
		this.buttonText = buttonText;
		this.firstTexture = firstTexture;
		this.secondTexture = secondTexture;
		this.visible = true;
	}

	public static MButton builder(int x, int y, String buttonText) {
		return new MButton(x, y, 200, 20, buttonText, BUTTON_DEFAULT_TEXTURES, null);
	}

	public static MButton builder(int x, int y, String buttonText, ResourceLocation firstTexture) {
		return new MButton(x, y, 200, 20, buttonText, firstTexture, null);
	}

	public static MButton builder(int x, int y, String buttonText, ResourceLocation firstTexture, ResourceLocation secondTexture) {
		return new MButton(x, y, 200, 20, buttonText, firstTexture, secondTexture);
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

			boolean mouseOver = mouseInArea(mc, mouseX, mouseY) || isSelected;

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
			} else {
				GuiHelper.cleanRender(x, y, width, height, (int) zLevel);
			}
			drawText(mc, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	public void drawText(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		int j = this.enabled ? 14737632 : 10526880;
		this.drawCenteredString(mc.fontRenderer, this.buttonText, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
	}

	protected int getHoverState(boolean mouseOver) {
		int i = 1;

		if (!this.enabled) i = 0;
		else if (mouseOver) i = 2;

		return i;
	}

	@Override
	public void mouseAction(int mouseX, int mouseY) {
		boolean mouseInArea = mouseInArea(Minecraft.getMinecraft(), mouseX, mouseY);
		if (mouseInArea && Mouse.isButtonDown(0) && !mouseButton1) action(Mouses.LMB);

		if (mouseInArea && Mouse.isButtonDown(1) && !mouseButton2) action(Mouses.RMB);

		mouseButton1 = Mouse.isButtonDown(0);
		mouseButton2 = Mouse.isButtonDown(1);
	}

	@Override
	public boolean mouseInArea(Minecraft mc, int mouseX, int mouseY) {
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}

	@Override
	public IButton action(Mouses click) {
		click(mc.getSoundHandler());
		this.click = click;
		action.run();
		return this;
	}

	@Override
	public void click(SoundHandler soundHandlerIn) {
		soundHandlerIn.playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	@Override
	public Mouses getClick() {
		return click;
	}

	@Override
	public void setAction(Runnable action) {
		this.action = action;
	}

	@Override
	public boolean isRight() {
		return click == Mouses.RMB;
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void setSelected(boolean in) {
		isSelected = in;
	}

	@Override
	public void keyInput(char typedChar, int keyCode) {
		if (isSelected) {
			switch (keyCode) {
				case Keyboard.KEY_NUMPADENTER:
				case Keyboard.KEY_RETURN:
				case Keyboard.KEY_SPACE:
					if (GuiScreen.isShiftKeyDown()) action(Mouses.RMB);
					else action(Mouses.LMB);
					break;
				case Keyboard.KEY_UP:
					action(Mouses.LMB);
					break;
				case Keyboard.KEY_DOWN:
					action(Mouses.RMB);
					break;
			}
		}
	}

	@Override
	public boolean canMove(int keyCode) {
		return true;
	}

	public MButton get() {
		return this;
	}
}
