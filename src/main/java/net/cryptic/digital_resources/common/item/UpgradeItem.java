package net.cryptic.digital_resources.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeItem extends Item {
    public UpgradeItem(List<Item> pList, Properties pProperties) {
        super(pProperties.stacksTo(16));
        pList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        String type = pStack.getItem().toString().replace("_upgrade","");

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable(String.format("item.upgrade.%s.tooltip",type)).withStyle(ChatFormatting.DARK_GRAY));
        } else {
            pTooltipComponents.add(Component.translatable("item.tooltip.shift").withStyle(ChatFormatting.DARK_PURPLE));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
