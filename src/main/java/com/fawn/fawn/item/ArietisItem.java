package com.fawn.fawn.item;

import com.fawn.fawn.util.ColoredText;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ArietisItem extends AxeItem {
    public boolean isBlocking;

    public ArietisItem(Settings settings) {
        super(ToolMaterials.NETHERITE, 6, -3.0F, settings);
    }

    public Text getName(ItemStack stack) {
        return ColoredText.withColor(Text.translatable(getTranslationKey()), 0xFDEE84);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (hand == Hand.OFF_HAND) {
            return TypedActionResult.fail(stack);
        } else {
            user.setCurrentHand(hand);
            isBlocking = true;
            return TypedActionResult.consume(stack);
        }
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            isBlocking = false;
        }

        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    public int getMaxUseTime(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (stack.getItem() instanceof ArietisItem arietisItem && entity instanceof PlayerEntity player && arietisItem.isBlocking) {
            player.sendMessage(Text.literal("You are blocking"));
        }
    }

    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
