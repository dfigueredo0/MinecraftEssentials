package com.elysium.essentials.data.datagen;

import com.elysium.essentials.data.tags.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.REPAIRABLE)
                .add(TagEntry.tag(ItemTags.SWORDS.location()))
                .add(TagEntry.tag(ItemTags.PICKAXES.location()))
                .add(TagEntry.tag(ItemTags.AXES.location()))
                .add(TagEntry.tag(ItemTags.SHOVELS.location()))
                .add(TagEntry.tag(ItemTags.HOES.location()))
                .add(TagEntry.tag(ItemTags.FOOT_ARMOR.location()))
                .add(TagEntry.tag(ItemTags.LEG_ARMOR.location()))
                .add(TagEntry.tag(ItemTags.CHEST_ARMOR.location()))
                .add(TagEntry.tag(ItemTags.HEAD_ARMOR.location()));
    }
}
