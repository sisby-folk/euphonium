package folk.sisby.euphonium.sounds.biome;

import folk.sisby.euphonium.EuphoniumClient;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import folk.sisby.euphonium.sound.BiomeSound;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.SoundHandler;

import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;

public class TheEnd implements ISoundType<BiomeSound> {
    public static SoundEvent SOUND;
    public static Predicate<Holder<Biome>> VALID_BIOME =
        holder -> holder.is(BiomeTags.IS_END);

    public TheEnd() {
        SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("biome.the_end"));
    }

    @Override
    public void addSounds(SoundHandler<BiomeSound> handler) {
        handler.getSounds().add(new BiomeSound(handler.getPlayer()) {
            @Override
            public boolean isValidBiomeCondition(Holder<Biome> holder, ResourceKey<Biome> key) {
                return VALID_BIOME.test(holder);
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
        });
    }
}
