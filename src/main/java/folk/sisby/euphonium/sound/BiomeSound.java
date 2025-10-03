package folk.sisby.euphonium.sound;

import folk.sisby.euphonium.EuphoniumBiome;
import folk.sisby.euphonium.EuphoniumClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;

import java.util.ConcurrentModificationException;
import java.util.List;

public abstract class BiomeSound implements ISoundInstance {
	protected MinecraftClient client;
	protected boolean isValid = false;
	protected PlayerEntity player;
	protected ClientWorld level;
	protected LoopingSound soundInstance = null;
	protected float blendScaling = 1.0F;
	protected float volumeScaleFade = 0.005F;

	protected BiomeSound(PlayerEntity player) {
		this.client = MinecraftClient.getInstance();
		this.player = player;
		this.level = (ClientWorld) player.getEntityWorld();
	}

	public abstract boolean isValidBiomeCondition(RegistryEntry<Biome> holder, RegistryKey<Biome> key);

	@Override
	public void updatePlayer(PlayerEntity player) {
		this.player = player;
		this.level = (ClientWorld) player.getEntityWorld();
	}

	@Override
	public ClientWorld getLevel() {
		return level;
	}

	@Override
	public PlayerEntity getPlayer() {
		return player;
	}

	@Override
	public MovingSoundInstance getSoundInstance() {
		return soundInstance;
	}

	@Override
	public double getVolumeScaling() {
		return EuphoniumClient.CONFIG.biomeAmbience.volumeScaling;
	}

	@Override
	public void tick() {
		var log = EuphoniumClient.LOGGER;
		boolean nowValid = isValid();

		if (isValid && !nowValid) isValid = false;
		if (!isValid && nowValid) isValid = true;

		if (isValid) {
			var volume = getVolume() * getVolumeScaling() * blendScaling;

			if (!isPlaying()) {
				soundInstance = new LoopingSound(player, getSound(), (float) volume, getPitch(), p -> isValid);
				try {
					getSoundManager().play(this.soundInstance);
				} catch (ConcurrentModificationException e) {
					log.debug("[{}] Exception in tick", this.getClass().getSimpleName());
				}
			} else {

				// Adjust sound volume with a fade.
				if (soundInstance.maxVolume != volume) {
					if (soundInstance.maxVolume < volume) {
						soundInstance.maxVolume += volumeScaleFade;
					} else {
						soundInstance.maxVolume -= volumeScaleFade;
					}
				}
			}
		}
	}

	@Override
	public boolean isValid() {
		if (client.world == null || level == null) return false;
		if (!player.isAlive()) return false;
		if (!isValidPlayerCondition()) return false;

		if (!EuphoniumBiome.VALID_DIMENSIONS.contains(level.getRegistryKey().getValue())) {
			return false;
		}

		var pos = player.getBlockPos();
		var blend = (float) EuphoniumClient.CONFIG.biomeAmbience.biomeBlend;

		if (blend > 0) {

			// Sample points.
			var directions = List.of(
				Direction.EAST,
				Direction.WEST,
				Direction.NORTH,
				Direction.SOUTH
			);

			for (var direction : directions) {
				for (int i = 0; i < blend; i += 2) {
					var relativePos = pos.offset(direction, i);

					// Get the biome and key for condition check.
					var holder = getBiomeHolder(relativePos);
					var key = getBiomeKey(relativePos);
					if (isValidBiomeCondition(holder, key)) {
						this.blendScaling = 1.0F - ((float) i / blend);
						return true;
					}
				}
			}

		} else {

			// Get the biome and key for condition check.
			var holder = getBiomeHolder(pos);
			var key = getBiomeKey(pos);

			if (isValidBiomeCondition(holder, key)) {
				this.blendScaling = 1.0F;
				return true;
			}
		}

		return false;
	}
}
