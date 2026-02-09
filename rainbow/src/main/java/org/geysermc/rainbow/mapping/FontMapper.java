package org.geysermc.rainbow.mapping;

import net.minecraft.resources.Identifier;
import org.geysermc.rainbow.Rainbow;
import org.geysermc.rainbow.mapping.texture.TextureResource;
import org.geysermc.rainbow.pack.BedrockFont;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FontMapper {

    public static BedrockFont mapFont(FontDefinition definition, AssetResolver assetResolver) {
        Map<String, BedrockFont.CharacterData> fontData = new HashMap<>();

        for (FontDefinition.Provider provider : definition.providers()) {
            if (!provider.type().equals("bitmap")) {
                continue;
            }

            Identifier textureIdentifier = provider.file();
            List<String> chars = provider.chars().orElse(List.of());
            int height = provider.height().orElse(8);
            int ascent = provider.ascent().orElse(7);

            int rows = chars.size();
            if (rows == 0) continue;
            int cols = chars.get(0).codePoints().toArray().length;
            if (cols == 0) continue;

            Optional<TextureResource> texture = assetResolver.getTexture(null, textureIdentifier);
            int texWidth = texture.map(tex -> tex.sizeOfFrame().width()).orElse(256);
            int texHeight = texture.map(tex -> tex.sizeOfFrame().height()).orElse(256);

            // Calculate character dimensions by dividing texture size by grid size
            int charWidth = texWidth / cols;
            int charHeight = texHeight / rows;

            String bedrockTexture = Rainbow.decorateTextureIdentifier(textureIdentifier).getPath();

            for (int row = 0; row < rows; row++) {
                int[] codePoints = chars.get(row).codePoints().toArray();
                for (int col = 0; col < Math.min(codePoints.length, cols); col++) {
                    int codePoint = codePoints[col];
                    if (codePoint == 0 || codePoint == 32) continue; // Skip null and space

                    String character = new String(new int[]{codePoint}, 0, 1);

                    // We use the ascent to slightly adjust the vertical positioning if possible,
                    // but Bedrock's font format is limited. We'll use height as advance.
                    fontData.put(character, new BedrockFont.CharacterData(
                            bedrockTexture,
                            List.of(col * charWidth, row * charHeight),
                            List.of(charWidth, charHeight),
                            height // advance (horizontal width in-game)
                    ));
                }
            }
        }

        return new BedrockFont(fontData);
    }
}
