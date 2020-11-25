package io.engi.fabricmc.lib.mixin;

import io.engi.fabricmc.lib.client.ItemColorsMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Shadow
    @Final
    private ItemColors itemColors;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(RunArgs args, CallbackInfo ci) {
        ItemColorsMap.INSTANCE.setBackedMap(itemColors);
    }
}
