package net.cryptic.digital_resources.client.events;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.client.screen.ResourceSimulator.SimulatorScreen;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.cryptic.digital_resources.registry.MenuTypeRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeRegistry.RESOURCE_SIMULATOR_MENU.get(), SimulatorScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {

    }

    @SubscribeEvent
    public static void onItemColorHandlerEvent(final RegisterColorHandlersEvent.Item event) {
        ItemRegistry.getAllDataShards().forEach(shard -> event.register(shard::getColor, shard));
    }

}
