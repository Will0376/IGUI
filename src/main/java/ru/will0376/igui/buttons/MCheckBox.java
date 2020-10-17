package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ru.will0376.igui.utils.GuiHelper;
import ru.will0376.igui.utils.Mouses;

public class MCheckBox extends Gui implements IButton {
	public static int CHECKBOX = 1;
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
	public ResourceLocation firstTexture;
	public ResourceLocation secondTexture;
	public int width;
	public int height;
	public int x;
	public int y;
	public int offsetTextX;
	public int offsetTextY;
	public float scaledTextX;
	public float scaledTextY;
	public boolean enabled;
	public boolean visible;
	public Minecraft mc = Minecraft.getMinecraft();
	private boolean isChecked;
	private String buttonString;
	private Mouses click;
	public boolean mouseButton1 = false;
	public boolean mouseButton2 = false;
	private Runnable action = () -> {
	};
	private boolean isSelected = false;

	public MCheckBox(int x, int y, int width, int height, boolean isChecked, ResourceLocation firstTexture, ResourceLocation secondTexture, String name) {
		this.isChecked = isChecked;
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.firstTexture = firstTexture;
		this.secondTexture = secondTexture;
		this.enabled = true;
		this.visible = true;
		this.buttonString = name;
	}

	public static MCheckBox builder(int x, int y) {
		return new MCheckBox(x, y, 20, 20, false, null, null, "x");
	}

	public static MCheckBox builder(int x, int y, boolean isChecked) {
		return new MCheckBox(x, y, 20, 20, isChecked, null, null, "x");
	}

	public static MCheckBox builder(int x, int y, String name) {
		return new MCheckBox(x, y, 20, 20, false, null, null, name);
	}

	public static MCheckBox builder(int x, int y, boolean isChecked, String name) {
		return new MCheckBox(x, y, 20, 20, isChecked, null, null, name);
	}

	public static MCheckBox builder(int x, int y, boolean isChecked, ResourceLocation firstTexture, String name) {
		return new MCheckBox(x, y, 20, 20, isChecked, firstTexture, null, name);
	}

	public static MCheckBox builder(int x, int y, boolean isChecked, ResourceLocation firstTexture, ResourceLocation secondTexture, String name) {
		return new MCheckBox(x, y, 20, 20, isChecked, firstTexture, secondTexture, name);
	}

	@Override
	public int getStaticId() {
		return CHECKBOX;
	}

	@Override
	public IButton setEnabled(boolean bool) {
		enabled = bool;
		return this;
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible) {
			if (firstTexture == null && secondTexture == null) {
				GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
				if (this.isChecked) {
					GuiHelper.drawScalledString(mc.fontRenderer, this.x + 5 + offsetTextX, this.y - (height / 2) + 5 + offsetTextY, scaledTextX, scaledTextY, 1, buttonString, -1);
				}
				if (this.isSelected)
					GuiHelper.drawScalledString(mc.fontRenderer, this.x + 22 + offsetTextX, this.y - (height / 2) + 4 + offsetTextY, scaledTextX, scaledTextY, 1, "<", -1);

			} else {
				if (!isChecked) Minecraft.getMinecraft().getTextureManager().bindTexture(firstTexture);
				else Minecraft.getMinecraft().getTextureManager().bindTexture(secondTexture);

				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				GuiHelper.cleanRender(x, y, width, height, (int) zLevel);
				//this.drawTexturedModalRect(this.x, this.y, 0, 46, this.width / 2, this.height);
			}
		}
	}

	@Override
	public void drawText(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		//NP;
	}

	@Override
	public void mouseAction(int mouseX, int mouseY) {
		if (mouseInArea(Minecraft.getMinecraft(), mouseX, mouseY) && Mouse.isButtonDown(0) && !mouseButton1) {
			action(Mouses.LMB);
		}
		if (mouseInArea(Minecraft.getMinecraft(), mouseX, mouseY) && Mouse.isButtonDown(1) && !mouseButton2) {
			action(Mouses.RMB);
		}

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

	public MCheckBox get() {
		return this;
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
					action(Mouses.RMB);
					break;
			}
		}
	}

	@Override
	public boolean canMove(int keyCode) {
		return true;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public boolean toggleIsChecked() {
		isChecked = !isChecked;
		return isChecked;
	}

	public String getButtonString() {
		return buttonString;
	}

	public MCheckBox setButtonString(String buttonString) {
		this.buttonString = buttonString;
		return this;
	}

	public MCheckBox addOffsetTextX(int in) {
		this.offsetTextX = offsetTextX + in;
		return this;
	}

	public MCheckBox addOffsetTextY(int in) {
		this.offsetTextY = offsetTextY + in;
		return this;
	}

	public MCheckBox setScaledTextX(float scaledTextX) {
		this.scaledTextX = scaledTextX;
		return this;
	}

	public MCheckBox setScaledTextY(float scaledTextY) {
		this.scaledTextY = scaledTextY;
		return this;
	}
}
