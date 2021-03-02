package ru.will0376.igui.obj.armor.loader.obj;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Face {
	public Vertex[] vertices;
	public Vertex[] vertexNormals;
	public Vertex faceNormal;
	public TextureCoordinate[] textureCoordinates;

	@SideOnly(Side.CLIENT)
	public void addFaceForRender(Tessellator tessellator) {
		this.addFaceForRender(tessellator, 5.0E-4F);
	}

	@SideOnly(Side.CLIENT)
	public void addFaceForRender(Tessellator tessellator, float textureOffset) {
		BufferBuilder buffer = tessellator.getBuffer();
		if (this.faceNormal == null) {
			this.faceNormal = this.calculateFaceNormal();
		}

		float averageU = 0.0F;
		float averageV = 0.0F;
		int i;
		if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
			TextureCoordinate[] var6 = this.textureCoordinates;
			int var7 = var6.length;

			for (i = 0; i < var7; ++i) {
				TextureCoordinate textureCoordinate = var6[i];
				averageU += textureCoordinate.u;
				averageV += textureCoordinate.v;
			}

			averageU /= (float) this.textureCoordinates.length;
			averageV /= (float) this.textureCoordinates.length;
		}

		for (i = 0; i < this.vertices.length; ++i) {
			if (this.textureCoordinates != null && this.textureCoordinates.length > 0) {
				float offsetU = textureOffset;
				float offsetV = textureOffset;
				if (this.textureCoordinates[i].u > averageU) {
					offsetU = -textureOffset;
				}

				if (this.textureCoordinates[i].v > averageV) {
					offsetV = -textureOffset;
				}

				buffer.pos(this.vertices[i].x, this.vertices[i].y, this.vertices[i].z)
						.tex(this.textureCoordinates[i].u + offsetU, this.textureCoordinates[i].v + offsetV)
						.normal(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z)
						.endVertex();
			} else {
				buffer.pos(this.vertices[i].x, this.vertices[i].y, this.vertices[i].z)
						.tex(0.0D, 0.0D)
						.normal(this.faceNormal.x, this.faceNormal.y, this.faceNormal.z)
						.endVertex();
			}
		}

	}

	public Vertex calculateFaceNormal() {
		Vec3d v1 = new Vec3d(this.vertices[1].x - this.vertices[0].x, this.vertices[1].y - this.vertices[0].y, this.vertices[1].z - this.vertices[0].z);
		Vec3d v2 = new Vec3d(this.vertices[2].x - this.vertices[0].x, this.vertices[2].y - this.vertices[0].y, this.vertices[2].z - this.vertices[0].z);
		Vec3d normalVector = v1.crossProduct(v2).normalize();
		return new Vertex((float) normalVector.x, (float) normalVector.y, (float) normalVector.z);
	}
}
