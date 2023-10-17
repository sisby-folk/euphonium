 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.helper.SoundHelper;
 import folk.sisby.euphonium.helper.WorldHelper;
 import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.RepeatedWorldSound;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.WorldSound;
 import net.minecraft.core.BlockPos;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.world.level.block.AmethystBlock;
 import net.minecraft.world.level.block.Block;
 import net.minecraft.world.level.block.Blocks;
 import org.jetbrains.annotations.Nullable;

 import java.util.Optional;

 public class Geode implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public Geode() {
         SOUND = SoundHelper.sound(EuphoniumClient.id("world.geode"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!EuphoniumClient.CONFIG.worldAmbience.geode) return;

         handler.getSounds().add(new RepeatedWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 Optional<BlockPos> optAmethyst = BlockPos.findClosestMatch(player.blockPosition(), 12, 8, pos -> {
                     Block block = level.getBlockState(pos).getBlock();
                     return block instanceof AmethystBlock;
                 });

                 Optional<BlockPos> optSmoothBasalt = BlockPos.findClosestMatch(player.blockPosition(), 12, 8, pos -> {
                     Block block = level.getBlockState(pos).getBlock();
                     return block == Blocks.SMOOTH_BASALT;
                 });

                 if (optAmethyst.isPresent() && optSmoothBasalt.isPresent()) {
                     setPos(optAmethyst.get());
                     return true;
                 }

                 return false;
             }

             @Override
             public boolean isValidPlayerCondition() {
                 return !WorldHelper.isOutside(player)
                     && WorldHelper.isBelowSeaLevel(player);
             }

             @Nullable
             @Override
             public SoundEvent getSound() {
                 return SOUND;
             }

             @Override
             public int getDelay() {
                 return level.random.nextInt(350) + 300;
             }

             @Override
             public float getVolume() {
                 return 0.5F;
             }

             @Override
             public float getPitch() {
                 return 0.8F + (0.4F * level.random.nextFloat());
             }
         });
     }
 }
