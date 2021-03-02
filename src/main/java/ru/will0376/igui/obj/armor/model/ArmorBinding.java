package ru.will0376.igui.obj.armor.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class ArmorBinding extends ModelBiped {
	public int color = -1;
	private EntityEquipmentSlot slot;

	public void pre() {
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(770, 771);
	}

	public void post() {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
	}

	public void bind(ResourceLocation texture) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}

	public void transformHead() {
	}

	public void partHead() {
	}

	public void transformBody() {
	}

	public void partBody() {
	}

	public void transformRightArm() {
	}

	public void partRightArm() {
	}

	public void transformLeftArm() {
	}

	public void partLeftArm() {
	}

	public void transformRightLeg() {
	}

	public void partRightLeg() {
	}

	public void transformLeftLeg() {
	}

	public void partLeftLeg() {
	}

	public void transformRightBoot() {
	}

	public void partRightBoot() {
	}

	public void transformLeftBoot() {
	}

	public void partLeftBoot() {
	}

	public ArmorBinding setSlot(EntityEquipmentSlot slot) {
		this.slot = slot;
		return this;
	}

	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yaw, float pitch, float scale) {
		GlStateManager.pushMatrix();
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, yaw, pitch, scale, entity);
		if (entity.isSneaking()) {
			GlStateManager.translate(0.0F, 0.2F, 0.0F);
		}

		float f6;
		float blue;
		if (entity instanceof EntityZombie || entity instanceof EntityGiantZombie) {
			f6 = MathHelper.sin(super.swingProgress * 3.1415927F);
			blue = MathHelper.sin((1.0F - (1.0F - super.swingProgress) * (1.0F - super.swingProgress)) * 3.1415927F);
			super.bipedRightArm.rotateAngleZ = 0.0F;
			super.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
			super.bipedRightArm.rotateAngleX = -1.5707964F;
			ModelRenderer modelRenderer = super.bipedRightArm;
			modelRenderer.rotateAngleX -= f6 * 1.2F - blue * 0.4F;
			modelRenderer = super.bipedRightArm;
			modelRenderer.rotateAngleZ += MathHelper.cos(limbSwingAmount * 0.09F) * 0.05F + 0.05F;
			modelRenderer = super.bipedRightArm;
			modelRenderer.rotateAngleX += MathHelper.sin(limbSwingAmount * 0.067F) * 0.05F;
			super.bipedLeftArm.rotateAngleZ = 0.0F;
			super.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
			super.bipedLeftArm.rotateAngleX = -1.5707964F;
			modelRenderer = super.bipedLeftArm;
			modelRenderer.rotateAngleX -= f6 * 1.2F - blue * 0.4F;
			modelRenderer = super.bipedLeftArm;
			modelRenderer.rotateAngleZ -= MathHelper.cos(limbSwingAmount * 0.09F) * 0.05F + 0.05F;
			modelRenderer = super.bipedLeftArm;
			modelRenderer.rotateAngleX -= MathHelper.sin(limbSwingAmount * 0.067F) * 0.05F;
			if (entity instanceof EntityGiantZombie) {
				GlStateManager.scale(6.0F, 6.0F, 6.0F);
			} else {
				GlStateManager.scale(1.2F, 1.2F, 1.2F);
			}
		}

		if (this.color != -1) {
			f6 = (float) (this.color >> 16 & 255) / 255.0F;
			blue = (float) (this.color >> 8 & 255) / 255.0F;
			float green = (float) (this.color & 255) / 255.0F;
			GlStateManager.color(f6, blue, green);
		}

		this.pre();
		f6 = 2.0F;
		if (this.slot == EntityEquipmentSlot.HEAD) {
			GlStateManager.pushMatrix();
			if (super.isChild) {
				GlStateManager.scale(1.5F / f6, 1.5F / f6, 1.5F / f6);
				GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
			}

			GlStateManager.translate(super.bipedHead.rotationPointX * scale, super.bipedHead.rotationPointY * scale, super.bipedHead.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedHead.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedHead.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedHead.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformHead();
			this.partHead();
			GlStateManager.popMatrix();
		}

		if (super.isChild) {
			GlStateManager.pushMatrix();
			GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
			GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
		}

		if (this.slot == EntityEquipmentSlot.CHEST) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(super.bipedBody.rotationPointX * scale, super.bipedBody.rotationPointY * scale, super.bipedBody.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedBody.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedBody.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedBody.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformBody();
			this.partBody();
			GlStateManager.popMatrix();
		}

		if (this.slot == EntityEquipmentSlot.CHEST) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(super.bipedRightArm.rotationPointX * scale, super.bipedRightArm.rotationPointY * scale, super.bipedRightArm.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedRightArm.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedRightArm.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedRightArm.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformRightArm();
			this.partRightArm();
			GlStateManager.popMatrix();
		}

		if (this.slot == EntityEquipmentSlot.CHEST) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(super.bipedLeftArm.rotationPointX * scale, super.bipedLeftArm.rotationPointY * scale, super.bipedLeftArm.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedLeftArm.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedLeftArm.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedLeftArm.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformLeftArm();
			this.partLeftArm();
			GlStateManager.popMatrix();
		}

		if (this.slot == EntityEquipmentSlot.LEGS) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(super.bipedRightLeg.rotationPointX * scale, super.bipedRightLeg.rotationPointY * scale, super.bipedRightLeg.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedRightLeg.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedRightLeg.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedRightLeg.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformRightLeg();
			this.partRightLeg();
			GlStateManager.popMatrix();
		}

		if (this.slot == EntityEquipmentSlot.LEGS) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(super.bipedLeftLeg.rotationPointX * scale, super.bipedLeftLeg.rotationPointY * scale, super.bipedLeftLeg.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedLeftLeg.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedLeftLeg.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedLeftLeg.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformLeftLeg();
			this.partLeftLeg();
			GlStateManager.popMatrix();
		}

		if (this.slot == EntityEquipmentSlot.FEET) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(super.bipedRightLeg.rotationPointX * scale, super.bipedRightLeg.rotationPointY * scale, super.bipedRightLeg.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedRightLeg.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedRightLeg.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedRightLeg.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformRightBoot();
			this.partRightBoot();
			GlStateManager.popMatrix();
		}

		if (this.slot == EntityEquipmentSlot.FEET) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(super.bipedLeftLeg.rotationPointX * scale, super.bipedLeftLeg.rotationPointY * scale, super.bipedLeftLeg.rotationPointZ * scale);
			GlStateManager.rotate(super.bipedLeftLeg.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(super.bipedLeftLeg.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(super.bipedLeftLeg.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			this.transformLeftBoot();
			this.partLeftBoot();
			GlStateManager.popMatrix();
		}

		if (super.isChild) {
			GlStateManager.popMatrix();
		}

		this.post();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}
}
