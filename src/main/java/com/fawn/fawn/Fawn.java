package com.fawn.fawn;

import com.fawn.fawn.command.FawnCommand;
import com.fawn.fawn.init.ModItemGroups;
import com.fawn.fawn.init.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;

public class Fawn implements ModInitializer {
	public static final String MODID = "fawn";
	public static boolean isFawn;

	public void onInitialize() {
		// Fawn Toggle Command
		CommandRegistrationCallback.EVENT.register((dispatcher, acc, dedicated) -> {
			FawnCommand.register(dispatcher);
		});

		// Init
		ModItems.init();
		ModItemGroups.init();
	}

	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}