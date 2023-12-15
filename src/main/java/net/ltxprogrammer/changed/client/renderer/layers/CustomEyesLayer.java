package net.ltxprogrammer.changed.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.client.renderer.model.LatexHumanoidModel;
import net.ltxprogrammer.changed.entity.BasicPlayerInfo;
import net.ltxprogrammer.changed.entity.EyeStyle;
import net.ltxprogrammer.changed.entity.LatexEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;
import java.util.function.Predicate;

public class CustomEyesLayer<M extends LatexHumanoidModel<T>, T extends LatexEntity> extends RenderLayer<T, M> {
    public static final ModelLayerLocation HEAD = new ModelLayerLocation(Changed.modResource("head"), "main");
    private final ModelPart head;
    private final Predicate<BasicPlayerInfo> shouldRenderSclera;
    private final Predicate<BasicPlayerInfo> shouldRenderIris;

    public static boolean never(BasicPlayerInfo info) {
        return false;
    }

    public static boolean always(BasicPlayerInfo info) {
        return true;
    }

    public static boolean ifDarkLatexOverride(BasicPlayerInfo info) {
        return info.isOverrideIrisOnDarkLatex();
    }

    public CustomEyesLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet,
                           Predicate<BasicPlayerInfo> shouldRenderSclera, Predicate<BasicPlayerInfo> shouldRenderIris) {
        super(parent);
        this.head = modelSet.bakeLayer(HEAD);
        this.shouldRenderSclera = shouldRenderSclera;
        this.shouldRenderIris = shouldRenderIris;
    }

    public static LayerDefinition createHead() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, CubeDeformation.NONE), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource bufferSource, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        var info = entity.getBasicPlayerInfo();
        var style = info.getEyeStyle();

        var irisColor = info.getIrisColor();
        var scleraColor = info.getScleraColor();

        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);

        head.copyFrom(this.getParentModel().getHead());
        if (shouldRenderSclera.test(info))
            head.render(pose, bufferSource.getBuffer(RenderType.entityCutout(style.getSclera())), packedLight, overlay, scleraColor.red(), scleraColor.green(), scleraColor.blue(), 1.0F);
        if (shouldRenderIris.test(info))
            head.render(pose, bufferSource.getBuffer(RenderType.entityCutout(style.getIris())), packedLight, overlay, irisColor.red(), irisColor.green(), irisColor.blue(), 1.0F);
    }
}