package folk.sisby.euphonium.sounds.world;

import folk.sisby.euphonium.EuphoniumClient;
import folk.sisby.euphonium.EuphoniumWorld;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.LoopedWorldSound;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.WorldSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class CaveDrone implements ISoundType<WorldSound> {
	public static SoundEvent SOUND;

	public CaveDrone() {
		SOUND = SoundEvent.of(EuphoniumClient.id("world.cave"));
	}

	public void addSounds(SoundHandler<WorldSound> handler) {
		if (!EuphoniumClient.CONFIG.worldAmbience.caveDrone) return;

		handler.getSounds().add(new LoopedWorldSound(handler.getPlayer()) {
			@Override
			public boolean isValidSituationCondition() {
				BlockPos pos = player.getBlockPos();
				int light = level.getLightLevel(pos);

				if (!EuphoniumWorld.VALID_CAVE_DIMENSIONS.contains(level.getRegistryKey().getValue())) {
					return false;
				}

				if (!level.isSkyVisibleAllowingSea(pos) && pos.getY() <= player.getEntityWorld().getSeaLevel()) {
					return pos.getY() <= EuphoniumClient.CONFIG.worldAmbience.caveDroneDepth || light <= EuphoniumClient.CONFIG.worldAmbience.caveLightLevel;
				}

				return false;
			}

			@Override
			public boolean isValidPlayerCondition() {
				return !player.isSubmergedInWater();
			}

			@Nullable
			@Override
			public SoundEvent getSound() {
				return SOUND;
			}
		});
	}
}
