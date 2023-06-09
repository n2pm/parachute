# Parachute 0.7.0

This version updates Parachute to Minecraft 1.20, temporarily disables some features (and permanently removes others), and reverts from Quilt to Fabric. Enjoy!

## Versioning & toolchain

- Updated Parachute to Minecraft 1.20
- Reverted from Quilt to Fabric
  - Using Quilt mappings for 1.20 was annoying, and Parachute does not depend on any Quilt-specific APIs, so there was no real point in using it
  - Parachute is now compatible with both Fabric and Quilt instances

## Features

No new features have been added, but some features have been removed.

- Temporarily disabled some tweaks that don't work right now
  - Will be fixed and re-enabled in a future version
- Removed visual WorldEdit CUI
  - No longer needed as the [original mod](https://github.com/EngineHub/WorldEditCUI) has been updated
- Removed Brigadier string escapes
  - String escapes were technically challenging to support (requiring mixins targeting Brigadier)

## Dependencies

No action is required on your part, as Parachute's dependencies are embedded into itself.

- Updated [Cauldron](https://gitlab.com/adryd/cauldron) to Minecraft 1.20
  - Cauldron is a helper library originally designed for Parachute
  - 3D rendering in Cauldron is currently broken, but this will not impact using Parachute
- Temporarily switched to [a fork of MaLiLib](https://github.com/kosmolot-mods/malilib) as the original is not updated yet
  - We have opted to use [Kosmolot](https://modrinth.com/user/Kosmolot)'s fork
  - Parachute will be reverted to the original MaLiLib when available
  - Due to technical issues with Gradle, we ended up having to bundle the built artifact into the `libs` folder
    - This is commit `11e09ac4c5cc71e55e9bb82e022d453a7aa24494`, built locally by [adryd](https://modrinth.com/user/adryd)
