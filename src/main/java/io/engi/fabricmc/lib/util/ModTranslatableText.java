package io.engi.fabricmc.lib.util;

import net.minecraft.text.TranslatableTextContent;

public class ModTranslatableText extends TranslatableTextContent {
    public ModTranslatableText(String modId, String namespace, String path, Object... args) {
        super(String.join(".", namespace, modId, path), args);
    }
}
