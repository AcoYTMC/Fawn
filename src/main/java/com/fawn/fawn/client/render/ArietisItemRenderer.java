package com.fawn.fawn.client.render;

import com.fawn.fawn.item.ArietisItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ArietisItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, IdentifiableResourceReloadListener {
    private final Identifier rendererId;
    private final Identifier itemId;
    private ItemRenderer itemRenderer;
    private BakedModel defaultModel;
    private BakedModel blockingModel;

    public ArietisItemRenderer(Identifier itemId) {
        this.rendererId = new Identifier(itemId.getNamespace(), itemId.getPath() + "_renderer");
        this.itemId = itemId;
    }

    public Identifier getFabricId() {
        return this.rendererId;
    }

    public CompletableFuture<Void> reload(ResourceReloader.Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return synchronizer.whenPrepared(Unit.INSTANCE).thenRunAsync(() -> {
            applyProfiler.startTick();
            applyProfiler.push("listener");
            MinecraftClient client = MinecraftClient.getInstance();
            this.itemRenderer = client.getItemRenderer();
            this.defaultModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.itemId, "inventory"));
            this.blockingModel = client.getBakedModelManager().getModel(new ModelIdentifier(new Identifier(this.itemId + "_blocking"), "inventory"));
            applyProfiler.pop();
            applyProfiler.endTick();
        }, applyExecutor);
    }

    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();

        boolean leftHanded = switch (mode) {
            case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> true;
            default -> false;
        };

        if (stack.getItem() instanceof ArietisItem arietisItem && arietisItem.isBlocking) {
            this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.blockingModel);
        } else {
            this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.defaultModel);
        }
    }
}
