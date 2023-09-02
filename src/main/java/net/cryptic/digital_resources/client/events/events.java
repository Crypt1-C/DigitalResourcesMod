package net.cryptic.digital_resources.client.events;

import com.mojang.brigadier.ParseResults;
import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.client.particle.BinaryParticle;
import net.cryptic.digital_resources.registry.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.server.ServerLifecycleEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class events {

    @SuppressWarnings("removal")
    @SubscribeEvent
    public static void registerParticleFactries(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.BINARY_PARTICLE.get(), BinaryParticle.Provider::new);
    }

}
