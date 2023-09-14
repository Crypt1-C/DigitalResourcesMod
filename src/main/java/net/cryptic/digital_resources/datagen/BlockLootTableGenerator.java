package net.cryptic.digital_resources.datagen;

import net.cryptic.digital_resources.registry.BlockRegistry;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class BlockLootTableGenerator extends BlockLoot {
    @Override
    protected void addTables() {
        for (RegistryObject<Block> object :BlockRegistry.BLOCKS.getEntries()) {
            if (!object.getId().toString().contains("ore")) {
                dropSelf(object.get());
            } else {
                add(object.get(), (block) -> createOreDrop(object.get(), ItemRegistry.RAW_NULLA.get()));
            }
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
