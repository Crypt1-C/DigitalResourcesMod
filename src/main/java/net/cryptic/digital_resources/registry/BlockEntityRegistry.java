package net.cryptic.digital_resources.registry;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.common.block.Simulator.ResourceSimulatorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    public static final RegistryObject<BlockEntityType<ResourceSimulatorBlockEntity>> RESOURCE_SIMULATOR = BLOCK_ENTITIES.register("resource_simulator", () -> BlockEntityType.Builder.of(ResourceSimulatorBlockEntity::new, BlockRegistry.SIMULATOR_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
