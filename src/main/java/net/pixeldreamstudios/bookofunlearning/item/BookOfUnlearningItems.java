package net.pixeldreamstudios.bookofunlearning.item;


import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.pixeldreamstudios.bookofunlearning.BookOfUnlearning;

public class BookOfUnlearningItems {
    public static final Item BOOK_OF_UNLEARNING = new BookOfUnlearningItem(new Item.Settings().maxCount(1));

    public static void registerItems() {
        Registry.register(Registries.ITEM, Identifier.of(BookOfUnlearning.MOD_ID, "book_of_unlearning"), BOOK_OF_UNLEARNING);}
}