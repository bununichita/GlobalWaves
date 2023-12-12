# Copyright Bunu Nichita Adrian 323CA 2023

# Proiect GlobalWaves  - Etapa 2

In etapa anterioara toate functiile legate de user erau implementate
in clasa StoreUsers (public class). La aparitia noilor tipuri de user,
clasa "StoreUsers" a devenit clasa abstracta, fiind extinsa de clasele 
"StoreNormalUsers", "StoreArtists", "StoreHosts", "StoreAdmin".
Desi clasa "StoreAdmin" putea fi implementata ca o  clasa de sine
statatoare, am ales sa extinda "StoreUsers" pentru simplitate si
intelegere mai usoara a codului.

A fost adaugata "SourceAlbum", care extinde "SourceAudio", si are
functionalitati similare cu "SourcePlaylist".

Cerintele legate de pagini au fost implementate cu ajutorul interfetei
"Page", care este implementata de cele 4 tipuri de pagini. Am ales sa
calculez valorile printate de fiecare pagina pe loc, in defavoarea
retinerii unei pagini de catre user, care ar fi trebuit actualizata
la fiecare comanda ce modifica valori afisate de ""printCurrentPage()".

Am refactorizat codul cu ajutorul clasei singleton "StatisticsData", ce
retine informatii necesare realizarii comenzilor de tip "Statistici
Generale".

Am eliminat toate comentariile de tip cod comentat, si toate clasele
si pachetele goale.

Din cauza unei restante din anul 1 nu am putut duce la bun sfarsit
aceasta etapa, dar o sa fac acest lucru dupa deadline deoarece as
vrea sa ma folosesc de scheletul meu si in etapa 3. M-ar ajuta foarte
mult un feedback referitor la eventuale restructurari ce mi-ar fi de
folos in etapa 3. Multumesc!

