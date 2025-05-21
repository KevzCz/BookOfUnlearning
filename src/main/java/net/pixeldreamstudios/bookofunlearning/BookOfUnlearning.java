package net.pixeldreamstudios.bookofunlearning;

import net.fabricmc.api.ModInitializer;

import net.pixeldreamstudios.bookofunlearning.config.BookOfUnlearningConfig;
import net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningCreativeTab;
import net.pixeldreamstudios.bookofunlearning.item.BookOfUnlearningItems;
import net.pixeldreamstudios.bookofunlearning.network.ServerNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookOfUnlearning implements ModInitializer {
	public static final String MOD_ID = "book-of-unlearning";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		BookOfUnlearningConfig.load();
		BookOfUnlearningItems.registerItems();
		BookOfUnlearningCreativeTab.registerGroup();
		ServerNetwork.register();
	}
}