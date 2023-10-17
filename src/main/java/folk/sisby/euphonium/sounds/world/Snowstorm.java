 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.WorldAmbience;
 import net.minecraft.sounds.SoundEvent;
 import org.jetbrains.annotations.Nullable;
  import folk.sisby.euphonium.sounds.biome.Icy;
 import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.RepeatedWorldSound;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.WorldSound;
 import folk.sisby.euphonium.helper.WorldHelper;

 public class Snowstorm implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public Snowstorm() {
         SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.snowstorm"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!WorldAmbience.CONFIG.snowstorm) return;

         handler.getSounds().add(new RepeatedWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 var holder = getBiomeHolder(player.blockPosition());
                 return Icy.VALID_BIOME.test(holder);
             }

             @Override
             public boolean isValidPlayerCondition() {
                 return WorldHelper.isOutside(player)
                     && getLevel().isThundering();
             }

             @Nullable
             @Override
             public SoundEvent getSound() {
                 return SOUND;
             }

             @Override
             public int getDelay() {
                 return level.random.nextInt(250) + 250;
             }

             @Override
             public float getVolume() {
                 return 0.9F;
             }
         });
     }
 }
