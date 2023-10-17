package folk.sisby.euphonium.sounds.world;

import folk.sisby.euphonium.EuphoniumClient;
import folk.sisby.euphonium.WorldAmbience;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.LoopedWorldSound;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.WorldSound;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.Nullable;

public class CaveDepth implements ISoundType<WorldSound> {
    public static SoundEvent SOUND;

    public CaveDepth() {
        SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.deep_cave"));
    }

    @Override
    public void addSounds(SoundHandler<WorldSound> handler) {
        if (!WorldAmbience.CONFIG.caveDepth) return;

        handler.getSounds().add(new LoopedWorldSound(handler.getPlayer()) {
            @Override
            public boolean isValidSituationCondition() {
                var pos = player.blockPosition();

                // Don't play this if the player is in the Deep Dark, the combined sounds are too intense.
                var key = getBiomeKey(pos);
                if (key == Biomes.DEEP_DARK) {
                    return false;
                }

                if (!WorldAmbience.VALID_CAVE_DIMENSIONS.contains(level.dimension().location())) {
                    return false;
                }

                var light = level.getMaxLocalRawBrightness(pos);
                var bottom = level.getMinBuildHeight() < 0 ? 0 : 32;
                return !level.canSeeSkyFromBelowWater(pos)
                    && pos.getY() <= bottom
                    && light < WorldAmbience.CAVE_LIGHT_LEVEL;
            }

            @Override
            public boolean isValidPlayerCondition() {
                return !player.isUnderWater();
            }

            @Nullable
            @Override
            public SoundEvent getSound() {
                return SOUND;
            }
        });
    }
}
