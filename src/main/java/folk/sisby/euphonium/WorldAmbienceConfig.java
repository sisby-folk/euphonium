package folk.sisby.euphonium;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import java.util.List;

public class WorldAmbienceConfig extends WrappedConfig {
	@Comment("Plays ambient sound according to features of the world around the player.")
	public final Boolean enabled = true;

    @Comment("Affects the volume of all situational ambient sounds. 1.0 is full volume.")
    public final Double volumeScaling = 0.55D;

    @Comment("Number of blocks above the ground that biome ambience will be silenced.\n" +
        "Set to zero to disable.")
    public final Integer cullSoundAboveGround = 32;

    @Comment("If true, plays ambient sounds while anywhere in the End.")
    public final Boolean alien = true;

    @Comment("If true, plays ambient sounds in cold and/or barren overworld environments.")
    public final Boolean bleak = true;

    @Comment("If true, plays more Integerense cave sounds when below Y 0 and light level is lower than 10.")
    public final Boolean caveDepth = true;

    @Comment("If true, plays a low drone sound when in a cave below Y 48.")
    public final Boolean caveDrone = true;

    @Comment("If true, plays ambient sounds when the player is underground and near deepslate blocks.")
    public final Boolean deepslate = true;

    @Comment("If true, plays ambient sounds in dry and/or hot overworld environments.")
    public final Boolean dry = true;

    @Comment("If true, plays ambient sounds from a nearby amethyst geode.")
    public final Boolean geode = true;

    @Comment("If true, plays ambient sounds when the player is underground and near gravel blocks.")
    public final Boolean gravel = true;

    @Comment("If true, plays ambient sounds when high up in the overworld.")
    public final Boolean high = true;

    @Comment("If true, plays ambient sounds while inside a woodland mansion.")
    public final Boolean mansion = true;

    @Comment("If true, plays ambient sounds from a nearby mineshaft.")
    public final Boolean mineshaft = true;

    @Comment("If true, plays ambient sounds in plains environments at night.")
    public final Boolean nightPlains = true;

    @Comment("If true, plays ambient sounds when in a cold biome during a thunderstorm.")
    public final Boolean snowstorm = true;

    @Comment("If true, plays water sounds from a nearby water source when underground.")
    public final Boolean undergroundWater = true;

    @Comment("If true, plays ambient sounds when a player is inside a village.")
    public final Boolean village = true;

    @Comment("Dimensions in which cave ambience (drone and depth) will be played.")
    public final List<String> caveDimensions = ValueList.create("minecraft:overworld",
        "minecraft:overworld"
    );
}
