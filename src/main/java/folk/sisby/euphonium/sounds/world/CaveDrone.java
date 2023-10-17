package folk.sisby.euphonium.sounds.world;

import folk.sisby.euphonium.EuphoniumClient;
import folk.sisby.euphonium.WorldAmbience;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.LoopedWorldSound;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.WorldSound;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class CaveDrone implements ISoundType<WorldSound> {
    public static SoundEvent SOUND;

    public CaveDrone() {
        SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.cave"));
    }

    public void addSounds(SoundHandler<WorldSound> handler) {
        if (!WorldAmbience.CONFIG.caveDrone) return;

        handler.getSounds().add(new LoopedWorldSound(handler.getPlayer()) {
            @Override
            public boolean isValidSituationCondition() {
                BlockPos pos = player.blockPosition();
                int light = level.getMaxLocalRawBrightness(pos);

                if (!WorldAmbience.VALID_CAVE_DIMENSIONS.contains(level.dimension().location())) {
                    return false;
                }

                if (!level.canSeeSkyFromBelowWater(pos) && pos.getY() <= player.level().getSeaLevel()) {
                    return pos.getY() <= 48 || light <= WorldAmbience.CAVE_LIGHT_LEVEL;
                }

                return false;
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
