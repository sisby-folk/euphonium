 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.helper.SoundHelper;
 import folk.sisby.euphonium.helper.WorldHelper;
 import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.SurfaceWorldSound;
 import folk.sisby.euphonium.sound.WorldSound;
 import folk.sisby.euphonium.sounds.biome.Plains;
 import folk.sisby.euphonium.sounds.biome.Savanna;
 import net.minecraft.sounds.SoundEvent;
 import org.jetbrains.annotations.Nullable;

 public class NightPlains implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public NightPlains() {
         SOUND = SoundHelper.sound(EuphoniumClient.id("world.nightplains"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!EuphoniumClient.CONFIG.worldAmbience.nightPlains) return;

         handler.getSounds().add(new SurfaceWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 var holder = getBiomeHolder(player.blockPosition());
                 var key = getBiomeKey(player.blockPosition());
                 return Plains.VALID_BIOME.test(holder, key)
                     || Savanna.VALID_BIOME.test(holder);
             }

             @Override
             public boolean isValidPlayerCondition() {
                 return WorldHelper.isNight(player)
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
                 return level.random.nextInt(500) + 500;
             }

             @Override
             public float getVolume() {
                 return 0.6F;
             }
         });
     }
 }
