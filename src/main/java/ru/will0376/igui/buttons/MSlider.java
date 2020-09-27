package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ru.will0376.igui.utils.GuiHelper;
import ru.will0376.igui.utils.Mouses;

public class MSlider extends Gui implements IButton {
	public static int SLIDER = 2;
	protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png"); //TODO: переделать на свои текстуры
	private final boolean mouseButton1 = false;
	public boolean enabled = true;
	public int x;
	public int y;
	public int width;
	public int height;
	public float sliderPosition, defaultValue;
	public float min, max;
	public String name;
	public String defName;
	Minecraft minecraft = Minecraft.getMinecraft();
	private final ResourceLocation bg;
	private final ResourceLocation button;
	private boolean mouseButton2 = false;
	private Runnable action = () -> {
	};
	private Mouses click;
	private boolean isSelected = false;

	public MSlider(int x, int y, int width, int height, float minIn, float maxIn, float defaultValue, ResourceLocation bg, ResourceLocation button, String name) {
		this.sliderPosition = (defaultValue - minIn) / (maxIn - minIn);
		this.defaultValue = defaultValue;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.min = minIn;
		this.max = maxIn;
		this.name = name;
		this.bg = bg;
		this.button = button;
		this.defName = name;
	}

	public static MSlider builder(int x, int y, float defaultValue) {
		return new MSlider(x, y, 200, 20, 0, 1, defaultValue, null, null, String.valueOf(defaultValue));
	}

	public static MSlider builder(int x, int y, int width, int height, float defaultValue) {
		return new MSlider(x, y, width, height, 0, 1, defaultValue, null, null, String.valueOf(defaultValue));
	}

	public static MSlider builder(int x, int y, int width, int height, float defaultValue, String name) {
		return new MSlider(x, y, width, height, 0, 1, defaultValue, null, null, name);
	}

	public static MSlider builder(int x, int y, int width, int height, float defaultValue, ResourceLocation bg, ResourceLocation button, String name) {
		return new MSlider(x, y, width, height, 0, 1, defaultValue, bg, button, name);
	}
	@Override
	public int getStaticId() {
		return SLIDER;
	}

	@Override
	public IButton setEnabled(boolean bool) {
		enabled = bool;
		return this;
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (bg == null) mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
		else mc.getTextureManager().bindTexture(bg);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		if (bg == null) {
			this.drawTexturedModalRect(this.x, this.y, 0, 46, this.width / 2, this.height);
			this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46, this.width / 2, this.height);
		} else {
			GuiHelper.cleanRender(x, y, width, height, 1);
		}
		if (button == null) {
			if (isSelected) {
				this.drawTexturedModalRect(this.x + (int) (this.sliderPosition * (float) (this.width - 8)), this.y, 0, 86, 4, this.height);
				this.drawTexturedModalRect(this.x + (int) (this.sliderPosition * (float) (this.width - 8)) + 4, this.y, 196, 86, 4, this.height);
			} else {
				this.drawTexturedModalRect(this.x + (int) (this.sliderPosition * (float) (this.width - 8)), this.y, 0, 66, 4, this.height);
				this.drawTexturedModalRect(this.x + (int) (this.sliderPosition * (float) (this.width - 8)) + 4, this.y, 196, 66, 4, this.height);
			}
		}
		FontRenderer fontrenderer = mc.fontRenderer;
		this.drawCenteredString(fontrenderer, this.name, this.x + this.width / 2, this.y + (this.height - 8) / 2, 14737632);
	}

	@Override
	public void mouseAction(int mouseX, int mouseY) {
		boolean mouse = mouseInArea(minecraft, mouseX, mouseY);
		if (mouse && Mouse.isButtonDown(0) && !mouseButton1) {
			this.sliderPosition = (float) (mouseX - (this.x + 4)) / (float) (this.width - 8);
			if (this.sliderPosition < 0.0F) this.sliderPosition = 0.0F;
			if (this.sliderPosition > 1.0F) this.sliderPosition = 1.0F;
			action(Mouses.LMB);
		}
		if (mouse && Mouse.isButtonDown(1) && !mouseButton2) action(Mouses.RMB);
		this.mouseButton2 = Mouse.isButtonDown(1);
	}

	@Override
	public boolean mouseInArea(Minecraft mc, int mouseX, int mouseY) {
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}

	@Override
	public IButton action(Mouses click) {
		this.click = click;
		action.run();
		return this;
	}

	@Override
	public void setAction(Runnable action) {
		this.action = action;
	}

	@Override
	public void click(SoundHandler soundHandlerIn) {
		//disabled
	}

	@Override
	public Mouses getClick() {
		return click;
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
				case Keyboard.KEY_R:
					setDefault();
					break;
				case Keyboard.KEY_UP:
					changePosition(true);
					break;
				case Keyboard.KEY_DOWN:
					changePosition(false);
					break;
			}
		}
	}

	private void changePosition(boolean up) {
		if (up) {
			if (GuiScreen.isShiftKeyDown()) this.sliderPosition = sliderPosition + 0.3f;
			else this.sliderPosition = sliderPosition + 0.1f;
			if (this.sliderPosition > 1.0F) this.sliderPosition = 1.0F;
			action(Mouses.LMB);
		} else {
			if (GuiScreen.isShiftKeyDown()) this.sliderPosition = sliderPosition - 0.3f;
			else this.sliderPosition = sliderPosition - 0.1f;
			if (this.sliderPosition < 0.0F) this.sliderPosition = 0.0F;
			action(Mouses.LMB);
		}

	}

	@Override
	public boolean canMove() {
		return true;
	}

	public void setDefault() {
		sliderPosition = defaultValue;
		name = defName;
	}
}
