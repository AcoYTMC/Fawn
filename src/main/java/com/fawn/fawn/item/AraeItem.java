package com.fawn.fawn.item;

import com.fawn.fawn.util.ColoredText;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class AraeItem extends SwordItem {
    public AraeItem(Settings settings) {
        super(ToolMaterials.NETHERITE, 4, -2.6F, settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (stack.isOf(stack.getItem())) {
            user.getItemCooldownManager().set(stack.getItem(), 20);

            user.setVelocity(user.getVelocity().getX(), user.getVelocity().getY() + 2, user.getVelocity().getZ());
            user.fallDistance = 0.0F;

            return TypedActionResult.consume(stack);
        } else if (stack.isOf(stack.getItem()) && user.isSneaking() && !user.isOnGround()) {
            user.getItemCooldownManager().set(stack.getItem(), 20);

            user.setVelocity(user.getRotationVector().getX() * (double) 1.7F, user.getRotationVector().getY() * (double) 1.3F, user.getRotationVector().getZ() * (double) 1.7F);
            user.fallDistance = 0.0F;

            return TypedActionResult.consume(stack);
        } else {
            return TypedActionResult.pass(stack);
        }
    }

    public Text getName(ItemStack stack) {
        return ColoredText.withColor(Text.translatable(getTranslationKey()), 0xFDEE84);
    }

    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
