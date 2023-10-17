package folk.sisby.euphonium;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.IntegerRange;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;
import net.minecraft.sounds.SoundSource;

import java.util.List;

public class EuphoniumConfig extends WrappedConfig {
    @Comment("The channel that Euphonium will use for playing sounds")
    public final SoundSource channel = SoundSource.AMBIENT;

	@Comment("Plays ambient background sound according to the biome and time of day")
	public final Boolean biomeAmbienceEnabled = true;

	@Comment("Plays ambient sound according to world features around the player")
	public final Boolean worldAmbienceEnabled = true;

	public final BiomeAmbience biomeAmbience = new BiomeAmbience();
	public static final class BiomeAmbience implements Section {
		@Comment("Number of blocks above the ground that biome ambience will be silenced")
		@Comment("Set to zero to disable")
		public final Integer cullSoundAboveGround = 32;

		@Comment("Number of blocks to check for neighbouring biomes")
		@Comment("Set to zero to disable")
		public final Integer biomeBlend = 32;

		@Comment("Affects the volume of all biome ambient sounds")
		@FloatRange(min=0.0D, max=1.0D)
		public final Double volumeScaling = 0.55D;

		@Comment("Dimensions in which biome ambience will be played")
		public final List<String> dimensions = ValueList.create("minecraft:overworld", "minecraft:overworld", "minecraft:the_end");
	}

	public final WorldAmbience worldAmbience = new WorldAmbience();
	public static final class WorldAmbience implements Section {
		@Comment("Affects the volume of all world ambient sounds")
		@FloatRange(min=0.0D, max=1.0D)
		public final Double volumeScaling = 0.55D;

		@Comment("Number of blocks above the ground that biome ambience will be silenced")
		@Comment("Set to zero to disable")
		public final Integer cullSoundAboveGround = 32;

		@Comment("Plays ambient sounds while anywhere in the End")
		public final Boolean alien = true;

		@Comment("Plays ambient sounds in cold and/or barren overworld environments")
		public final Boolean bleak = true;

		@Comment("Plays ambient sounds when the player is underground and near deepslate blocks")
		public final Boolean deepslate = true;

		@Comment("Plays ambient sounds in dry and/or hot overworld environments")
		public final Boolean dry = true;

		@Comment("Plays ambient sounds when high up in the overworld")
		public final Boolean high = true;

		@Comment("Plays ambient sounds while inside a woodland mansion")
		public final Boolean mansion = true;

		@Comment("Plays ambient sounds in plains environments at night")
		public final Boolean nightPlains = true;

		@Comment("Plays ambient sounds when in a cold biome during a thunderstorm")
		public final Boolean snowstorm = true;

		@Comment("Plays water sounds from a nearby water source when underground")
		public final Boolean undergroundWater = true;

		@Comment("Plays ambient sounds when a player is inside a village")
		public final Boolean village = true;

		@Comment("Plays ambient sounds from a nearby mineshaft")
		public final Boolean mineshaft = true;

		@Comment("Plays ambient sounds from a nearby amethyst geode")
		public final Boolean geode = true;

		@Comment("Plays ambient sounds when the player is underground and near gravel blocks")
		public final Boolean gravel = true;

		@Comment("Plays more Intense cave sounds when below Y 0 and light level is lower than 10")
		public final Boolean caveDepth = true;

		@Comment("Plays a low drone sound when in a cave below a certain depth")
		public final Boolean caveDrone = true;

		@Comment("Depth (Y Coordinate) under which cave drone ambience will play")
		public final Integer caveDroneDepth = 48;

		@Comment("Light level at which cave ambience (drone and depth) will stop playing")
		@IntegerRange(min=0, max=16)
		public final Integer caveLightLevel = 10;

		@Comment("Dimensions in which cave ambience (drone and depth) will be played")
		public final List<String> caveDimensions = ValueList.create("minecraft:overworld", "minecraft:overworld");
	}
}
