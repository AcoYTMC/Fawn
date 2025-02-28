package com.fawn.fawn.command;

import com.fawn.fawn.Fawn;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.UUID;

@SuppressWarnings("unchecked")
public class FawnCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)LiteralArgumentBuilder.literal("fawn").executes((commandContext) -> {
            ServerCommandSource source = (ServerCommandSource)commandContext.getSource();
            PlayerEntity player = source.getPlayer();

            assert player != null;
            if (player.getUuid().equals(UUID.fromString("017f5cdc-086b-4d98-a0c2-7dc43d5117bd")) && Fawn.isFawn) {
                Fawn.isFawn = false;
                player.sendMessage(Text.literal("You are no longer Fawn").formatted(Formatting.GRAY));
            } else if (player.getUuid().equals(UUID.fromString("017f5cdc-086b-4d98-a0c2-7dc43d5117bd")) && !Fawn.isFawn) {
                Fawn.isFawn = true;
                player.sendMessage(Text.literal("You are now Fawn").formatted(Formatting.GOLD));
            } else {
                player.sendMessage(Text.literal("You do not have access to this command.").formatted(Formatting.RED));
            }

            return 1;
        }));
    }
}
