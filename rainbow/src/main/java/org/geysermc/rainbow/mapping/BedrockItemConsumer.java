package org.geysermc.rainbow.mapping;

import net.minecraft.resources.Identifier;
import org.geysermc.rainbow.pack.BedrockFont;
import org.geysermc.rainbow.pack.BedrockItem;

public interface BedrockItemConsumer {

    void accept(BedrockItem item);

    void acceptFont(Identifier identifier, BedrockFont font);
}
