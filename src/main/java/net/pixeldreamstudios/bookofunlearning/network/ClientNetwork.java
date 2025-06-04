package net.pixeldreamstudios.bookofunlearning.network;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.pixeldreamstudios.bookofunlearning.client.screen.SkillTreeScreen;
import net.pixeldreamstudios.bookofunlearning.util.ZoomHandler;

public class ClientNetwork {
    public static void register() {
       ClientPlayNetworking.registerGlobalReceiver(
                OpenSkillTreeScreenPayload.ID,
                (payload, context) -> {
                    context.client().execute(() -> {
                        context.client().setScreen(new SkillTreeScreen(payload.skillTrees()));
                    });
                }
        );
        ClientTickEvents.END_CLIENT_TICK.register(client -> ZoomHandler.tick());

    }
}