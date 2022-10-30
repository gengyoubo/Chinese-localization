package net.ltxprogrammer.changed.entity.beast;

import net.ltxprogrammer.changed.entity.GenderedLatexEntity;
import net.ltxprogrammer.changed.entity.LatexType;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.level.Level;

public abstract class AbstractLatexHypnoCat extends GenderedLatexEntity {
    public AbstractLatexHypnoCat(EntityType<? extends AbstractLatexHypnoCat> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(5, new FloatGoal(this));
    }

    public boolean causeFallDamage(float p_148859_, float p_148860_, DamageSource p_148861_) { return false; }

    @Override
    public int getTicksRequiredToFreeze() { return 200; }

    @Override
    public LatexType getLatexType() {
        return LatexType.NEUTRAL;
    }

    public ChangedParticles.Color3 getDripColor() {
        return ChangedParticles.Color3.DARK;
    }
}
