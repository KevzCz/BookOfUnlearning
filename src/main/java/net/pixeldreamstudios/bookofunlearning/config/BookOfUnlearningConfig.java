package net.pixeldreamstudios.bookofunlearning.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.Identifier;
import net.pixeldreamstudios.bookofunlearning.BookOfUnlearning;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BookOfUnlearningConfig {
    private static final File CONFIG_FILE = new File("config/book_of_unlearning.json");

    // ✅ Enable pretty printing
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Set<Identifier> blacklistedTrees = new HashSet<>();

    public static void load() {
        try {
            if (!CONFIG_FILE.exists()) {
                BookOfUnlearning.LOGGER.info("No Book of Unlearning config found, generating default.");

                // Ensure config directory exists
                CONFIG_FILE.getParentFile().mkdirs();

                // Create default config
                JsonObject defaultConfig = new JsonObject();
                defaultConfig.add("blacklisted_skill_trees", GSON.toJsonTree(Collections.emptyList()));

                try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                    GSON.toJson(defaultConfig, writer); // ✅ Write pretty-printed JSON
                }

                return;
            }

            // Read existing config
            JsonObject obj = GSON.fromJson(new FileReader(CONFIG_FILE), JsonObject.class);
            Type listType = new TypeToken<Set<String>>() {}.getType();

            Set<String> rawList = GSON.fromJson(obj.get("blacklisted_skill_trees"), listType);
            for (String str : rawList) {
                try {
                    Identifier id = Identifier.tryParse(str);
                    if (id != null) {
                        blacklistedTrees.add(id);
                    } else {
                        BookOfUnlearning.LOGGER.warn("Invalid identifier in config: {}", str);
                    }
                } catch (Exception e) {
                    BookOfUnlearning.LOGGER.warn("Error parsing identifier in config: {}", str, e);
                }
            }

            BookOfUnlearning.LOGGER.info("Loaded {} blacklisted skill trees.", blacklistedTrees.size());

        } catch (Exception e) {
            BookOfUnlearning.LOGGER.error("Failed to load Book of Unlearning config", e);
        }
    }

    public static boolean isBlacklisted(Identifier id) {
        return blacklistedTrees.contains(id);
    }

    public static Set<Identifier> getBlacklistedTrees() {
        return Collections.unmodifiableSet(blacklistedTrees);
    }
}
