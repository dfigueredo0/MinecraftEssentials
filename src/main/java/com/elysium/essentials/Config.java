package com.elysium.essentials;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.elysium.essentials.Essentials;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue HIDE_RECIPE_BOOK = BUILDER
            .comment("If true, the recipe book button is hidden when opening screens by default.")
            .define("hideRecipeBook", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean hideRecipeBook;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        hideRecipeBook = HIDE_RECIPE_BOOK.get();
    }
}
