 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.WorldAmbience;
 import net.minecraft.core.BlockPos;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.world.level.block.Blocks;
 import org.jetbrains.annotations.Nullable;
  import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.RepeatedWorldSound;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.WorldSound;

 import java.util.Optional;

 public class Mineshaft implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public Mineshaft() {
         SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.mineshaft"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!WorldAmbience.CONFIG.mineshaft) return;

         handler.getSounds().add(new RepeatedWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 // Find the closest rail block in the mineshaft.  This will become the sound source.
                 Optional<BlockPos> rail = BlockPos.findClosestMatch(player.blockPosition(), 8, 16, pos -> {
                     var block = level.getBlockState(pos).getBlock();
                     return block == Blocks.RAIL;
                 });

                 if (rail.isPresent()) {
                     setPos(rail.get());
                     return true;
                 }

                 return false;
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
                 return level.random.nextInt(300) + 300;
             }

             @Override
             public float getVolume() {
                 return 0.8F;
             }
         });
     }
 }
