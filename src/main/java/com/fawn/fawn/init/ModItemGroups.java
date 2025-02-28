package com.fawn.fawn.init;

import com.fawn.fawn.Fawn;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> FAWN_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Fawn.id("fawn_group"));

    public static void init() {
        ItemGroup FAWN_GROUP = FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.ARIETIS))
                .displayName(Text.translatable("itemGroup.fawn.fawn_group"))
                .entries(((displayContext, entries) -> {
                    entries.add(ModItems.ARAE);
                    entries.add(ModItems.ARIETIS);
                }))
                .build();
    }
}
