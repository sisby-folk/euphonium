 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.helper.SoundHelper;
 import folk.sisby.euphonium.helper.WorldHelper;
 import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.SurfaceWorldSound;
 import folk.sisby.euphonium.sound.WorldSound;
 import folk.sisby.euphonium.sounds.biome.Badlands;
 import folk.sisby.euphonium.sounds.biome.Desert;
 import folk.sisby.euphonium.sounds.biome.Savanna;
 import net.minecraft.sounds.SoundEvent;
 import org.jetbrains.annotations.Nullable;

 public class Dry implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public Dry() {
         SOUND = SoundHelper.sound(EuphoniumClient.id("world.dry"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!EuphoniumClient.CONFIG.worldAmbience.dry) return;

         handler.getSounds().add(new SurfaceWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 var holder = getBiomeHolder(player.blockPosition());

                 return Badlands.VALID_BIOME.test(holder)
                     || Desert.VALID_BIOME.test(holder)
                     || Savanna.VALID_BIOME.test(holder);
             }

             @Override
             public boolean isValidPlayerCondition() {
                 return !WorldHelper.isNight(player)
                     && WorldHelper.isOutside(player)
                     && !WorldHelper.isBelowSeaLevel(player);
             }

             @Nullable
             @Override
             public SoundEvent getSound() {
                 return SOUND;
             }

             @Override
             public int getDelay() {
                 return level.random.nextInt(750) + 500;
             }

             @Override
             public float getVolume() {
                 return 0.5F;
             }
         });
     }
 }
