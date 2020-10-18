package ru.will0376.igui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ru.will0376.igui.utils.Mouses;

import java.util.function.Predicate;
/**
 * @deprecated Не готов.
 */
@Deprecated
@SideOnly(Side.CLIENT)
public class MTextField extends Gui implements IButton {
	public static int TextField = 3;
	private final Predicate<String> validator = s -> true;
	private final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
	private final int enabledColor = 14737632;
	private final int disabledColor = 7368816;
	private final boolean enableBackgroundDrawing = true;
	private final int maxStringLength = 32;
	private final GuiResponse response;
	public boolean enabled = true;
	public Mouses click;
	public int x;
	public int y;
	public int width;
	public int height;
	public boolean selected;
	public boolean visible;
	public String defaultText;
	public String inputText;
	public int cursor = 0;
	private int cursorCounter;
	private int lineScrollOffset;
	private int selectionEnd;
	private Runnable action;

	public MTextField(int x, int y, int width, int height, String inputText, GuiResponse response) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.inputText = inputText;
		visible = true;
		this.response = response;
	}

	public GuiResponse getResponse() {
		return response;
	}

	@Override
	public int getStaticId() {
		return TextField;
	}

	@Override
	public IButton setEnabled(boolean bool) {
		this.enabled = bool;
		return this;
	}

	@Override
	public void draw(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		drawTextBox();
	}

	@Override
	public void drawText(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		//TODO
	}

	public void drawTextBox() {
		if (visible) {
			drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -6250336);
			drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -16777216);

			int i = this.enabled ? this.enabledColor : this.disabledColor;
			int j = this.cursor - this.lineScrollOffset;
			int k = this.selectionEnd - this.lineScrollOffset;
			String s = this.fontRenderer.trimStringToWidth(this.inputText.substring(this.lineScrollOffset), this.width - 8);
			boolean flag = j >= 0 && j <= s.length();
			boolean flag1 = this.selected && this.cursorCounter / 6 % 2 == 0 && flag;
			int l = this.enableBackgroundDrawing ? this.x + 4 : this.x;
			int i1 = this.enableBackgroundDrawing ? this.y + (this.height - 8) / 2 : this.y;
			int j1 = l;

			if (k > s.length()) {
				k = s.length();
			}

			if (!s.isEmpty()) {
				String s1 = flag ? s.substring(0, j) : s;
				j1 = this.fontRenderer.drawStringWithShadow(s1, (float) l, (float) i1, i);
			}

			boolean flag2 = this.cursor < this.inputText.length() || this.inputText.length() >= this.maxStringLength;
			int k1 = j1;

			if (!flag) {
				k1 = j > 0 ? l + this.width : l;
			} else if (flag2) {
				k1 = j1 - 1;
				--j1;
			}

			if (!s.isEmpty() && flag && j < s.length()) {
				j1 = this.fontRenderer.drawStringWithShadow(s.substring(j), (float) j1, (float) i1, i);
			}

			if (flag1) {
				if (flag2) {
					Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
				} else {
					this.fontRenderer.drawStringWithShadow("_", (float) k1, (float) i1, i);
				}
			}

			if (k != j) {
				int l1 = l + this.fontRenderer.getStringWidth(s.substring(0, k));
				this.drawSelectionBox(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRenderer.FONT_HEIGHT);
			}
		}
	}

	public void writeText(String textToWrite) {
		String s = "";
		String s1 = ChatAllowedCharacters.filterAllowedCharacters(textToWrite);
		int i = Math.min(this.cursor, this.selectionEnd);
		int j = Math.max(this.cursor, this.selectionEnd);
		int k = this.maxStringLength - this.inputText.length() - (i - j);

		if (!this.inputText.isEmpty()) {
			s = s + this.inputText.substring(0, i);
		}

		int l;

		if (k < s1.length()) {
			s = s + s1.substring(0, k);
			l = k;
		} else {
			s = s + s1;
			l = s1.length();
		}

		if (!this.inputText.isEmpty() && j < this.inputText.length()) {
			s = s + this.inputText.substring(j);
		}

		if (this.validator.test(s)) {
			this.inputText = s;
			this.moveCursorBy(i - this.selectionEnd + l);
		}
	}

	public void moveCursorBy(int num) {
		this.setCursorPosition(this.selectionEnd + num);
	}

	public void setCursorPosition(int pos) {
		this.cursor = pos;
		int i = this.inputText.length();
		this.cursor = MathHelper.clamp(this.cursor, 0, i);
		this.setSelectionPos(this.cursor);
	}

	public void setSelectionPos(int position) {
		int i = this.inputText.length();
		if (position > i) position = i;
		if (position < 0) position = 0;

		this.selectionEnd = position;

		if (this.fontRenderer != null) {
			if (this.lineScrollOffset > i) this.lineScrollOffset = i;

			int j = this.width - 8;
			String s = this.fontRenderer.trimStringToWidth(this.inputText.substring(this.lineScrollOffset), j);
			int k = s.length() + this.lineScrollOffset;

			if (position == this.lineScrollOffset)
				this.lineScrollOffset -= this.fontRenderer.trimStringToWidth(this.inputText, j, true).length();

			if (position > k) this.lineScrollOffset += position - k;
			else if (position <= this.lineScrollOffset) this.lineScrollOffset -= this.lineScrollOffset - position;

			this.lineScrollOffset = MathHelper.clamp(this.lineScrollOffset, 0, i);
		}
	}

	private void drawSelectionBox(int startX, int startY, int endX, int endY) {
		if (startX < endX) {
			int i = startX;
			startX = endX;
			endX = i;
		}

		if (startY < endY) {
			int j = startY;
			startY = endY;
			endY = j;
		}

		if (endX > this.x + this.width) {
			endX = this.x + this.width;
		}

		if (startX > this.x + this.width) {
			startX = this.x + this.width;
		}

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.enableColorLogic();
		GlStateManager.colorLogicOp(GlStateManager.LogicOp.OR_REVERSE);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(startX, endY, 0.0D).endVertex();
		bufferbuilder.pos(endX, endY, 0.0D).endVertex();
		bufferbuilder.pos(endX, startY, 0.0D).endVertex();
		bufferbuilder.pos(startX, startY, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.disableColorLogic();
		GlStateManager.enableTexture2D();
	}

	@Override
	public void mouseAction(int mouseX, int mouseY) {
		if (!selected && mouseInArea(Minecraft.getMinecraft(), mouseX, mouseY) && Mouse.isButtonDown(0)) {
			response.setBool(true);
		}
	}

	@Override
	public boolean mouseInArea(Minecraft mc, int mouseX, int mouseY) {
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}

	@Override
	public IButton action(Mouses click) {
		this.click = click;
		return this;
	}

	@Override
	public void setAction(Runnable action) {

	}

	@Override
	public void click(SoundHandler soundHandlerIn) {

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
		return selected;
	}

	@Override
	public void setSelected(boolean in) {
		selected = in;
	}

	public void deleteWords(int num) {
		if (!this.inputText.isEmpty()) {
			if (this.selectionEnd != this.cursor) this.writeText("");
			else this.deleteFromCursor(this.getNthWordFromCursor(num) - this.cursor);
		}
	}

	public void deleteFromCursor(int num) {
		if (!this.inputText.isEmpty()) {
			if (this.selectionEnd != this.cursor) this.writeText("");
			else {
				boolean flag = num < 0;
				int i = flag ? this.cursor + num : this.cursor;
				int j = flag ? this.cursor : this.cursor + num;
				String s = "";

				if (i >= 0) s = this.inputText.substring(0, i);
				if (j < this.inputText.length()) s = s + this.inputText.substring(j);
				if (this.validator.test(s)) {
					this.inputText = s;
					if (flag) this.moveCursorBy(num);
				}
			}
		}
	}

	public int getNthWordFromCursor(int numWords) {
		return this.getNthWordFromPos(numWords, cursor);
	}

	public int getNthWordFromPos(int n, int pos) {
		return this.getNthWordFromPosWS(n, pos, true);
	}

	public int getNthWordFromPosWS(int n, int pos, boolean skipWs) {
		int i = pos;
		boolean flag = n < 0;
		int j = Math.abs(n);

		for (int k = 0; k < j; ++k) {
			if (!flag) {
				int l = this.inputText.length();
				i = this.inputText.indexOf(32, i);

				if (i == -1) i = l;
				else while (skipWs && i < l && this.inputText.charAt(i) == ' ') ++i;
			} else {
				while (skipWs && i > 0 && this.inputText.charAt(i - 1) == ' ') --i;
				while (i > 0 && this.inputText.charAt(i - 1) != ' ') --i;
			}
		}
		return i;
	}

	@Override
	public void keyInput(char typedChar, int keyCode) {
		switch (keyCode) {
			case Keyboard.KEY_LEFT:
				setCursorPosition(cursor - 1);
				break;
			case Keyboard.KEY_RIGHT:
				setCursorPosition(cursor + 1);
				break;
			case Keyboard.KEY_BACK:
				if (GuiScreen.isCtrlKeyDown() && this.enabled) this.deleteWords(-1);
				else if (this.enabled) this.deleteFromCursor(-1);

				break;
			default:
				writeText(Character.toString(typedChar));
				break;
		}
	}

	@Override
	public boolean canMove(int keyCode) {
		return (cursor == 0 && keyCode == Keyboard.KEY_LEFT) || (cursor == inputText.length() && keyCode == Keyboard.KEY_RIGHT);
	}
}
