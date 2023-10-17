package folk.sisby.euphonium;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import java.util.List;

public class BiomeAmbienceConfig extends WrappedConfig {
	@Comment("Plays ambient background sound according to the biome and time of day.")
	public final Boolean enabled = true;

    @Comment("Number of blocks above the ground that biome ambience will be silenced.")
    @Comment("Set to zero to disable.")
    public final Integer cullSoundAboveGround = 32;

    @Comment("Number of blocks to check for neighbouring biomes.")
	@Comment("Set to zero to disable.")
    public final Integer biomeBlend = 32;

    @Comment("Affects the volume of all biome ambient sounds. 1.0 is full volume.")
    public final Double volumeScaling = 0.55D;

    @Comment("Dimensions in which biome ambience will be played.")
    public final List<String> dimensions = ValueList.create("minecraft:overworld",
        "minecraft:overworld",
        "minecraft:the_end"
    );
}
