package folk.sisby.euphonium.sounds.world;

import folk.sisby.euphonium.EuphoniumClient;
import folk.sisby.euphonium.helper.SoundHelper;
import folk.sisby.euphonium.helper.WorldHelper;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.RepeatedWorldSound;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.WorldSound;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class Deepslate implements ISoundType<WorldSound> {
    public static SoundEvent SOUND;

    public Deepslate() {
        SOUND = SoundHelper.sound(EuphoniumClient.id("world.deepslate"));
    }

    public void addSounds(SoundHandler<WorldSound> handler) {
        if (!EuphoniumClient.CONFIG.worldAmbience.deepslate) return;

        handler.getSounds().add(new RepeatedWorldSound(handler.getPlayer()) {
            @Override
            public boolean isValidSituationCondition() {
                Optional<BlockPos> optBlock = BlockPos.findClosestMatch(player.blockPosition(), 8, 4, pos -> {
                    Block block = level.getBlockState(pos).getBlock();
                    return block == Blocks.DEEPSLATE;
                });

                return optBlock.isPresent();
            }

            @Override
            public boolean isValidPlayerCondition() {
                return !WorldHelper.isOutside(player)
                    && WorldHelper.isBelowSeaLevel(player);
            }

            @Nullable
            @Override
            public SoundEvent getSound() {
                return SOUND;
            }

            @Override
            public int getDelay() {
                return level.random.nextInt(400) + 400;
            }

            @Override
            public float getVolume() {
                return 0.7F;
            }
        });
    }
}
