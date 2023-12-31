<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement -->

<center>
Ambient sounds to fit vanilla.<br/>
A backfork of <a href="https://modrinth.com/mod/charmonium">Charmonium</a>.<br/>
Requires <a href="https://modrinth.com/mod/connector">Connector</a> and <a href="https://modrinth.com/mod/forgified-fabric-api">FFAPI</a> on forge.<br/>
</center>

---

Charmonium (and by extension this mod) adds biome, structure, and world feature-specific ambient sound to minecraft.

All types of ambience are toggleable, and by default the volume can be tweaked through the Ambient Sounds slider.

### Design

This is a backport of the very shiny post-rewrite version of Charmonium.

In place of the (1.20.2 only) Charmony API, Euphonium uses base fabric methods and an environment-agnostic version of Quilt Config ([Kaleido](https://github.com/sisby-folk/kaleido-config)) for a simple load-time toml configuration.

This makes Euphonium slightly less featureful (it has no config screen), but it *should* be very stable - thanks entirely to the quality of svenhjol's rewrite (there aren't even any mixins!). 

We hope these changes can help preserve the mod and make it easier to maintain or port to awkward versions. 

---

### Afterword

All mods are built on the work of many others.

This mod is included in [Tinkerer's Quilt](https://modrinth.com/modpack/tinkerers-quilt) - our modpack about rediscovering vanilla.

We're open to suggestions for how to implement stuff better - if you see something wonky and have an idea - let us know.
