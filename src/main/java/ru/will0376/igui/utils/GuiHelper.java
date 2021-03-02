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

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class GuiHelper {
	@Deprecated
	public static void bindTexture(String modId, String path) {
		Texture.bindTexture(Texture.getResource(modId, path));
	}

	@Deprecated
	public static void bindTexture(String all) {
		Texture.bindTexture(Texture.getResource(all));
	}

	@Deprecated
	public static void bindTexture(ResourceLocation location) {
		Texture.bindTexture(location);
	}

	@Deprecated
	public static void cleanRenderCentered(double x, double y, double x2, double y2, int zLevel) {
		Texture.renderTextureCentered(x, y, x2, y2, zLevel);
	}

	@Deprecated
	public static void cleanRender(double x, double y, double x2, double y2, int zLevel) {
		Texture.renderTexture(x, y, x2, y2, zLevel);
	}

	@Deprecated
	public static void renderBlocks(int x, int y, ItemStack it, float scalledX, float scalledY, float Z) {
		Texture.renderBlocks(x, y, it, scalledX, scalledY, Z);
	}

	@Deprecated
	public static void drawScalledString(int x, int y, float scalledX, float scalledY, String string, int color) {
		Text.drawScalledString(x, y, scalledX, scalledY, string, color);
	}

	@Deprecated
	public static void drawString(int x, int y, String string, int color) {
		Text.drawString(x, y, string, color);
	}

	@Deprecated
	public static void drawScalledCenteredString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color) {
		Text.drawScalledCenteredString(fontrenderer, x, y, scalledX, scalledY, zLevel, string, color);
	}

	@Deprecated
	public static void drawScalledCenteredString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color, boolean shadow) {
		Text.drawScalledCenteredString(fontrenderer, x, y, scalledX, scalledY, zLevel, string, color, shadow);
	}

	@Deprecated
	public static void drawScalledString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color) {
		Text.drawScalledString(fontrenderer, x, y, scalledX, scalledY, zLevel, string, color);
	}

	@Deprecated
	public static void drawScalledString(FontRenderer fontrenderer, int x, int y, float scalledX, float scalledY, float zLevel, String string, int color, boolean shadow) {
		Text.drawScalledString(fontrenderer, x, y, scalledX, scalledY, zLevel, string, color, shadow);
	}

	public static class Texture {
		private static final Map<String, ResourceLocation> cachedResources = new HashMap<>();

		public static ResourceLocation getResource(String rs) {
			if (!cachedResources.containsKey(rs)) cachedResources.put(rs, new ResourceLocation(rs));
			return cachedResources.get(rs);
		}

		public static ResourceLocation getResource(String modId, String path) {
			return getResource(modId + ":" + path);
		}

		public static void bindTexture(ResourceLocation location) {
			Minecraft.getMinecraft().renderEngine.bindTexture(location);
		}

		public static void renderTextureCentered(double x, double y, double x2, double y2, int zLevel) {
			renderTexture(x - (x2 / 2), y - (y2 / 2), x2, y2, zLevel);
		}

		public static void bindLocalPlayerSkin() {
			bindTexture(Minecraft.getMinecraft().player.getLocationSkin());
		}

		public static void renderTexture(double x, double y, double x2, double y2, int zLevel) {
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuffer();
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
			bufferbuilder.pos((x + 0), (y + y2), zLevel).tex(0, 1).endVertex();
			bufferbuilder.pos((x + x2), (y + y2), zLevel).tex(1, 1).endVertex();
			bufferbuilder.pos((x + x2), (y + 0), zLevel).tex(1, 0).endVertex();
			bufferbuilder.pos((x + 0), (y + 0), zLevel).tex(0, 0).endVertex();
			tessellator.draw();
		}

		public static void renderSkinHead(int x, int y, float scaleX, float scaleY, float scaleZ) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, 0.0F);
			GlStateManager.scale(4.0F, 4.0F, 0.0F);
			Gui.drawScaledCustomSizeModalRect(0, 0, 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
			GlStateManager.popMatrix();
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
	}

	public static class Text {
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
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y, zLevel);
				GlStateManager.scale(scalledX, scalledY, zLevel);
				fontrenderer.drawString(string, 0, 0, color, shadow);
				GlStateManager.popMatrix();
			} else fontrenderer.drawString(string, x, y, color, shadow);
		}
	}
}
