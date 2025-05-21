package net.pixeldreamstudios.bookofunlearning.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static net.pixeldreamstudios.bookofunlearning.BookOfUnlearning.MOD_ID;

public record ResetSkillTreePayload(Identifier categoryId) implements CustomPayload {

    public static final Id<ResetSkillTreePayload> ID =
            new Id<>(Identifier.of(MOD_ID, "reset_skill_tree"));

    public static final PacketCodec<PacketByteBuf, ResetSkillTreePayload> CODEC =
            PacketCodec.of(ResetSkillTreePayload::write, ResetSkillTreePayload::read);

    public static ResetSkillTreePayload read(PacketByteBuf buf) {
        return new ResetSkillTreePayload(buf.readIdentifier());
    }

    public void write(PacketByteBuf buf) {
        buf.writeIdentifier(categoryId);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
