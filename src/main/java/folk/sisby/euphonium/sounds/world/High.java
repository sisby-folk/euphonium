 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.WorldAmbience;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.world.level.Level;
 import org.jetbrains.annotations.Nullable;
  import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.RepeatedWorldSound;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.WorldSound;
 import folk.sisby.euphonium.helper.WorldHelper;

 public class High implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public High() {
         SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.high"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!WorldAmbience.CONFIG.high) return;

         handler.getSounds().add(new RepeatedWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 int top = level.getMaxBuildHeight() > 256 ? 200 : 150;

                 return level.dimension() == Level.OVERWORLD
                     && player.blockPosition().getY() > top;
             }

             @Override
             public boolean isValidPlayerCondition() {
                 return level.dimension() == Level.OVERWORLD
                     && WorldHelper.isOutside(player);
             }

             @Nullable
             @Override
             public SoundEvent getSound() {
                 return SOUND;
             }

             @Override
             public int getDelay() {
                 return level.random.nextInt(100) + 100;
             }

             @Override
             public float getVolume() {
                 return 0.85F;
             }
         });
     }
 }
