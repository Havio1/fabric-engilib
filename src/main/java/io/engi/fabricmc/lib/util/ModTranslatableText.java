package io.engi.fabricmc.lib.util;

import net.minecraft.text.TranslatableText;

public class ModTranslatableText extends TranslatableText {
    public ModTranslatableText(String modId, String namespace, String path, Object... args) {
        super(String.join(".", namespace, modId, path), args);
    }
}
