package folk.sisby.euphonium.sound;

import folk.sisby.euphonium.EuphoniumClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;

public abstract class WorldSound implements ISoundInstance {
	protected MinecraftClient client;
	protected PlayerEntity player;
	protected ClientWorld level;
	protected boolean isValid;
	protected boolean playUnderWater = false;

	public WorldSound(PlayerEntity player) {
		this.client = MinecraftClient.getInstance();
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
	public void updatePlayer(PlayerEntity player) {
		this.player = player;
		this.level = (ClientWorld) player.getEntityWorld();
	}

	public abstract boolean isValidSituationCondition();

	@Override
	public boolean isValid() {

		// Initial filters.
		if (client.world == null || level == null) return false;
		if (!player.isAlive()) return false;
		if (player.isSubmergedInWater() && !playUnderWater) return false;

		return isValidSituationCondition()
			&& isValidPlayerCondition();
	}

	@Override
	public double getVolumeScaling() {
		return EuphoniumClient.CONFIG.worldAmbience.volumeScaling;
	}
}
