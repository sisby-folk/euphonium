 package folk.sisby.euphonium.sounds.world;

 import folk.sisby.euphonium.EuphoniumClient;
 import folk.sisby.euphonium.WorldAmbience;
 import net.minecraft.sounds.SoundEvent;
 import net.minecraft.world.entity.npc.Villager;
 import net.minecraft.world.phys.AABB;
 import org.jetbrains.annotations.Nullable;
  import folk.sisby.euphonium.sound.ISoundType;
 import folk.sisby.euphonium.sound.SoundHandler;
 import folk.sisby.euphonium.sound.SurfaceWorldSound;
 import folk.sisby.euphonium.sound.WorldSound;
 import folk.sisby.euphonium.helper.WorldHelper;

 import java.util.List;

 public class Village implements ISoundType<WorldSound> {
     public static SoundEvent SOUND;

     public Village() {
         SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("world.village"));
     }

     public void addSounds(SoundHandler<WorldSound> handler) {
         if (!WorldAmbience.CONFIG.village) return;

         handler.getSounds().add(new SurfaceWorldSound(handler.getPlayer()) {
             @Override
             public boolean isValidSituationCondition() {
                 AABB bb = new AABB(player.blockPosition()).inflate(32);
                 List<Villager> villagers = level.getEntitiesOfClass(Villager.class, bb);

                 if (villagers.size() >= 2) {
                     Villager villager = villagers.get(player.getRandom().nextInt(villagers.size()));
                     setPos(villager.blockPosition());
                     return true;
                 }

                 return false;
             }

             @Override
             public boolean isValidPlayerCondition() {
                 return super.isValidPlayerCondition()
                     && !WorldHelper.isNight(player);
             }

             @Nullable
             @Override
             public SoundEvent getSound() {
                 return SOUND;
             }

             @Override
             public int getDelay() {
                 return level.random.nextInt(400) + 320;
             }

             @Override
             public float getVolume() {
                 return 0.82F;
             }
         });
     }
 }
