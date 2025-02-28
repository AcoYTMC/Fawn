package com.fawn.fawn;

import com.fawn.fawn.client.render.ArietisItemRenderer;
import com.fawn.fawn.init.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class FawnClient implements ClientModInitializer {
    public void onInitializeClient() {
        registerArietis(ModItems.ARIETIS);
    }

    public static void registerArietis(Item item) {
        Identifier itemId = Registries.ITEM.getId(item);
        ArietisItemRenderer arietisItemRenderer = new ArietisItemRenderer(itemId);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(arietisItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(item, arietisItemRenderer);

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier(itemId, "inventory"));
            out.accept(new ModelIdentifier(new Identifier(itemId + "_blocking"), "inventory"));
        });
    }
}
