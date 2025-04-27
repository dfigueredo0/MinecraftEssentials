package com.elysium.essentials.data.tags;

import com.elysium.essentials.Essentials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> MINEABLE_ORES = createTag("mineable_ores");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Essentials.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> REPAIRABLE = createTag("repairable");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Essentials.MODID, name));
        }
    }
}
