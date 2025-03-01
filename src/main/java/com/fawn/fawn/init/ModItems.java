package com.fawn.fawn.init;

import com.fawn.fawn.Fawn;
import com.fawn.fawn.item.AraeItem;
import com.fawn.fawn.item.ArietisItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface ModItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap();
    Item ARAE = createItem("arae", new AraeItem(new Item.Settings().maxDamage(0).rarity(Rarity.UNCOMMON)));
    Item ARIETIS = createItem("arietis", new ArietisItem(new Item.Settings().maxDamage(0).rarity(Rarity.UNCOMMON)));

    private static <T extends Item> T createItem(String name, T item) {
        ITEMS.put(item, Fawn.id(name));
        return item;
    }

    static void init() {
        ITEMS.keySet().forEach((item) -> {
            Registry.register(Registries.ITEM, ITEMS.get(item), item);
        });
    }
}
