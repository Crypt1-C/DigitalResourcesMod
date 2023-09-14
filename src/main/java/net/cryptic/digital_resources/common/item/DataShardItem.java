package net.cryptic.digital_resources.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DataShardItem extends Item implements ItemColor {

    int color;

    public DataShardItem(List<DataShardItem> pList, Properties pProperties, int pColor) {
        super(pProperties);
        this.color = pColor;
        pList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        String resource = WordUtils.capitalize(pStack.getItem().toString().replace("_data_shard",""));
        Style colorStyle = Style.EMPTY.withColor(TextColor.fromRgb(this.color));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("item.data_shard.tooltip").withStyle(ChatFormatting.GRAY).append(Component.literal("[" + resource + "]").withStyle(colorStyle)));
        } else {
            pTooltipComponents.add(Component.translatable("item.tooltip.shift").withStyle(ChatFormatting.DARK_PURPLE));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public int getColor() {
        return this.color;
    }

    @SuppressWarnings("unused")
    public int getColor(ItemStack pItemStack, int pTintIndex) {
        return pTintIndex == 0 ? color : -1;
    }

}
