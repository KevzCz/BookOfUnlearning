package net.pixeldreamstudios.bookofunlearning.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pixeldreamstudios.bookofunlearning.network.ResetSkillTreePayload;

import java.util.List;

public class SkillTreeScreen extends Screen {
    private final List<Identifier> skillTrees;
    private final List<ButtonWidget> buttons = new java.util.ArrayList<>();

    private static final Identifier BACKGROUND_TEXTURE =
            Identifier.of("book-of-unlearning", "textures/gui/skill_tree_bg.png");

    public SkillTreeScreen(List<Identifier> skillTrees) {
        super(Text.translatable("screen.book_of_unlearning.skill_trees")
                .copy().setStyle(Style.EMPTY.withColor(0xEFD8A4).withBold(true)));
        this.skillTrees = skillTrees;
    }

    @Override
    protected void init() {
        buttons.clear();

        int cardWidth = 100;
        int cardHeight = 24;
        int spacing = 10;
        int totalHeight = skillTrees.size() * (cardHeight + spacing);
        int startY = (height / 2) - (totalHeight / 2);

        for (int i = 0; i < skillTrees.size(); i++) {
            Identifier tree = skillTrees.get(i);
            String name = beautify(tree);

            Text label = Text.literal("➤ " + name + " ◀")
                    .copy().setStyle(Style.EMPTY.withColor(0xE8D8B8).withItalic(true));

            ButtonWidget button = ButtonWidget.builder(label, btn -> {
                        ClientPlayNetworking.send(new ResetSkillTreePayload(tree));
                        this.close();
                    })
                    .position(width / 2 - cardWidth / 2, startY + i * (cardHeight + spacing))
                    .size(cardWidth, cardHeight)
                    .tooltip(Tooltip.of(Text.translatable("tooltip.book_of_unlearning.skill_tree_button")))
                    .build();

            button.active = true;
            buttons.add(button);
            addDrawableChild(button);
        }
    }




    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);


        int panelWidth = 200;
        int panelHeight = 200;
        int panelX = width / 2 - panelWidth / 2;
        int panelY = height / 2 - panelHeight / 2;

        context.drawTexture(BACKGROUND_TEXTURE, panelX, panelY, 0, 0, panelWidth, panelHeight, panelWidth, panelHeight);
        context.fillGradient(panelX, panelY, panelX + panelWidth, panelY + panelHeight, 0xAA111111, 0xAA222222);
        context.drawBorder(panelX, panelY, panelWidth, panelHeight, 0xFFDDCCAA);


        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, panelY + 10, 0xFFEBD7);

        for (ButtonWidget button : buttons) {
            int x = button.getX();
            int y = button.getY();
            int w = button.getWidth();
            int h = button.getHeight();


            int fillColor = button.isHovered() ? 0x44FFFFFF : 0x33111111;
            int borderColor = button.isHovered() ? 0xFFD6CFAA : 0xFF444444;

            context.fill(x - 2, y - 2, x + w + 2, y + h + 2, fillColor);
            context.drawBorder(x - 2, y - 2, w + 4, h + 4, borderColor);
        }

    }




    private String beautify(Identifier id) {
        String path = id.getPath().replace('_', ' ');
        return path.substring(0, 1).toUpperCase() + path.substring(1);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
