# Házi feladat specifikáció

Információk [itt](https://viauac00.github.io/laborok/hf)

## Mobil- és webes szoftverek
### [Dátum]
### My bookshelfs
### Fekete Sámuel - (GJ8J3A)
### feketesamu@gmail.com
### Laborvezető: [Laborvezető neve]

## Bemutatás

Egy olyan alkalmazást szeretnék készíteni, aminek segítségével a felhasználók feljegyezhetik, hogy mely könyveket olvasták el vagy akarják elolvasni. A könyveket különböző, általuk készített virtuális polcokra tehetik fel, például "kedvencek", "el akarom olvasni", stb. Minden könyvhöz lehet jegyzetet is fűzni.

## Főbb funkciók

Az alkalmazás főképernyőjén könyveket lehet keresni cím és szerző alapján. A keresés eredményei a Google Books API-tól érkeznek. A könyvekre rákattintva egy olyan képernyőre kerülünk, ahol a könyvről több részlet olvasható. Itt található egy gomb, amivel a könyvet egy már létező vagy egy új polcra lehet rakni. A polcok nézetre a menüvel lehet elnavigálni, ahol át lehet látni, hogy mely könyvek vannak az egyes polcokon, illetve lehet könyveket törölni is a polcokról. Azokhoz a könyvekhez, amik már felkerültek egy polcra, lehet jegyzetet fűzni. A felhasználó polcai, azok tartalma valamint a jegyzetek perzisztenses tárolódnak a telefon tárhelyén.

## Választott technológiák:

- UI - Jetpack Compose???
- fragmentek
- RecyclerView
- Perzisztens adattárolás
- Hálózati (REST) kommunikáció
