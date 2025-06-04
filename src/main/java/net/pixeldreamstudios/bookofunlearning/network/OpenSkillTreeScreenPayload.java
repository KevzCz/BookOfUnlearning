package net.pixeldreamstudios.bookofunlearning.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.pixeldreamstudios.bookofunlearning.BookOfUnlearning.MOD_ID;

public record OpenSkillTreeScreenPayload(List<Identifier> skillTrees) implements CustomPayload {
    public static final Id<OpenSkillTreeScreenPayload> ID =
            new Id<>(Identifier.of(MOD_ID, "open_skill_tree_gui"));

    public static final PacketCodec<PacketByteBuf, OpenSkillTreeScreenPayload> CODEC =
            PacketCodec.of(OpenSkillTreeScreenPayload::write, OpenSkillTreeScreenPayload::read);

    @Override
    public Id<OpenSkillTreeScreenPayload> getId() {
        return ID;
    }

    public static OpenSkillTreeScreenPayload read(PacketByteBuf buf) {
        List<Identifier> skillTrees = buf.readList(PacketByteBuf::readIdentifier);
        return new OpenSkillTreeScreenPayload(skillTrees);
    }

    public void write(PacketByteBuf buf) {
        buf.writeCollection(skillTrees, PacketByteBuf::writeIdentifier);
    }
}
