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

