package ru.will0376.igui.obj.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.will0376.igui.obj.armor.model.ArmorBinding;

public class ItemCustomArmor extends ItemArmor {
	public final String name;
	@SideOnly(Side.CLIENT)
	private ArmorBinding armorBinding;

	public ItemCustomArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.name = name;
		this.setTranslationKey(name + "_" + equipmentSlotIn.getName());
		this.setRegistryName(name + "_" + equipmentSlotIn.getName());
	}

	@SideOnly(Side.CLIENT)
	public static ModelBiped fillingArmorModel(ModelBiped model, EntityLivingBase entityLiving) {
		if (model == null) {
			return null;
		} else {
			model.bipedHead.showModel = model.bipedHeadwear.showModel = model.bipedBody.showModel = model.bipedRightArm.showModel = model.bipedLeftArm.showModel = model.bipedRightLeg.showModel = model.bipedLeftLeg.showModel = false;
			model.isSneak = entityLiving.isSneaking();
			model.isRiding = entityLiving.isRiding();
			model.isChild = entityLiving.isChild();
			ItemStack held_item = entityLiving.getHeldItemMainhand();
			if (!held_item.isEmpty()) {
				model.rightArmPose = ModelBiped.ArmPose.ITEM;
				if (entityLiving instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entityLiving;
					if (player.getItemInUseCount() > 0) {
						EnumAction enumaction = held_item.getItemUseAction();
						if (enumaction == EnumAction.BOW) {
							model.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
						} else if (enumaction == EnumAction.BLOCK) {
							model.rightArmPose = ModelBiped.ArmPose.BLOCK;
						}
					}
				}
			} else {
				model.rightArmPose = ModelBiped.ArmPose.EMPTY;
			}
			return model;
		}
	}

	@SideOnly(Side.CLIENT)
	public void setArmorBinding(ArmorBinding base) {
		this.armorBinding = base.setSlot(getEquipmentSlot());
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		return fillingArmorModel(this.getModelRaw(itemStack), entityLiving);
	}

	public ArmorBinding getModelRaw(ItemStack stack) {
		if (this.hasColor(stack)) {
			this.armorBinding.color = this.getColor(stack);
		}

		return this.armorBinding;
	}
}
