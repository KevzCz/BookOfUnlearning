package net.pixeldreamstudios.bookofunlearning.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningItems;

public class ZoomHandler {
    public static float zoomProgress = 0f; // 0.0 = no zoom, 1.0 = full zoom
    private static final float ZOOM_SPEED = 0.01f; // How fast to zoom in/out per tick

    public static void tick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ItemStack stack = client.player.getActiveItem();

        boolean isZooming = client.player.isUsingItem() && stack.isOf(BookOfUnlearningItems.BOOK_OF_UNLEARNING);

        if (isZooming) {
            zoomProgress = Math.min(0.6f, zoomProgress + ZOOM_SPEED);
        } else {
            zoomProgress = Math.max(0f, zoomProgress - ZOOM_SPEED);
        }
    }

    public static double applyZoom(double originalFov) {
        float clamped = Math.min(1f, Math.max(0f, zoomProgress));
        return originalFov * (1.0 - 0.6 * clamped); // Up to 60% zoom
    }
}
