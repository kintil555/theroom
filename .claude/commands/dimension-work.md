Task ini menyentuh dimension/chunk generator Backrooms. WAJIB ikuti urutan:

1. Cek apakah NBT structure sudah ada di `data/backrooms/structure/` — kalau kosong,
   STOP dan tanya user dulu, jangan generate struktur dummy diam-diam.
2. Baca `BackroomsConfig.java` untuk field `roomMinSize`/`roomMaxSize`/`structureWeightSeedOffset`
   yang sudah disiapkan — pakai itu, jangan bikin config baru.
3. Riset web HANYA untuk API generator (ChunkGenerator/StructurePlacement) 1.21.4,
   bukan untuk versi Fabric/Loom (sudah pin di `.claude/versions.md`).
4. Implementasi bertahap: generator dulu tanpa mob/loot, baru fitur tambahan.

Detail task: $ARGUMENTS
