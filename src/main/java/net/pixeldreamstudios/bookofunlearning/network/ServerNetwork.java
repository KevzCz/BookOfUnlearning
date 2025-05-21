package net.pixeldreamstudios.bookofunlearning.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.pixeldreamstudios.bookofunlearning.BookOfUnlearning;
import net.puffish.skillsmod.SkillsMod;

public class ServerNetwork {
    public static void register() {
        PayloadTypeRegistry.playC2S().register(ResetSkillTreePayload.ID, ResetSkillTreePayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ResetSkillTreePayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            context.player().server.execute(() -> {
                var mainHand = player.getMainHandStack();
                var offHand = player.getOffHandStack();

                // Look for the book in either hand
                if (mainHand.isOf(net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningItems.BOOK_OF_UNLEARNING)) {
                    mainHand.decrement(1);
                    SkillsMod.getInstance().resetSkills(player, payload.categoryId());
                } else if (offHand.isOf(net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningItems.BOOK_OF_UNLEARNING)) {
                    offHand.decrement(1);
                    SkillsMod.getInstance().resetSkills(player, payload.categoryId());
                } else {
                    // Optional: tell the player they didn’t hold the right item
                    player.sendMessage(Text.literal("§cYou must hold the Book of Unlearning."), false);
                }
            });
        });

    }
}
