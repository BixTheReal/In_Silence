package org.bix_studio.in_silence;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {
    private static KeyMapping toggleShaderKey;

    @EventBusSubscriber(modid = "in_silence", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Registration {

        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            toggleShaderKey = new KeyMapping(
                    "key.in_silence.toggle_horror_shader",
                    GLFW.GLFW_KEY_H,
                    "key.categories.in_silence"
            );
            event.register(toggleShaderKey);
            System.out.println("✅ Keybind registriert: H-Taste für Horror Shader");
        }
    }

    // FORGE Bus für Tick-Events (kein bus Parameter = FORGE bus)
    @EventBusSubscriber(modid = "in_silence", value = Dist.CLIENT)
    public static class TickHandler {

        @SubscribeEvent
        public static void onClientTick(PlayerTickEvent.Post event) {
            if (toggleShaderKey != null) {
                while (toggleShaderKey.consumeClick()) {
                    System.out.println("🎮 H-Taste gedrückt! Toggle Shader...");
                    HorrorShaderManager.toggle();
                }
            }
        }
    }
}
