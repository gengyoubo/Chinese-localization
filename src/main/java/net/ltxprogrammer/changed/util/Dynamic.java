package net.ltxprogrammer.changed.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class Dynamic {
    static boolean replaceLocked(Registry<?> reg, boolean state) {
        if (reg instanceof NamespacedDefaultedWrapper regWrapper) {
            boolean locked = regWrapper.locked;
            regWrapper.locked = state;
            return locked;
        }
        if (reg instanceof NamespacedWrapper regWrapper) {
            boolean locked = regWrapper.locked;
            regWrapper.locked = state;
            return locked;
        }
        return false;
    }

    static boolean replaceFrozen(Registry<?> reg, boolean state) {
        if (reg instanceof NamespacedDefaultedWrapper regWrapper) {
            boolean frozen = regWrapper.getHolderHelper().isFrozen();
            if (state)
                regWrapper.freeze();
            else
                regWrapper.unfreeze();
            return frozen;
        }
        if (reg instanceof NamespacedWrapper regWrapper) {
            boolean frozen = regWrapper.getHolderHelper().isFrozen();
            if (state)
                regWrapper.freeze();
            else
                regWrapper.unfreeze();
            return frozen;
        }
        if (reg instanceof MappedRegistry regWrapper) {
            if (state)
                regWrapper.freeze();
            else
                regWrapper.unfreeze();
            return !state;
        }
        return false;
    }

    public static <T extends Entity> EntityType<T> lateRegisterEntityType(ResourceLocation name, EntityType.Builder<T> builder) {
        boolean locked = replaceLocked(Registry.ENTITY_TYPE, false);
        boolean frozen = replaceFrozen(Registry.ENTITY_TYPE, false);
        boolean forgeFrozen = RegistryManager.ACTIVE.getRegistry(ForgeRegistries.Keys.ENTITY_TYPES).isLocked();
        RegistryManager.ACTIVE.getRegistry(ForgeRegistries.Keys.ENTITY_TYPES).unfreeze();
        EntityType<T> type = (EntityType<T>) Registry.register(Registry.ENTITY_TYPE, name, builder.build(name.toString()).setRegistryName(name));
        RegistryManager.ACTIVE.getRegistry(ForgeRegistries.Keys.ENTITY_TYPES).freeze();
        replaceFrozen(Registry.ENTITY_TYPE, frozen);
        replaceLocked(Registry.ENTITY_TYPE, locked);
        return type;
    }

    public static <T extends LivingEntity> void lateRegisterEntityAttributes(EntityType<T> type, AttributeSupplier supplier) {
        ForgeHooks.FORGE_ATTRIBUTES.putIfAbsent(type, supplier);
    }

    public static <T> T lateRegister(Registry<T> registry, ResourceLocation name, Supplier<T> supplier) {
        boolean locked = replaceLocked(registry, false);
        boolean frozen = replaceFrozen(registry, false);
        T type = Registry.register(registry, name, supplier.get());
        replaceFrozen(registry, frozen);
        replaceLocked(registry, locked);
        return type;
    }
}
