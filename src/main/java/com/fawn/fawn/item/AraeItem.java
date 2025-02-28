package com.fawn.fawn.item;

import com.fawn.fawn.util.ColoredText;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;

public class AraeItem extends SwordItem {
    public AraeItem(Settings settings) {
        super(ToolMaterials.NETHERITE, 4, -2.6F, settings);
    }

    public Text getName(ItemStack stack) {
        return ColoredText.withColor(Text.translatable(getTranslationKey()), 0xFDEE84);
    }

    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
