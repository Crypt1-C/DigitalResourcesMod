package net.cryptic.digital_resources.datagen;

import net.cryptic.digital_resources.registry.BlockRegistry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockLootTableGenerator extends BlockLoot {
    @Override
    protected void addTables() {
        BlockRegistry.BLOCKS.getEntries().forEach(registryObject -> dropSelf(registryObject.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
