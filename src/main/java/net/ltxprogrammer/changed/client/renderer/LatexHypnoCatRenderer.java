package net.ltxprogrammer.changed.client.renderer;

import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.client.renderer.layers.EmissiveBodyLayer;
import net.ltxprogrammer.changed.client.renderer.model.LatexHypnoCatModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLatexWolfModel;
import net.ltxprogrammer.changed.entity.beast.LatexHypnoCat;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LatexHypnoCatRenderer extends LatexHumanoidRenderer<LatexHypnoCat, LatexHypnoCatModel, ArmorLatexWolfModel<LatexHypnoCat>> {
    public LatexHypnoCatRenderer(EntityRendererProvider.Context context) {
        super(context, new LatexHypnoCatModel(context.bakeLayer(LatexHypnoCatModel.LAYER_LOCATION)),
                ArmorLatexWolfModel::new, ArmorLatexWolfModel.INNER_ARMOR, ArmorLatexWolfModel.OUTER_ARMOR, 0.5f);
        this.addLayer(new EmissiveBodyLayer<>(this, Changed.modResource("textures/latex_hypno_cat_emissive.png")));
    }

    @Override
    public ResourceLocation getTextureLocation(LatexHypnoCat p_114482_) {
        return new ResourceLocation("changed:textures/latex_hypno_cat.png");
    }
}