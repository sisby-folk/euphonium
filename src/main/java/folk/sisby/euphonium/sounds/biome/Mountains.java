package folk.sisby.euphonium.sounds.biome;

import folk.sisby.euphonium.EuphoniumClient;
import folk.sisby.euphonium.helper.WorldHelper;
import folk.sisby.euphonium.sound.BiomeSound;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.SurfaceBiomeSound;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Mountains implements ISoundType<BiomeSound> {
	public static final Predicate<RegistryEntry<Biome>> VALID_BIOME = holder -> holder.isIn(BiomeTags.IS_MOUNTAIN)
		|| holder.isIn(ConventionalBiomeTags.MOUNTAIN)
		|| holder.isIn(BiomeTags.IS_HILL)
		|| holder.isIn(ConventionalBiomeTags.EXTREME_HILLS);
	public static SoundEvent DAY_SOUND;
	public static SoundEvent NIGHT_SOUND;

	public Mountains() {
		DAY_SOUND = SoundEvent.of(EuphoniumClient.id("biome.mountains.day"));
		NIGHT_SOUND = SoundEvent.of(EuphoniumClient.id("biome.mountains.night"));
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
			public boolean isValidBiomeCondition(RegistryEntry<Biome> holder, RegistryKey<Biome> key) {
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
			public boolean isValidBiomeCondition(RegistryEntry<Biome> holder, RegistryKey<Biome> key) {
				return VALID_BIOME.test(holder);
			}
		});
	}
}
