package ru.will0376.igui.obj.armor.loader.obj;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

public class GroupObject {
	private final int glListId;
	public String name;
	public ArrayList<Face> faces;
	public int glDrawingMode;
	private boolean first;

	public GroupObject() {
		this("");
	}

	public GroupObject(String name) {
		this(name, -1);
	}

	public GroupObject(String name, int glDrawingMode) {
		this.faces = new ArrayList<>();
		this.name = name;
		this.glDrawingMode = glDrawingMode;
		this.glListId = GlStateManager.glGenLists(1);
		this.first = true;
	}

	@SideOnly(Side.CLIENT)
	public void render() {
		if (!this.first) {
			GlStateManager.callList(this.glListId);
		} else {
			this.first = false;
			GlStateManager.glNewList(this.glListId, 4865);
			if (this.faces.size() > 0) {
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder builder = tessellator.getBuffer();
				builder.begin(this.glDrawingMode, DefaultVertexFormats.POSITION_TEX_NORMAL);
				this.render(tessellator);
				tessellator.draw();
			}

			GlStateManager.glEndList();
		}

	}

	@SideOnly(Side.CLIENT)
	public void render(Tessellator tessellator) {
		for (Face face : this.faces)
			face.addFaceForRender(tessellator);
	}
}
