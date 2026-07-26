# Analisis: V-Fast/BackroomsMod (referensi generator)

Repo: https://github.com/V-Fast/BackroomsMod (clone lokal, commit `2dcf13e`)

## Jawaban inti pertanyaan
**Tidak dipisah dinding/lantai.** Satu file `.nbt` = **satu ruangan utuh**
(lantai + dinding + langit-langit + prop di dalamnya jadi satu struktur).
Dicek langsung dari `room_1.nbt`: tag `size` = `[19, 8, 19]` (X, Y, Z) dan
semua block (lantai, tembok, langit-langit) ada dalam satu list `blocks`
di file yang sama. Tidak ada nbt terpisah untuk "wall.nbt" / "floor.nbt".

## Lokasi structure
`src/main/resources/data/backrooms/structure/level_0/room_1.nbt` s/d `room_24_extendpit.nbt`
— 24 varian ruangan level 0. Ukuran per file ±8–10 KB (gzip), ukuran ruangan
seragam grid 19×8×19 blok supaya bisa disambung jigsaw.

## Cara variasi/randomisasi ruangan: BUKAN custom generator Java
Mod ini **tidak punya `ChunkGenerator` custom**. Semua random-room memakai
sistem **vanilla Jigsaw Structure** (data-driven JSON), 3 lapis:

1. **`worldgen/structure_set/level_0_set.json`** — nempatin structure di
   dunia (placement `random_spread`, spacing 10, separation 9).
2. **`worldgen/structure/level_0.json`** — `"type": "minecraft:jigsaw"`,
   `start_pool` menunjuk ke template pool, `size: 20` (jigsaw depth /
   jumlah piece maksimal saat expand), `terrain_adaptation: beard_thin`.
3. **`worldgen/template_pool/level_0/poolextension.json`** — daftar semua
   room NBT + `weight` per elemen (`minecraft:single_pool_element`,
   `projection: rigid`). Weight tinggi = sering muncul (mis. room_1 = 50,
   room_23_pitfall = 1 → jarang/langka).

Jigsaw block yang ditanam di dalam tiap NBT (block `minecraft:jigsaw`,
biasanya di tepi ruangan) yang menentukan di mana ruangan lain boleh
"nyambung". Vanilla jigsaw generator otomatis pilih & sambung piece
secara acak sesuai weight — logic randomisasi sepenuhnya di JSON, bukan Java.

Satu-satunya custom Java structure di repo ini (`CaptainRuthieStructure`,
`BackroomsPieceTypes`) itu untuk structure easter-egg terpisah (NPC), bukan
untuk sistem ruangan utama.

## Implikasi untuk mod kita
- Cukup pakai vanilla Jigsaw, **tidak perlu bikin ChunkGenerator sendiri**.
- Struktur NBT yang perlu kita siapkan: tiap NBT = 1 ruangan lengkap
  (lantai+dinding+langit-langit), bukan potongan per elemen.
- Tiap NBT wajib punya minimal 1 block `minecraft:jigsaw` di sisi yang
  boleh disambung (nama target pool ditulis di NBT block-nya sendiri,
  via block-entity data jigsaw, bukan di JSON).
- Ukuran & orientasi antar room harus konsisten (mis. selalu kelipatan
  grid yang sama) supaya jigsaw bisa align.
- File config yang perlu dibuat setelah NBT siap: `structure_set`,
  `structure` (type jigsaw), `template_pool` — persis pola di atas.
- `BackroomsConfig.roomMinSize/roomMaxSize/structureWeightSeedOffset`
  (punya kita) sebenarnya tidak dipakai vanilla jigsaw — weight & size
  diatur di JSON, bukan runtime config. Field itu baru relevan kalau kita
  memilih bikin custom generator sendiri nanti (bukan jigsaw).

## Next steps repo ini
1. Putuskan: pakai vanilla Jigsaw (lebih stabil & simpel, dipakai mod
   referensi ini) vs custom ChunkGenerator (lebih fleksibel tapi lebih ribet).
2. Kalau pilih Jigsaw: siapkan NBT ruangan (grid seragam, ada jigsaw block
   di connector), lalu buat 3 file JSON (structure_set, structure, template_pool)
   seperti contoh di atas.
3. Update `.claude/dimension-work.md` kalau keputusan arsitektur berubah.
