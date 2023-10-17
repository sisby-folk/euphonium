 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.helper.WorldHelper;
 import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.SurfaceWorldSound;
 import folk.sisby.euphonium.sound.WorldSound;
 import folk.sisby.euphonium.sounds.biome.Icy;
 import folk.sisby.euphonium.sounds.biome.Mountains;
 import net.minecraft.sounds.SoundEvent;
 import org.jetbrains.annotations.Nullable;

 public class Bleak implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public Bleak() {
         SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.bleak"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!EuphoniumClient.CONFIG.worldAmbience.bleak) return;

         handler.getSounds().add(new SurfaceWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 var holder = getBiomeHolder(player.blockPosition());
                 return Icy.VALID_BIOME.test(holder)
                     || Mountains.VALID_BIOME.test(holder);
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
