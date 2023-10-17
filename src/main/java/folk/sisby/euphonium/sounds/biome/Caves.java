package folk.sisby.euphonium.sounds.biome;

import folk.sisby.euphonium.EuphoniumClient;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import folk.sisby.euphonium.sound.BiomeSound;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.SoundHandler;

import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;

public class Caves implements ISoundType<BiomeSound> {
    public static SoundEvent DEEP_DARK;
    public static SoundEvent DRIPSTONE;
    public static SoundEvent LUSH;

    public static final Predicate<Holder<Biome>> VALID_BIOME =
        holder -> holder.is(BiomeTags.IS_BEACH);

    public Caves() {
        DEEP_DARK = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("biome.caves.deep_dark"));
        DRIPSTONE = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("biome.caves.dripstone"));
        LUSH = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("biome.caves.lush"));
    }

    @Override
    public void addSounds(SoundHandler<BiomeSound> handler) {
        // Deep dark caves.
        handler.getSounds().add(new BiomeSound(handler.getPlayer()) {
            @Override
            public boolean isValidBiomeCondition(Holder<Biome> holder, ResourceKey<Biome> key) {
                return key.equals(Biomes.DEEP_DARK);
            }

            @Override
            public boolean isValidPlayerCondition() {
                return true;
            }

            @Nullable
            @Override
            public SoundEvent getSound() {
                return DEEP_DARK;
            }
        });

        // Dripstone caves.
        handler.getSounds().add(new BiomeSound(handler.getPlayer()) {
            @Override
            public boolean isValidBiomeCondition(Holder<Biome> holder, ResourceKey<Biome> key) {
                return key.equals(Biomes.DRIPSTONE_CAVES);
            }

            @Override
            public boolean isValidPlayerCondition() {
                return true;
            }

            @Nullable
            @Override
            public SoundEvent getSound() {
                return DRIPSTONE;
            }
        });

        // Lush caves.
        handler.getSounds().add(new BiomeSound(handler.getPlayer()) {
            @Override
            public boolean isValidBiomeCondition(Holder<Biome> holder, ResourceKey<Biome> key) {
                return key.equals(Biomes.LUSH_CAVES);
            }

            @Override
            public boolean isValidPlayerCondition() {
                return true;
            }

            @Nullable
            @Override
            public SoundEvent getSound() {
                return LUSH;
            }
        });
    }
}
