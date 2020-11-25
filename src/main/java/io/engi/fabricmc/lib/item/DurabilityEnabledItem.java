package io.engi.fabricmc.lib.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public interface DurabilityEnabledItem {
    default boolean showDurability(ItemStack stack) {
        return true;
    }

    default int getDurability(ItemStack stack) {
        return stack.getDamage();
    }

    default int getMaxDurability(ItemStack stack) {
        return stack.getMaxDamage();
    }

    default int getDurabilityColor(ItemStack stack) {
        float damage = (float) getDurability(stack);
        float maxDamage = (float) getMaxDurability(stack);
        float hue = Math.max(0.0F, (maxDamage - damage) / maxDamage);
        return MathHelper.hsvToRgb(hue / 3.0F, 1.0F, 1.0F);
    }
}
