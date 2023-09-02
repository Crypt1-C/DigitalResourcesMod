package net.cryptic.digital_resources.common.item;

import net.minecraft.world.item.Item;

import java.util.List;

public class UpgradeItem extends Item {
    public UpgradeItem(List<Item> pList, Properties pProperties) {
        super(pProperties.stacksTo(16));
        pList.add(this);
    }
}
