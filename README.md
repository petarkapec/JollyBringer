# Jolly Bringer

Web-stranica za planiranje božićih aktivnosti!

# Opis projekta
Ovaj projekt je rezultat timskog rada u sklopu projeknog zadatka kolegija [Programsko inženjerstvo](https://www.fer.unizg.hr/predmet/proinz) na Fakultetu elektrotehnike i računarstva Sveučilišta u Zagrebu. 

Cilj projekta je napraviti web sučelje za upravljanje aktivnostima za vrijeme Božića. Korisnici mogu ući u grupe unutar kojih mogu raspravljati o Božiću i blagdanima, te planirati i upravljati aktivnostima dok čekaju Božić.


# Funkcijski zahtjevi
3.1 Korisničke uloge

Aplikacija uključuje tri vrste korisnika:

    Sudionici: članovi grupe koji sudjeluju u blagdanskim aktivnostima.
    Božićni predsjednik: organizator koji upravlja aktivnostima u grupi.
    Administrator: osoba s najvišim ovlastima koja nadzire aplikaciju i upravlja korisničkim računima.

3.2 Funkcionalnosti za sve korisnike

    Registracija i prijava u aplikaciju.
    Prikaz odbrojavanja do Božića (dani, sati, minute, sekunde).

3.3 Funkcionalnosti za Božićnog predsjednika

    Kreiranje grupe korisnika.
    Dodavanje korisnika u grupu.
    Upravljanje prikazom aktivnosti na dashboardu grupe.
    Dodavanje aktivnosti i prilagodba dnevnika aktivnosti u adventskom kalendaru.
    Korištenje AI agenta za automatsko dodavanje aktivnosti.

3.4 Funkcionalnosti za sudionike

    Pregled i interakcija s aktivnostima.
    Sudjelovanje u chatu.
    Ažuriranje aktivnosti i davanje povratnih informacija.

3.5 Funkcionalnosti za administratora

    Upravljanje korisničkim računima i grupama.
    Pristup za nadzor i moderaciju svih dijelova aplikacije.



# Tehnologije
> React  
> Vite    
> Java Spring  
> Postgres SQL
 
# Upute  
>Frontend u korijenu u terminalu treba pokrenuti uz npm install, npm run dev  
>Backend pokrenuti klasu JollyBringerApplication    

# Status
Frontend link https://jollybringer-frontend-latest.onrender.com  
Backend link https://jollybringer-latest.onrender.com  
Za prvu predaju napravili smo login stranicu uz oauth2 autentikaciju koja vodi na dashboard. Napravili smo funkcionalnost dodavanja korisnika u grupe koja funkcionira na način da se korisnik kroz header prijavi za ulogu Božićnog predsjednika, tu prijavu admin odobrava na admin dashboardu i korisnik postaje predsjednik. Tada predsjednik može u headeru napraviti grupu i dodati korisnike. Također admin u admin dashboardu vidi sve grupe, sve korisnike i sve prijave. Projekt radi lokalno na dev grani, ali deployana stranica ima https problem pa ne može dohvatiti dashboard. Za testiranje admin funkcionalnosti treba dodati u JSON datoteci u backendu svoj email i role_id 3.


# Članovi tima 
Ante Mrkonjić  
Antun Požgaj  
Petra Predrijevac  
Petar Kapec  
Klara Zaninović  
Marta Horvat  
Bruno Jurakić  

# Kontribucije
>Pravila ovise o organizaciji tima i su često izdvojena u CONTRIBUTING.md
> TODO možda?


# 📝 Kodeks ponašanja [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](CODE_OF_CONDUCT.md)
>TODO delete?
Kao studenti sigurno ste upoznati s minimumom prihvatljivog ponašanja definiran u [KODEKS PONAŠANJA STUDENATA FAKULTETA ELEKTROTEHNIKE I RAČUNARSTVA SVEUČILIŠTA U ZAGREBU](https://www.fer.hr/_download/repository/Kodeks_ponasanja_studenata_FER-a_procisceni_tekst_2016%5B1%5D.pdf), te dodatnim naputcima za timski rad na predmetu [Programsko inženjerstvo](https://wwww.fer.hr).
Očekujemo da ćete poštovati [etički kodeks IEEE-a](https://www.ieee.org/about/corporate/governance/p7-8.html) koji ima važnu obrazovnu funkciju sa svrhom postavljanja najviših standarda integriteta, odgovornog ponašanja i etičkog ponašanja u profesionalnim aktivnosti. Time profesionalna zajednica programskih inženjera definira opća načela koja definiranju  moralni karakter, donošenje važnih poslovnih odluka i uspostavljanje jasnih moralnih očekivanja za sve pripadnike zajenice.

Kodeks ponašanja skup je provedivih pravila koja služe za jasnu komunikaciju očekivanja i zahtjeva za rad zajednice/tima. Njime se jasno definiraju obaveze, prava, neprihvatljiva ponašanja te  odgovarajuće posljedice (za razliku od etičkog kodeksa). U ovom repozitoriju dan je jedan od široko prihvačenih kodeks ponašanja za rad u zajednici otvorenog koda.
>### Poboljšajte funkcioniranje tima:
>* definirajte načina na koji će rad biti podijeljen među članovima grupe
>* dogovorite kako će grupa međusobno komunicirati.
>* ne gubite vrijeme na dogovore na koji će grupa rješavati sporove primjenite standarde!
>* implicitno podrazmijevamo da će svi članovi grupe slijediti kodeks ponašanja.
 
>###  Prijava problema
>TODO delete?
>Najgore što se može dogoditi je da netko šuti kad postoje problemi. Postoji nekoliko stvari koje možete učiniti kako biste najbolje riješili sukobe i probleme:
>* Obratite mi se izravno [e-pošta](mailto:vlado.sruk@fer.hr) i  učinit ćemo sve što je u našoj moći da u punom povjerenju saznamo koje korake trebamo poduzeti kako bismo riješili problem.
>* Razgovarajte s vašim asistentom jer ima najbolji uvid u dinamiku tima. Zajedno ćete saznati kako riješiti sukob i kako izbjeći daljnje utjecanje u vašem radu.
>* Ako se osjećate ugodno neposredno razgovarajte o problemu. Manje incidente trebalo bi rješavati izravno. Odvojite vrijeme i privatno razgovarajte s pogođenim članom tima te vjerujte u iskrenost.

# 📝 Licenca
>TODO delete?
Važeča (1)
[![CC BY-NC-SA 4.0][cc-by-nc-sa-shield]][cc-by-nc-sa]

Ovaj repozitorij sadrži otvoreni obrazovni sadržaji (eng. Open Educational Resources)  i licenciran je prema pravilima Creative Commons licencije koja omogućava da preuzmete djelo, podijelite ga s drugima uz 
uvjet da navođenja autora, ne upotrebljavate ga u komercijalne svrhe te dijelite pod istim uvjetima [Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License HR][cc-by-nc-sa].
>
> ### Napomena:
>
> Svi paketi distribuiraju se pod vlastitim licencama.
> Svi upotrijebleni materijali  (slike, modeli, animacije, ...) distribuiraju se pod vlastitim licencama.

[![CC BY-NC-SA 4.0][cc-by-nc-sa-image]][cc-by-nc-sa]

[cc-by-nc-sa]: https://creativecommons.org/licenses/by-nc/4.0/deed.hr 
[cc-by-nc-sa-image]: https://licensebuttons.net/l/by-nc-sa/4.0/88x31.png
[cc-by-nc-sa-shield]: https://img.shields.io/badge/License-CC%20BY--NC--SA%204.0-lightgrey.svg

Orginal [![cc0-1.0][cc0-1.0-shield]][cc0-1.0]
>
>COPYING: All the content within this repository is dedicated to the public domain under the CC0 1.0 Universal (CC0 1.0) Public Domain Dedication.
>
[![CC0-1.0][cc0-1.0-image]][cc0-1.0]

[cc0-1.0]: https://creativecommons.org/licenses/by/1.0/deed.en
[cc0-1.0-image]: https://licensebuttons.net/l/by/1.0/88x31.png
[cc0-1.0-shield]: https://img.shields.io/badge/License-CC0--1.0-lightgrey.svg

### Reference na licenciranje repozitorija
