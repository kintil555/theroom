# Implementasi Dimensi Backrooms (Vanilla Jigsaw)

## File yang ditambahkan

### Structure NBT
`data/backrooms/structure/level0/*.nbt`
- room_1..room_9, room_upstairs, room_downstairs: grid 19×8×19 (kecuali
  room_upstairs 19×7×19), masing-masing 4 jigsaw block (N/S/E/W) di
  y=0, posisi (0,y,9) (9,y,0) (9,y,18) (18,y,9).
- hugeroom (19×18×19), hugeroompitfalls (19×25×19),
  hugeroompitfallschairs (19×25×19): sama, jigsaw di y=7.
- room_upstairs: 0 jigsaw block — dianggap dead-end/endpoint room.
  Kalau mau dia bisa nyambung juga, tambah 4 jigsaw block manual di
  Structure Block sebelum re-export NBT.
- Semua jigsaw block NBT-nya berisi `target/pool/name: minecraft:empty`
  (default kosong dari Structure Block). Ini normal — TIDAK perlu edit
  NBT satu-satu. Redirect dilakukan lewat `pool_aliases` di
  `worldgen/structure/level0.json`.

### Worldgen JSON
- `worldgen/template_pool/level0/rooms.json` — pool utama semua room
  (weight room biasa 40, stairs 6, huge room 1-3 karena langka).
- `worldgen/template_pool/level0/start_pool.json` — pool subset untuk
  piece pertama (room_1, room_2, room_5) supaya start selalu stabil.
- `worldgen/structure/level0.json` — jigsaw structure, `size: 32`
  (kedalaman maks), `start_height` y=1 (di atas bedrock), `terrain_adaptation: none`
  (backrooms bukan terrain-following), `pool_aliases` me-redirect alias
  `minecraft:empty` → `backrooms:level0/rooms`.
- `worldgen/structure_set/level0_set.json` — random_spread, spacing 2,
  separation 1 (rapat, backrooms harus terasa terus-menerus).
- `worldgen/biome/the_backrooms.json` — biome custom, no precipitation,
  fog kuning pudar, tanpa mob spawn normal.

### Dimension
- `dimension_type/the_backrooms.json` — no skylight, has_ceiling true,
  ambient_light rendah, monster_spawn_light_level 0 (untuk entity
  custom nanti, bukan mob vanilla karena spawner di biome kosong).
- `dimension/the_backrooms.json` — pakai `minecraft:flat` generator
  (bedrock y=0 + air), biar tidak perlu density_function/noise_settings
  custom. Struktur jigsaw dipasang di atas air kosong ini.

### Java
- `dimension/ModDimensions.java` — RegistryKey<World> konstan.
- `command/BackroomsCommand.java` — `/backrooms enter [target]` dan
  `/backrooms leave [target]`, pakai `ServerPlayerEntity#teleportTo(TeleportTarget)`
  (API 1.21.4). Permission level 2 (op).
- `BackroomsMod.java` — daftar command via `CommandRegistrationCallback`.

## Cara pakai in-game
1. Build & jalankan mod.
2. `/backrooms enter` — teleport ke dimensi, spawn di tengah room
   pertama (9.5, 2, 9.5).
3. Jalan ke tepi ruangan tempat jigsaw block ada → chunk baru generate,
   ruangan lain otomatis tersambung secara acak (vanilla jigsaw).
4. `/backrooms leave` — kembali ke overworld spawn.

## Yang masih perlu diputuskan/ditambah
- `room_upstairs` tanpa jigsaw: putuskan apakah dijadikan dead-end
  (biarkan) atau ditambah jigsaw block manual supaya ikut nyambung
  normal.
- Tekstur/model liminal (karpet kuning, wallpaper) — belum ada asset
  block custom, NBT yang di-upload asumsinya sudah pakai block vanilla
  (wool/carpet/concrete) sehingga tidak perlu resource pack tambahan;
  kalau NBT memakai block modded custom yang belum diregister, structure
  akan gagal load karena block ID tidak ditemukan.
- `max_distance_from_center: 80` membatasi radius grid dari titik start;
  naikkan kalau area backrooms perlu lebih luas dari 80 chunk-piece.
- Belum ada testing build (`gradlew`/Loom Maven tidak bisa diakses dari
  sandbox ini) — build & jalankan lokal untuk verifikasi kompilasi.
