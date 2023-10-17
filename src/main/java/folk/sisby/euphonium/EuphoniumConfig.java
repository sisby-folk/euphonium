package folk.sisby.euphonium;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class EuphoniumConfig extends WrappedConfig {
    @Comment("The channel that Euphonium will use for playing sounds. Defaults to 'ambient'.")
	@Comment("Options: music, record, weather, block, hostile, neutral, player, ambient, voice")
    public final String channel = "ambient";
}
