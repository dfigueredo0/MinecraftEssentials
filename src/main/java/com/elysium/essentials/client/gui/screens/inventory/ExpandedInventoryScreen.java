package com.elysium.essentials.client.gui.screens.inventory;

import com.elysium.essentials.Essentials;
import com.elysium.essentials.client.gui.screens.SorterButton;
import com.elysium.essentials.world.inventory.ExpandedInventoryMenu;
import com.mojang.blaze3d.platform.Lighting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class ExpandedInventoryScreen extends AbstractContainerScreen<ExpandedInventoryMenu> {
    private static final ResourceLocation BG_TEXTURE = ResourceLocation.fromNamespaceAndPath(Essentials.MODID, "textures/gui/container/expanded_inventory.png");
    private static final int SHIFT_X = 24;
    private float xMouse;
    private float yMouse;
    private boolean buttonClicked;

    public ExpandedInventoryScreen(ExpandedInventoryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.imageWidth = 176 + SHIFT_X;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos += SHIFT_X;

        this.addRenderableWidget(new SorterButton(getSortingButtonsPosition().x(), getSortingButtonsPosition().y(), 9, 9, Component.literal("Sort")));
    }

    protected ScreenPosition getSortingButtonsPosition() {
        return new ScreenPosition(this.leftPos + 156 + SHIFT_X, this.height / 2 - 16);
    }

    protected void onSortingButtonClicked() {
        this.buttonClicked = true;
    }

    public static void renderEntityInInventoryFollowsMouse(
            GuiGraphics guiGraphics,
            int x1,
            int y1,
            int x2,
            int y2,
            int scale,
            float yOffset,
            float mouseX,
            float mouseY,
            LivingEntity entity
    ) {
        float f = (float)(x1 + x2) / 2.0F;
        float f1 = (float)(y1 + y2) / 2.0F;
        float f2 = (float)Math.atan((f - mouseX) / 40.0F);
        float f3 = (float)Math.atan((f1 - mouseY) / 40.0F);

        renderEntityInInventoryFollowsAngle(guiGraphics, x1, y1, x2, y2, scale, yOffset, f2, f3, entity);
    }

    public static void renderEntityInInventoryFollowsAngle(
            GuiGraphics guiGraphics,
            int minX,
            int minY,
            int maxX,
            int maxY,
            int i,
            float j,
            float angleXComponent,
            float angleYComponent,
            LivingEntity livingEntity
    ) {
        float f = (float)(minX + maxX) / 2.0F;
        float f1 = (float)(minY + maxY) / 2.0F;
        guiGraphics.enableScissor(minX, minY, maxX, maxY);
        float f2 = angleXComponent;
        float f3 = angleYComponent;
        Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
        Quaternionf quaternionf1 = new Quaternionf().rotateX(f3 * 20.0F * (float) (Math.PI / 180.0));
        quaternionf.mul(quaternionf1);
        float f4 = livingEntity.yBodyRot;
        float f5 = livingEntity.getYRot();
        float f6 = livingEntity.getXRot();
        float f7 = livingEntity.yHeadRotO;
        float f8 = livingEntity.yHeadRot;
        livingEntity.yBodyRot = 180.0F + f2 * 20.0F;
        livingEntity.setYRot(180.0F + f2 * 40.0F);
        livingEntity.setXRot(-f3 * 20.0F);
        livingEntity.yHeadRot = livingEntity.getYRot();
        livingEntity.yHeadRotO = livingEntity.getYRot();
        float f9 = livingEntity.getScale();
        Vector3f vector3f = new Vector3f(0.0F, livingEntity.getBbHeight() / 2.0F + j * f9, 0.0F);
        float f10 = (float)i / f9;
        renderEntityInInventory(guiGraphics, f, f1, f10, vector3f, quaternionf, quaternionf1, livingEntity);
        livingEntity.yBodyRot = f4;
        livingEntity.setYRot(f5);
        livingEntity.setXRot(f6);
        livingEntity.yHeadRotO = f7;
        livingEntity.yHeadRot = f8;
        guiGraphics.disableScissor();
    }

    public static void renderEntityInInventory(
            GuiGraphics guiGraphics,
            float x,
            float y,
            float scale,
            Vector3f translate,
            Quaternionf pose,
            @Nullable Quaternionf cameraOrientation,
            LivingEntity entity
    ) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((double)x, (double)y, 50.0);
        guiGraphics.pose().scale(scale, scale, -scale);
        guiGraphics.pose().translate(translate.x, translate.y, translate.z);
        guiGraphics.pose().mulPose(pose);
        guiGraphics.flush();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (cameraOrientation != null) {
            entityrenderdispatcher.overrideCameraOrientation(cameraOrientation.conjugate(new Quaternionf()).rotateY((float) Math.PI));
        }

        entityrenderdispatcher.setRenderShadow(false);
        guiGraphics.drawSpecial(multiBufferSource -> entityrenderdispatcher.render(entity, 0.0, 0.0, 0.0, 1.0F, guiGraphics.pose(), multiBufferSource, 15728880));
        guiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        guiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int xOffset, int yOffset) {
        guiGraphics.blit(RenderType::guiTextured, BG_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        if (this.minecraft != null)
            renderEntityInInventoryFollowsMouse(guiGraphics, this.leftPos + 26, this.topPos + 8, this.leftPos + 75, this.topPos + 78, 30, 0.0625F, this.xMouse, this.yMouse, this.minecraft.player);
    }
}
