package org.bix_studio.in_silence;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = "in_silence", value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class HorrorShaderManager {

    private static final ResourceLocation HORROR_SHADER =
            ResourceLocation.fromNamespaceAndPath("in_silence", "horror_vhs");

    private static float intensity = 1.0f;

    public static void enable() {
        PostProcessingManager manager = VeilRenderSystem.renderer().getPostProcessingManager();
        if (manager.add(1000, HORROR_SHADER)) {
            System.out.println("Horror VHS Shader aktiviert!");
        }
    }

    public static void disable() {
        PostProcessingManager manager = VeilRenderSystem.renderer().getPostProcessingManager();
        if (manager.remove(HORROR_SHADER)) {
            System.out.println("Horror VHS Shader deaktiviert!");
        }
    }

    public static void toggle() {
        PostProcessingManager manager = VeilRenderSystem.renderer().getPostProcessingManager();
        if (manager.isActive(HORROR_SHADER)) {
            disable();
        } else {
            enable();
        }
    }

    public static void setIntensity(float value) {
        intensity = Math.max(0.0f, Math.min(1.0f, value));
    }

    public static boolean isActive() {
        return VeilRenderSystem.renderer().getPostProcessingManager().isActive(HORROR_SHADER);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (isActive()) {
            var pipeline = VeilRenderSystem.renderer().getPostProcessingManager().getPipeline(HORROR_SHADER);
            if (pipeline != null) {

            }
        }
    }
}