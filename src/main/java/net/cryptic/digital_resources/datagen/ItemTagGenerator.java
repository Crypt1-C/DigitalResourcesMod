package net.cryptic.digital_resources.datagen;

import net.cryptic.digital_resources.Main;
import net.cryptic.digital_resources.registry.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ItemTagGenerator extends ForgeRegistryTagsProvider<Item> {
    public ItemTagGenerator(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForgeRegistries.ITEMS, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        addKey(Items.CLAY_BALL,new ResourceLocation("forge", "clay"));

    }

    private void addKey(Item pItem, ResourceLocation pResourceLocation) {

        TagKey<Item> key = Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createTagKey(pResourceLocation);
        tag(key).add(pItem);

    }
}
