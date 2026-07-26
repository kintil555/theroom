# Project Rules — Backrooms Fabric Mod

## Stack (jangan ubah tanpa alasan kuat)
MC 1.21.4 · Fabric Loader 0.16.9 · Fabric API 0.113.0+1.21.4 · Loom 1.9 · Java 21
Package root: `com.kintil555.backrooms`

## Hemat kredit
- Jangan re-read file yang isinya sudah ada di context, gunakan `str_replace` bukan tulis ulang file.
- Perubahan kecil = 1 `str_replace`. Jangan rewrite seluruh file kalau <40% berubah.
- Jangan jalankan `./gradlew build` berulang untuk cek typo — baca kode dengan teliti dulu.
- Jangan riset web untuk hal yang sudah ada di `.claude/versions.md`.
- Satu tugas = satu commit message jelas. Jangan commit per file kecil-kecil.
- Jawaban ringkas, tidak perlu jelaskan hal yang obvious dari kode.

## Stabilitas kode
- Semua registrasi (Item/Block/Sound/dll) lewat kelas di `registry/`, jangan taruh di `BackroomsMod` langsung.
- Field yang belum dipakai generator (room size, seed offset, dll) taruh di `BackroomsConfig`, jangan hardcode.
- Struktur NBT masuk ke `data/backrooms/structure/`, jangan taruh di tempat lain.
- Client-only code wajib di `src/client/java`, jangan campur ke `src/main`.
- Selalu cek `fabric.mod.json` entrypoint & `depends` tetap sinkron kalau tambah class baru.
- Sebelum ubah versi library, cek `.claude/versions.md` — update file itu juga kalau versi berubah.

## Alur kerja tugas baru
1. Baca `.claude/versions.md` + file relevan saja (jangan seluruh repo).
2. Kalau nambah fitur besar (block, entity, generator) → buat rencana singkat dulu sebelum nulis kode.
3. Implementasi.
4. Push via `github:push_files` (banyak file = satu commit).
