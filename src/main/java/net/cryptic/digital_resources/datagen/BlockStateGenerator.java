package net.cryptic.digital_resources.datagen;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.registry.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {

    private final ExistingFileHelper existingFileHelper;

    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
        this.existingFileHelper = exFileHelper;
    }

    @Override
    protected void registerStatesAndModels() {
        generateBlockModel(BlockRegistry.NULLA_ORE.get());
        generateBlockModel(BlockRegistry.RAW_NULLA_BLOCK.get());
        generateBlockModel(BlockRegistry.NULLA_BLOCK.get());

        generateBlockState(BlockRegistry.NULLA_ORE.get());
        generateBlockState(BlockRegistry.RAW_NULLA_BLOCK.get());
        generateBlockState(BlockRegistry.NULLA_BLOCK.get());
    }

    private void generateBlockModel(Block pBlock) {
        String name = pBlock.asItem().toString();
        models().withExistingParent(name, mcLoc("block/cube_all"))
                .texture("all", modLoc(String.format("block/%s", name)));
    }

    private void generateBlockState(Block pBlock) {
        String name = pBlock.asItem().toString();

        ModelFile modelFile = new ModelFile.ExistingModelFile(modLoc("block/" + name), existingFileHelper);
        getVariantBuilder(pBlock).forAllStates(state -> ConfiguredModel.builder().modelFile(modelFile).build());
    }




}
