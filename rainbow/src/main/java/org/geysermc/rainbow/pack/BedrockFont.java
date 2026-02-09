package org.geysermc.rainbow.pack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;

public record BedrockFont(Map<String, CharacterData> fontData) {
    public static final Codec<BedrockFont> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(Codec.STRING, CharacterData.CODEC).fieldOf("font_data").forGetter(BedrockFont::fontData)
            ).apply(instance, BedrockFont::new)
    );

    public record CharacterData(String texture, List<Integer> uv, List<Integer> uvSize, int advance) {
        public static final Codec<CharacterData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("texture").forGetter(CharacterData::texture),
                        Codec.INT.listOf(2, 2).fieldOf("uv").forGetter(CharacterData::uv),
                        Codec.INT.listOf(2, 2).fieldOf("uv_size").forGetter(CharacterData::uvSize),
                        Codec.INT.fieldOf("advance").forGetter(CharacterData::advance)
                ).apply(instance, CharacterData::new)
        );
    }
}
