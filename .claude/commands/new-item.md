Tambah item baru ke mod ini mengikuti pola `registry/ModItems.java` yang sudah ada
(RegistryKey + Item.Settings + REGISTERED list). Jangan buat sistem registrasi baru.

Langkah:
1. Tambah field `register(...)` di `ModItems.java` (str_replace, jangan rewrite).
2. Tambah entri lang di `assets/backrooms/lang/en_us.json`.
3. Tambah model json di `assets/backrooms/models/item/<id>.json` (parent item/generated).
4. Kalau perlu tampil di creative tab, tambah `entries.add(...)` di `ModItemGroups.java`.
5. Commit satu kali untuk semua file di atas.

Nama item: $ARGUMENTS
