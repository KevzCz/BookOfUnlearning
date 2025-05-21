package net.pixeldreamstudios.bookofunlearning.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ClientNetwork {
    public static void register() {
        PayloadTypeRegistry.playS2C().register(OpenSkillTreeScreenPayload.ID, OpenSkillTreeScreenPayload.CODEC);
    }
}