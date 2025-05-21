package net.pixeldreamstudios.bookofunlearning.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pixeldreamstudios.bookofunlearning.BookOfUnlearning;
import net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningItems;

public class BookOfUnlearningCreativeTab {
    public static final RegistryKey<ItemGroup> BOOK_OF_UNLEARNING_GROUP = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(),
            Identifier.of(BookOfUnlearning.MOD_ID, "book_of_unlearning_group")
    );

    public static void registerGroup() {
        Registry.register(Registries.ITEM_GROUP, BOOK_OF_UNLEARNING_GROUP,
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup." + BookOfUnlearning.MOD_ID + ".book_of_unlearning_group"))
                        .icon(() -> new ItemStack(BookOfUnlearningItems.BOOK_OF_UNLEARNING))
                        .entries((context, entries) -> {
                            entries.add(BookOfUnlearningItems.BOOK_OF_UNLEARNING);
                        })
                        .build()
        );
    }
}