 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.RepeatedWorldSound;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.WorldSound;
 import folk.sisby.euphonium.sounds.biome.TheEnd;
 import net.minecraft.sounds.SoundEvent;
 import org.jetbrains.annotations.Nullable;

 public class Alien implements ISoundType<WorldSound> {
    public static SoundEvent SOUND;

    public Alien() {
        SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.alien"));
    }

    public void addSounds(SoundHandler<WorldSound> handler) {
        if (!EuphoniumClient.CONFIG.worldAmbience.alien) return;

        handler.getSounds().add(new RepeatedWorldSound(handler.getPlayer()) {
            @Override
            public boolean isValidSituationCondition() {
                var holder = getBiomeHolder(player.blockPosition());
                return TheEnd.VALID_BIOME.test(holder);
            }

            @Override
            public boolean isValidPlayerCondition() {
                return true;
            }

            @Nullable
            @Override
            public SoundEvent getSound() {
                return SOUND;
            }

            @Override
            public int getDelay() {
                return level.random.nextInt(400) + 300;
            }

            @Override
            public float getVolume() {
                return 0.85F;
            }
        });
    }
}
