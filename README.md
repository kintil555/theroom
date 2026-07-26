# The Backrooms (Fabric Mod)

Minecraft 1.21.4, Fabric Loader 0.16.9, Fabric API 0.113.0+1.21.4, Loom 1.9.

## Status
Scaffold only. Dimension/chunk generator (rooms via random NBT structures)
is **not implemented yet** — waiting on the wall/floor NBT structure set.

Implemented:
- Mod init (`BackroomsMod`) + client entrypoint stub
- `backrooms.json` config (`config/BackroomsConfig.java`) with placeholder
  fields (`roomMinSize`, `roomMaxSize`, `structureWeightSeedOffset`) for the
  future generator
- Items: Almond Water (drinkable, regen buff), Duct Tape, Backrooms Key,
  Crumpled Note
- Creative tab, sound event registry stub
- `data/backrooms/structure/` — drop your wall/floor `.nbt` files here later

## Next steps
1. Add wall/floor NBT structures to `data/backrooms/structure/`.
2. Build a `ChunkGenerator` + dimension type that randomly picks from them.
3. Wire `BackroomsConfig` room-size fields into the generator.

## Build
```
./gradlew build
```
