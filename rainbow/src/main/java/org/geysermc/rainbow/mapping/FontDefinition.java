package org.geysermc.rainbow.mapping;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import java.util.List;
import java.util.Optional;

public record FontDefinition(List<Provider> providers) {
    public static final Codec<FontDefinition> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Provider.CODEC.listOf().fieldOf("providers").forGetter(FontDefinition::providers)
            ).apply(instance, FontDefinition::new)
    );

    public record Provider(String type, Identifier file, Optional<Integer> ascent, Optional<Integer> height, Optional<List<String>> chars) {
        public static final Codec<Provider> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("type").forGetter(Provider::type),
                        Identifier.CODEC.fieldOf("file").forGetter(Provider::file),
                        Codec.INT.optionalFieldOf("ascent").forGetter(Provider::ascent),
                        Codec.INT.optionalFieldOf("height").forGetter(Provider::height),
                        Codec.STRING.listOf().optionalFieldOf("chars").forGetter(Provider::chars)
                ).apply(instance, Provider::new)
        );
    }
}
