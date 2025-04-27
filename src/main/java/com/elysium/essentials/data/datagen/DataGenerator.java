package com.elysium.essentials.data.datagen;

import com.elysium.essentials.Essentials;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Essentials.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBlockTagProvider blockTagProvider = new ModBlockTagProvider(packOutput, lookupProvider);
        generator.addProvider(true, blockTagProvider);
        generator.addProvider(true, new ModItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter()));
        generator.addProvider(true, new ModLanguageProvider(packOutput, "en_us"));
    }
}
