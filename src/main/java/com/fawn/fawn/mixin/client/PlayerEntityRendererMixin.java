package com.fawn.fawn.mixin.client;

import com.fawn.fawn.item.ArietisItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("unchecked")
@Mixin({PlayerEntityRenderer.class})
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, EntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(
            method = "getArmPose",
            at = {@At("HEAD")},
            cancellable = true
    )
    private static void twoHandedItemRenderer(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        if (player.getStackInHand(hand).getItem() instanceof ArietisItem arietisItem) {
            if (!arietisItem.isBlocking) {
                cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_CHARGE);
            } else if (arietisItem.isBlocking) {
                cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
            }
        }
    }
}
