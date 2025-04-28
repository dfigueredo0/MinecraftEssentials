package com.elysium.essentials.util;

import com.elysium.essentials.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

import java.util.HashMap;
import java.util.List;

public class RecipeBookHandler {
    public static final HashMap<Screen, ImageButton> recipeButtons = new HashMap<Screen, ImageButton>();
    private static boolean hidden = Config.hideRecipeBook;

    public static void toggleRecipeBook(Minecraft client, Screen screen) {
        if (!(screenHasRecipeButton(screen)))
            return;
        if (screen instanceof ChatScreen)
            return;

        List<GuiEventListener> widgets = (List<GuiEventListener>) screen.children();

        ImageButton imageButton = null;
        for (GuiEventListener widget : widgets) {
            if (widget instanceof ImageButton button) {
                try {
                    if (button.getWidth() == 20 && button.getHeight() == 18) {
                        imageButton = button;
                        recipeButtons.put(screen, button);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (imageButton == null && recipeButtons.containsKey(screen)) {
            imageButton = recipeButtons.get(screen);
        }

        if (imageButton != null) {
            imageButton.visible = !hidden;
        }
    }

    private static boolean screenHasRecipeButton(Screen screen) {
        return (screen instanceof InventoryScreen || screen instanceof CraftingScreen);
    }

    public static void updateVisibility(Minecraft client) {
        Screen screen = client.screen;
        if (screen == null) return;

        if (screenHasRecipeButton(screen)) {
            if (recipeButtons.containsKey(screen)) {
                ImageButton button = recipeButtons.get(screen);
                if (button != null) {
                    button.visible = !hidden;
                }
            }
        }
    }

    public static void unregisterButton(Screen screen) {
        if (screenHasRecipeButton(screen)) {
            recipeButtons.remove(screen);
        }
    }

    public static boolean getHidden() {
        return hidden;
    }

    public static void setHidden(boolean enabled) {
        hidden = enabled;
        Config.hideRecipeBook = enabled;
    }
}
