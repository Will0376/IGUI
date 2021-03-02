package ru.will0376.igui.obj.armor.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import ru.will0376.igui.obj.armor.loader.AdvancedModelLoaderRegistry;
import ru.will0376.igui.obj.armor.loader.IModelCustom;

public class ModelExampleArmor extends ArmorBinding {
	public static final ResourceLocation texture = new ResourceLocation("<modid>", "models/armor/examplearmor/tex.png");
	public static final IModelCustom body = AdvancedModelLoaderRegistry.ARMOR.getModel(new ResourceLocation("<modid>", "models/armor/examplearmor/body.obj"));

	//Common parameters
	public void transform() {
		GlStateManager.rotate(-180.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(0.1F, 0.1F, 0.1F);
		this.bind(texture);
	}

	public void transformBody() {
		GlStateManager.scale(0.27F, 0.27F, 0.27F);
		GlStateManager.translate(0.0F, -2.0F, 0.1F);
		this.transform();
	}

	public void partBody() {
		body.renderAll();
	}
}
