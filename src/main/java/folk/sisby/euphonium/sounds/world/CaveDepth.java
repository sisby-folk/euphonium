package folk.sisby.euphonium.sounds.world;

import folk.sisby.euphonium.EuphoniumClient;
import folk.sisby.euphonium.EuphoniumWorld;
import folk.sisby.euphonium.helper.SoundHelper;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.LoopedWorldSound;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.WorldSound;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class CaveDepth implements ISoundType<WorldSound> {
    public static SoundEvent SOUND;

    public CaveDepth() {
        SOUND = SoundHelper.sound(EuphoniumClient.id("world.deep_cave"));
    }

    @Override
    public void addSounds(SoundHandler<WorldSound> handler) {
        if (!EuphoniumClient.CONFIG.worldAmbience.caveDepth) return;

        handler.getSounds().add(new LoopedWorldSound(handler.getPlayer()) {
            @Override
            public boolean isValidSituationCondition() {
                var pos = player.blockPosition();

                if (!EuphoniumWorld.VALID_CAVE_DIMENSIONS.contains(level.dimension().location())) {
                    return false;
                }

                var light = level.getMaxLocalRawBrightness(pos);
                var bottom = level.getMinBuildHeight() < 0 ? 0 : 32;
                return !level.canSeeSkyFromBelowWater(pos)
                    && pos.getY() <= bottom
                    && light < EuphoniumClient.CONFIG.worldAmbience.caveLightLevel;
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
