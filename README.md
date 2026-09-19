# Client Commands for NeoForge

An unofficial NeoForge port of [ClientCommands](https://github.com/Earthcomputer/clientcommands)
by Earthcomputer, for **Minecraft 26.2** and **NeoForge 26.2**.

All commands and features are the work of the original project; this repository only contains the
port to the NeoForge loader. Licensed under LGPL-3.0-or-later, like the original.

## Requirements

- Java 25
- Minecraft 26.2 with NeoForge 26.2.0.72 (or a compatible 26.2 build)

Nothing else needs to be installed: the Fabric API modules and the Fabric-only libraries the mod
uses are jar-in-jar bundled.

## Building

```
./gradlew build
```

The mod jar is written to `build/libs/clientcommands-<version>.jar`.

## How the port works

This is not a rewrite: the original sources are kept as close to upstream as possible, and the
loader-specific layers are adapted around them.

- A `@Mod` entry point (`ClientCommandsNeoForge`) invokes the Fabric entry points of this mod and
  of the embedded Fabric-only libraries, in the order the Fabric loader would use.
- Fabric API surface that has a working NeoForge port is used as-is (command API, api-base,
  transitive access wideners, and the 26.2 fork of the networking API). Mod metadata, mixins and
  the access widener are declared for NeoForge instead of `fabric.mod.json`.
- The parts of the Fabric API that cannot run on 26.2 (client lifecycle events, level rendering
  events, render state data) are implemented natively on top of the corresponding NeoForge events.
  Those implementations live under `net.fabricmc.fabric.api.client.*` so the original sources keep
  compiling unchanged.
- Access widening is done with a native NeoForge access transformer
  (`META-INF/accesstransformer.cfg`), converted from the original access widener.
- A few compatibility mixins live in `mixin/compat`: they restore vanilla members that 26.2 moved
  or removed, and add the command-source state that the Fabric command source has on Fabric.

## Known limitations

- SimpleWaypoints (and with it `/cwaypoint` and waypoint rendering) is currently disabled: it needs
  HUD layer support that has not been ported yet.
- Command flags are stored on the command source in place instead of on a copy, so a flag can
  persist for the rest of the session (on Fabric the source is recreated per suggestion pass).
- `MushroomCowSheepAndSnowGolemMixin` does not match NeoForge's patched shearing code, so RNG
  tracking for shearing/milking is inactive on those paths. `defaultRequire` in
  `mixins.clientcommands.json` is set to 0 because of this.
