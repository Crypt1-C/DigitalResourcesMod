package net.cryptic.digital_resources.registry;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.common.block.Encoder.EncoderBlock;
import net.cryptic.digital_resources.common.block.Simulator.ResourceSimulatorBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static final RegistryObject<Block> SIMULATOR_BLOCK = registerBlock("resource_simulator",()-> new ResourceSimulatorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).requiresCorrectToolForDrops().strength(10.0F, 50.0F)), ItemTabRegistry.MISC_TAB);
    public static final RegistryObject<Block> ENCODER_BLOCK = registerBlock("resource_encoder",()-> new EncoderBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).requiresCorrectToolForDrops().strength(10.0F, 50.0F)), ItemTabRegistry.MISC_TAB);
    public static final RegistryObject<Block> NULLA_ORE = registerBlock("nulla_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.NETHER_ORE).requiresCorrectToolForDrops().strength(4.5F, 3.0F)), ItemTabRegistry.MISC_TAB);
    public static final RegistryObject<Block> RAW_NULLA_BLOCK = registerBlock("raw_nulla_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)), ItemTabRegistry.MISC_TAB);
    public static final RegistryObject<Block> NULLA_BLOCK = registerBlock("nulla_block", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).requiresCorrectToolForDrops().strength(10.0F, 50.0F)), ItemTabRegistry.MISC_TAB);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
