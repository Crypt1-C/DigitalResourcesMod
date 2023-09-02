package net.cryptic.digital_resources.common.block.Encoder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class EncoderBlock extends Block {
    public EncoderBlock(Properties pProperties) {
        super(pProperties.sound(SoundType.NETHERITE_BLOCK));
    }
}
