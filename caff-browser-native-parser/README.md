# Natív parser

## Felhasznált formátumok

- [CAFF formátum](docs/CAFF.txt)
  - tömörítés nélküli animációformátum
  - CIFF képek tárolására alkalmas
  - az animációhoz tartozó metaadatokat tárolja
- [CIFF formátum](docs/CIFF.txt)
  - tömörítés nélküli képformátum
  - pixel információkat tartalmaz
  - a képhez tartozó metaadatokat tárolja

## Futtatás

1. Az [images](/images) mappában található 3 db CAFF példa fájl, amivel tesztelhetjük a működést. A program az ebben a mappában található képekből dolgozik.
2. A fájlt nevét a `main` függvény `argv` listájából olvassa ki, emiatt erre szükség van a futtatási beállítások megadásánál.
   ![image info](../images/run-configurations.png)
3. A beparsolt CIFF fájlok az `output-images` mappába generálódnak `.ppm` formátumban.
4. A CAFF és CIFF fájlokhoz tartozó metaadatokból egy `.json` fájl kerül generálásra, amelyet az `output-json` mappában találunk.