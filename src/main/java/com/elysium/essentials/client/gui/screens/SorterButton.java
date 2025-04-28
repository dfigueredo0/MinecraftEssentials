package com.elysium.essentials.client.gui.screens;

import com.elysium.essentials.Essentials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SorterButton extends AbstractWidget {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Essentials.MODID,"textures/gui/sorter.png");

    public SorterButton(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.isHovered())
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.translatable("essentials.tooltip.button.sorter"), mouseX, mouseY);

        guiGraphics.blit(RenderType::guiTextured, TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height, 9, 9);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("essentials.button.usage.hover"));
    }
}
