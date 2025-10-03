package folk.sisby.euphonium.sound;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public interface ISoundInstance {
	boolean isValid();

	boolean isValidPlayerCondition();

	void tick();

	void updatePlayer(PlayerEntity player);

	PlayerEntity getPlayer();

	ClientWorld getLevel();

	@Nullable
	SoundEvent getSound();

	MovingSoundInstance getSoundInstance();

	default Biome getBiome(BlockPos pos) {
		return getBiomeHolder(pos).value();
	}

	default RegistryKey<Biome> getBiomeKey(BlockPos pos) {
		var biome = getBiome(pos);
		return getLevel().getRegistryManager()
			.getOptional(RegistryKeys.BIOME)
			.flatMap(registry -> registry.getKey(biome))
			.orElse(null);
	}

	default RegistryEntry<Biome> getBiomeHolder(BlockPos pos) {
		return getPlayer().getEntityWorld().getBiome(pos);
	}

	default MinecraftClient getMinecraft() {
		return MinecraftClient.getInstance();
	}

	default SoundManager getSoundManager() {
		return getMinecraft().getSoundManager();
	}

	default boolean isPlaying() {
		return getSoundInstance() != null
			&& getSoundManager().isPlaying(getSoundInstance());
	}

	default void stop() {
		getSoundManager().stop(getSoundInstance());
	}

	default int getDelay() {
		return 0;
	}

	default float getVolume() {
		return 1.0F;
	}

	default float getPitch() {
		return 1.0F;
	}

	default double getVolumeScaling() {
		return 1.0D;
	}
}
