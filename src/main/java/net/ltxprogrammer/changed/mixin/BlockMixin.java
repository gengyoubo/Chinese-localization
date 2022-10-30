package net.ltxprogrammer.changed.mixin;

import net.ltxprogrammer.changed.block.AbstractLatexBlock;
import net.ltxprogrammer.changed.block.AbstractLatexCrystal;
import net.ltxprogrammer.changed.entity.LatexType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

import static net.ltxprogrammer.changed.block.AbstractLatexBlock.COVERED;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviour implements ItemLike, net.minecraftforge.common.extensions.IForgeBlock {
    // TODO assimilate blocks into latex blocks more (render, behavior)
    private BlockMixin(Properties p_60452_) {
        super(p_60452_);
    }

    private boolean isLatexed(BlockState blockState) {
        return getLatexed(blockState) != LatexType.NEUTRAL;
    }

    private LatexType getLatexed(BlockState blockState) {
        return blockState.getProperties().contains(COVERED) ? blockState.getValue(COVERED) : LatexType.NEUTRAL;
    }

    @Inject(method = "getSoundType", at = @At("HEAD"), cancellable = true)
    public void getSoundType(BlockState p_49963_, CallbackInfoReturnable<SoundType> callbackInfoReturnable) {
        if (isLatexed(p_49963_))
            callbackInfoReturnable.setReturnValue(SoundType.SLIME_BLOCK);
    }

    @Inject(method = "isRandomlyTicking", at = @At("HEAD"), cancellable = true)
    public void isRandomlyTicking(BlockState p_49921_, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (isLatexed(p_49921_))
            callbackInfoReturnable.setReturnValue(true);
    }

    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    public void stepOn(Level p_152431_, BlockPos p_152432_, BlockState p_152433_, Entity p_152434_, CallbackInfo callbackInfo) {
        if (isLatexed(p_152433_)) {
            callbackInfo.cancel();
            AbstractLatexBlock.stepOn(p_152431_, p_152432_, p_152433_, p_152434_, getLatexed(p_152433_));
        }
    }

    /*@Inject(method = "canSustainPlant", at = @At("HEAD"), cancellable = true)
    public void canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (isLatexed(state)) {
            if (getLatexed(state) != LatexType.DARK_LATEX) {
                callbackInfoReturnable.setReturnValue(false);
                return;
            }

            BlockState plant = plantable.getPlant(world, pos.relative(facing));
            if (plant.getBlock() instanceof AbstractLatexCrystal)
                callbackInfoReturnable.setReturnValue(true);
            else
                callbackInfoReturnable.setReturnValue(false);
        }
    }*/
}
