package com.fawn.fawn.mixin;

import com.fawn.fawn.Fawn;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin({PlayerEntity.class})
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(
            method = {"getDisplayName"},
            at = {@At("RETURN")}
    )
    private Text setAcoToFawn(Text original) {
        if (this.getUuid().equals(UUID.fromString("017f5cdc-086b-4d98-a0c2-7dc43d5117bd")) && Fawn.isFawn) {
            return Text.literal("Fawn");
        } else {
            return original;
        }
    }
}
