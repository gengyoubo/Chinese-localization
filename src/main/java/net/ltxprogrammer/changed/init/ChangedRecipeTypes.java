package net.ltxprogrammer.changed.init;

import com.google.common.collect.ImmutableList;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.LatexType;
import net.ltxprogrammer.changed.entity.variant.LatexVariant;
import net.ltxprogrammer.changed.recipe.InfuserRecipes;
import net.ltxprogrammer.changed.util.DelayedItemStack;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.client.RecipeBookRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.commons.compress.utils.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChangedRecipeTypes {
    public static Map<ResourceLocation, RecipeType<?>> REGISTRY = new HashMap<>();
    private static <T extends Recipe<?>> RecipeType<T> register(ResourceLocation name) {
        var recipe = new RecipeType<T>() {
            public String toString() {
                return name.toString();
            }
        };

        REGISTRY.put(name, recipe);
        return recipe;
    }

    private static <T extends Recipe<?>> RecipeBookType registerBookType(String name) {
        return RecipeBookType.create(name);
    }

    public static RecipeType<InfuserRecipes.InfuserRecipe> INFUSER_RECIPE = register(Changed.modResource("infuser"));

    public static final RecipeBookType INFUSER_BOOK = registerBookType( "CHANGED_INFUSER");

    @SubscribeEvent
    public static void onRegister(FMLCommonSetupEvent event) {
        REGISTRY.forEach((name, type) -> {
            Registry.register(Registry.RECIPE_TYPE, name, type);
        });
    }
}
