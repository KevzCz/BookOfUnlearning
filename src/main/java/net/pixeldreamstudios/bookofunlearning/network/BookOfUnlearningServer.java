package net.pixeldreamstudios.bookofunlearning.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pixeldreamstudios.bookofunlearning.config.BookOfUnlearningConfig;
import net.puffish.skillsmod.SkillsMod;

public class BookOfUnlearningServer {
    public static void openSkillTreeSelection(ServerPlayerEntity player) {
        var categoriesWithSpentPoints = SkillsMod.getInstance()
                .getUnlockedCategories(player)
                .stream()
                .filter(id -> SkillsMod.getInstance().getSpentPoints(player, id).orElse(0) > 0)
                .filter(id -> !BookOfUnlearningConfig.isBlacklisted(id))
                .toList();

        if (categoriesWithSpentPoints.isEmpty()) {
            player.sendMessage(
                    Text.literal("§cNo Skill Trees available"),
                    true
            );
            return;
        }

        ServerPlayNetworking.send(player, new OpenSkillTreeScreenPayload(categoriesWithSpentPoints));
    }
}