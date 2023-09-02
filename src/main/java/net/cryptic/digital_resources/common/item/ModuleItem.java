package net.cryptic.digital_resources.common.item;

import net.minecraft.world.item.Item;

import java.util.List;

public class ModuleItem extends Item {
    public ModuleItem(List<Item> pList, Properties pProperties) {
        super(pProperties.stacksTo(1));
        pList.add(this);
    }
}
