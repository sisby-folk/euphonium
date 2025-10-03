package folk.sisby.euphonium.sounds.world;

import folk.sisby.euphonium.EuphoniumClient;
import folk.sisby.euphonium.helper.WorldHelper;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.RepeatedWorldSound;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sound.WorldSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class High implements ISoundType<WorldSound> {
	public static SoundEvent SOUND;

	public High() {
		SOUND = SoundEvent.of(EuphoniumClient.id("world.high"));
	}

	public void addSounds(SoundHandler<WorldSound> handler) {
		if (!EuphoniumClient.CONFIG.worldAmbience.high) return;

		handler.getSounds().add(new RepeatedWorldSound(handler.getPlayer()) {
			@Override
			public boolean isValidSituationCondition() {
				int topY = level.getTopY(Heightmap.Type.WORLD_SURFACE, player.getBlockPos());
				int top = topY > 256 ? 200 : 150;

				return level.getRegistryKey() == World.OVERWORLD
					&& player.getBlockPos().getY() > top;

			}

			@Override
			public boolean isValidPlayerCondition() {
				return level.getRegistryKey() == World.OVERWORLD
					&& WorldHelper.isOutside(player);
			}

			@Nullable
			@Override
			public SoundEvent getSound() {
				return SOUND;
			}

			@Override
			public int getDelay() {
				return level.random.nextInt(100) + 100;
			}

			@Override
			public float getVolume() {
				return 0.85F;
			}
		});
	}
}
