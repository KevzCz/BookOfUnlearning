package net.pixeldreamstudios.bookofunlearning.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.pixeldreamstudios.bookofunlearning.network.BookOfUnlearningServer;

import java.util.List;

public class BookOfUnlearningItem extends Item {
    private static final int CHARGE_TIME = 60;

    public BookOfUnlearningItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.setCurrentHand(hand);
        return TypedActionResult.consume(player.getStackInHand(hand));
    }
    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.book_of_unlearning.reset_info")
                .copy().setStyle(Style.EMPTY.withColor(0xAAAAFF).withItalic(true)));
    }


    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return CHARGE_TIME;
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient && user instanceof PlayerEntity player) {
            int usedTicks = getMaxUseTime(stack, user) - remainingUseTicks;

            if (usedTicks >= 10) {
                spawnMagicParticles(player);
            }

            if (usedTicks >= CHARGE_TIME - 5) {
                spawnVortexParticles(player);
            }

            if (player.age % 10 == 0 && player.getRandom().nextFloat() < 0.5f) {
                world.playSound(
                        player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                        SoundCategory.PLAYERS,
                        0.3f + player.getRandom().nextFloat() * 0.3f,
                        1.0f + player.getRandom().nextFloat() * 0.4f,
                        false
                );
            }
        }
    }


    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient && user instanceof ServerPlayerEntity player) {
            BookOfUnlearningServer.openSkillTreeSelection(player);
        }
        return stack;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (world.isClient && user instanceof PlayerEntity && remainingUseTicks > 0) {
            world.playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE,
                    SoundCategory.PLAYERS, 0.5f, 1.5f, false);
        }
    }

    private void spawnMagicParticles(PlayerEntity player) {
        player.getWorld().addParticle(
                ParticleTypes.ENCHANT,
                player.getX() + (player.getRandom().nextDouble() - 0.5),
                player.getY() + 1.0 + (player.getRandom().nextDouble()),
                player.getZ() + (player.getRandom().nextDouble() - 0.5),
                0, 0.01, 0
        );
    }

    private void spawnVortexParticles(PlayerEntity player) {
        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(player.age * 10 + i * 120);
            double radius = 0.5;
            double x = player.getX() + Math.cos(angle) * radius;
            double z = player.getZ() + Math.sin(angle) * radius;
            player.getWorld().addParticle(
                    ParticleTypes.PORTAL,
                    x,
                    player.getY() + 1,
                    z,
                    0, 0.05, 0
            );
        }
    }
}
