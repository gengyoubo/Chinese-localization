package net.ltxprogrammer.changed.client.renderer;

import net.ltxprogrammer.changed.client.renderer.model.LightLatexWolfMaleModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexWolfModel;
import net.ltxprogrammer.changed.entity.beast.LightLatexWolfMale;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LightLatexWolfMaleRenderer extends LatexHumanoidRenderer<LightLatexWolfMale, LightLatexWolfMaleModel, ArmorLatexWolfModel<LightLatexWolfMale>> {
    public LightLatexWolfMaleRenderer(EntityRendererProvider.Context context) {
        super(context, new LightLatexWolfMaleModel(context.bakeLayer(LightLatexWolfMaleModel.LAYER_LOCATION)),
                ArmorLatexWolfModel::new, ArmorLatexWolfModel.INNER_ARMOR, ArmorLatexWolfModel.OUTER_ARMOR, 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LightLatexWolfMale p_114482_) {
        return new ResourceLocation("changed:textures/light_latex_wolf.png");
    }
}