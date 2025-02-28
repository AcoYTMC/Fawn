package com.fawn.fawn.mixin;

import com.fawn.fawn.Fawn;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin({ServerPlayerEntity.class})
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(
            method = {"onSpawn"},
            at = {@At("TAIL")}
    )
    private void spawn(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        updateTabList(player);
    }

    @Inject(
            method = {"tick"},
            at = {@At("HEAD")}
    )
    private void tick(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        updateTabList(player);
    }

    @Inject(
            method = {"getPlayerListName"},
            at = {@At("TAIL")},
            cancellable = true
    )
    private void replaceNameOnTabList(CallbackInfoReturnable<Text> cir) {
        if (this.getUuid().equals(UUID.fromString("017f5cdc-086b-4d98-a0c2-7dc43d5117bd")) && Fawn.isFawn) {
            cir.setReturnValue(Text.literal("Fawn"));
        }
    }

    @Unique
    private static void updateTabList(ServerPlayerEntity player) {
        ServerPlayNetworkHandler handler = player.networkHandler;
        if (handler != null) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                ServerPlayerEntity playerEntity = server.getPlayerManager().getPlayer(player.getUuid());
                if (playerEntity != null) {
                    server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_DISPLAY_NAME, playerEntity));
                }
            }
        }
    }
}
