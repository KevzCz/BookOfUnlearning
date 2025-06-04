package net.pixeldreamstudios.bookofunlearning.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pixeldreamstudios.bookofunlearning.client.screen.SkillTreeScreen;
import net.pixeldreamstudios.bookofunlearning.network.ClientNetwork;
import net.pixeldreamstudios.bookofunlearning.network.OpenSkillTreeScreenPayload;
import net.pixeldreamstudios.bookofunlearning.util.ZoomHandler;

public class BookOfUnlearningClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientNetwork.register();
    }
}