package folk.sisby.euphonium.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

import java.util.List;

/**
 * @version 1.0.0-charmonium
 */
@SuppressWarnings("unused")
public class WorldHelper {
	public static boolean isDay(PlayerEntity player) {
		long dayTime = player.getEntityWorld().getTimeOfDay() % 24000;
		return dayTime >= 0 && dayTime < 12700;
	}

	public static boolean isNight(PlayerEntity player) {
		long dayTime = player.getEntityWorld().getTimeOfDay() % 24000;
		return dayTime >= 12700;
	}

	public static boolean isThundering(PlayerEntity player) {
		return player.getEntityWorld().isThundering();
	}

	public static boolean isOutside(PlayerEntity player) {
		if (player.isSubmergedInWater()) return false;

		int blocks = 24;
		int start = 1;

		BlockPos playerPos = player.getBlockPos();

		if (player.getEntityWorld().isSkyVisible(playerPos)) return true;
		if (player.getEntityWorld().isSkyVisibleAllowingSea(playerPos)) return true;

		for (int i = start; i < start + blocks; i++) {
			BlockPos check = new BlockPos(playerPos.getX(), playerPos.getY() + i, playerPos.getZ());
			BlockState state = player.getEntityWorld().getBlockState(check);
			Block block = state.getBlock();

			if (player.getEntityWorld().isAir(check)) continue;

			if (!state.isOpaque()) continue;

			if (player.getEntityWorld().isSkyVisible(check)) return true;
			if (player.getEntityWorld().isSkyVisibleAllowingSea(check)) return true;
			if (state.isOpaque()) return false;
		}

		return player.getEntityWorld().isSkyVisible(playerPos.up(blocks));
	}

	public static float distanceFromGround(PlayerEntity player, int check) {
		var level = player.getEntityWorld();
		var pos = player.getBlockPos();
		var playerHeight = pos.getY();

		// Sample points.
		var samples = List.of(
			pos.east(check),
			pos.west(check),
			pos.north(check),
			pos.south(check)
		);

		int avg = 0;
		for (BlockPos sample : samples) {
			avg += level.getTopY(Heightmap.Type.WORLD_SURFACE, sample.getX(), sample.getZ());
		}
		avg /= samples.size();
		return Math.max(0.0F, playerHeight - avg);
	}

	public static boolean isBelowSeaLevel(PlayerEntity player) {
		return player.getBlockPos().getY() < player.getEntityWorld().getSeaLevel();
	}

	public static double getDistanceSquared(BlockPos pos1, BlockPos pos2) {
		double d0 = pos1.getX();
		double d1 = pos1.getZ();
		double d2 = d0 - pos2.getX();
		double d3 = d1 - pos2.getZ();
		return d2 * d2 + d3 * d3;
	}
}
