package folk.sisby.euphonium.sounds.biome;

import folk.sisby.euphonium.EuphoniumClient;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import folk.sisby.euphonium.sound.BiomeSound;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.SurfaceBiomeSound;
import folk.sisby.euphonium.helper.WorldHelper;

import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;

public class Ocean implements ISoundType<BiomeSound> {
    public static SoundEvent DAY_SOUND;
    public static SoundEvent NIGHT_SOUND;
    public static final Predicate<Holder<Biome>> VALID_BIOME =
        holder -> holder.is(BiomeTags.IS_OCEAN) || holder.is(ConventionalBiomeTags.OCEAN);

    public Ocean() {
        DAY_SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("biome.ocean.day"));
        NIGHT_SOUND = SoundEvent.createVariableRangeEvent(EuphoniumClient.id("biome.ocean.night"));
    }

    @Override
    public void addSounds(SoundHandler<BiomeSound> handler) {
        // Day sound.
        handler.getSounds().add(new SurfaceBiomeSound(handler.getPlayer(), true) {
            @Nullable
            @Override
            public SoundEvent getSound() {
                return DAY_SOUND;
            }

            @Override
            public boolean isValidPlayerCondition() {
                return super.isValidPlayerCondition() && WorldHelper.isDay(player);
            }

            @Override
            public boolean isValidBiomeCondition(Holder<Biome> holder, ResourceKey<Biome> key) {
                return VALID_BIOME.test(holder);
            }
        });

        // Night sound.
        handler.getSounds().add(new SurfaceBiomeSound(handler.getPlayer(), true) {
            @Nullable
            @Override
            public SoundEvent getSound() {
                return NIGHT_SOUND;
            }

            @Override
            public boolean isValidPlayerCondition() {
                return super.isValidPlayerCondition() && WorldHelper.isNight(player);
            }

            @Override
            public boolean isValidBiomeCondition(Holder<Biome> holder, ResourceKey<Biome> key) {
                return VALID_BIOME.test(holder);
            }
        });
    }
}
