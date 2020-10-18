package ru.will0376.igui.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiHelper {

	public static void bindTexture(String modId, String path) {
		bindTexture(modId + ":" + path);
	}

	public static void bindTexture(String all) {
		bindTexture(new ResourceLocation(all));
	}

	public static void bindTexture(ResourceLocation location) {
		Minecraft.getMinecraft().renderEngine.bindTexture(location);
	}

	public static void drawTextured(int x, int y) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(x, y + (176 + 20), 0).tex(0, 1).endVertex(); //нижний-левый
		bufferbuilder.pos(x + 400, y + (176 + 20), 0).tex(1, 1).endVertex();//нижний-правый
		bufferbuilder.pos(x + 400, y - 20, 0).tex(1, 0).endVertex();//верхний-правый
		bufferbuilder.pos(x, y - 20, 0).tex(0, 0).endVertex();//верхний-левый
		tessellator.draw();
	}

	public static void cleanRenderCentered(double x, double y, double x2, double y2, int zLevel) {
		cleanRender(x - (x2 / 2), y - (y2 / 2), x2, y2, zLevel);
	}

	public static void cleanRender(double x, double y, double x2, double y2, int zLevel) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((x + 0), (y + y2), zLevel).tex(0, 1).endVertex();
		bufferbuilder.pos((x + x2), (y + y2), zLevel).tex(1, 1).endVertex();
		bufferbuilder.pos((x + x2), (y + 0), zLevel).tex(1, 0).endVertex();
		bufferbuilder.pos((x + 0), (y + 0), zLevel).tex(0, 0).endVertex();
		tessellator.draw();
	}


	public static void renderSkinHead(int x, int y) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(mc.player.getLocationSkin());
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, 0.0F);
		GlStateManager.scale(4.0F, 4.0F, 0.0F);
		Gui.drawScaledCustomSizeModalRect(0, 0, 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
		GlStateManager.popMatrix();
	}

	public static void renderBlocks(int x, int y, ItemStack it, float scalledX, float scalledY) {
		renderBlocks(x, y, it, scalledX, scalledY, 0);
	}

	public static void renderBlocks(int x, int y, ItemStack it, float scalledX, float scalledY, float Z) {
		if (scalledX == 0) scalledX = 1;
		if (scalledY == 0) scalledY = 1;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, Z);
		GlStateManager.scale(scalledX, scalledY, 0.0F);
		Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(it, 0, 0);
		GlStateManager.popMatrix();
	}

	public static void drawScalledString(int x, int y, float scalledX, float scalledY, String string, int color) {
		drawScalledString(Minecraft.getMinecraft().fontRenderer, x, y, scalledX, scalledY, 2, string, color);
	}

	public static void drawString(int x, int y, String string, int color) {
		drawScalledString(Minecraft.getMinecraft().fontRenderer, x, y, 1, 1, 2, string, color);
	}

	public static void drawScalledCenteredString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color) {
		drawScalledString(fontrenderer, x - fontrenderer.getStringWidth(string) / 2, y, scalledX, scalledY, zLevel, string, color);
	}

	public static void drawScalledCenteredString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color, boolean shadow) {
		drawScalledString(fontrenderer, x - fontrenderer.getStringWidth(string) / 2, y, scalledX, scalledY, zLevel, string, color, shadow);
	}

	public static void drawScalledString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color) {
		drawScalledString(fontrenderer, x, y, scalledX, scalledY, zLevel, string, color, false);
	}

	public static void drawScalledString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color, boolean shadow) {
		if (color == -1) color = 16777215;
		if (scalledX != 1.0f || scalledY != 1.0f || zLevel != 0) {
			GL11.glPushMatrix();
			GL11.glTranslatef(x, y, zLevel);
			GL11.glScalef(scalledX, scalledY, zLevel);
			fontrenderer.drawString(string, 0, 0, color, shadow);
			GL11.glPopMatrix();
		} else fontrenderer.drawString(string, x, y, color, shadow);
	}
}
