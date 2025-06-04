package net.pixeldreamstudios.bookofunlearning.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.puffish.skillsmod.SkillsMod;

public class ServerNetwork {
    public static void register() {

        PayloadTypeRegistry.playS2C().register(OpenSkillTreeScreenPayload.ID, OpenSkillTreeScreenPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ResetSkillTreePayload.ID, ResetSkillTreePayload.CODEC);


        ServerPlayNetworking.registerGlobalReceiver(ResetSkillTreePayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            context.player().server.execute(() -> {
                var mainHand = player.getMainHandStack();
                var offHand = player.getOffHandStack();

                if (mainHand.isOf(net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningItems.BOOK_OF_UNLEARNING)) {
                    mainHand.decrement(1);
                    SkillsMod.getInstance().resetSkills(player, payload.categoryId());
                } else if (offHand.isOf(net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningItems.BOOK_OF_UNLEARNING)) {
                    offHand.decrement(1);
                    SkillsMod.getInstance().resetSkills(player, payload.categoryId());
                } else {
                    player.sendMessage(Text.literal("§cYou must hold the Book of Unlearning."), false);
                }
            });
        });
    }

}
