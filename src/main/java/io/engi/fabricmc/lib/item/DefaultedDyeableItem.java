package io.engi.fabricmc.lib.item;

import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;

public interface DefaultedDyeableItem extends DyeableItem {
    default int getDefaultColor(ItemStack stack) {
        return 10511680;
    }

    default int getColor(ItemStack stack) {
        int color = DyeableItem.super.getColor(stack);
        if (color == 10511680) { // Override default brown color without copy-pasting the reference method
            return getDefaultColor(stack);
        }
        return color;
    }
}
