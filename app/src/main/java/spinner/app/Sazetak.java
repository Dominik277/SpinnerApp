/*
-----------
O PROJEKTU
-----------
-Ovaj mali projekt nam prikazuje uporabu SQLite baze podataka, te prikazivanja podataka koji su pohranjeni u bazu
 s pomoću Spinner-a te s pomoću padajućeg menija.Na vrhu activitia imamo EditText view u koji unosimo zeljeni tekst
 koji zelimo pohraniti u bazu, klikom na gumb Dodaj taj podatak se sprema u bazu.Nakon toga u Spinneru s pomocu
 padajuceg menija mozemo prikazati sve podatke koje smo unijeli u bazu podataka.Također imamo i kontrolu da ako
 korisnik kojim slucajem nije unio nista u EditText view onda se na dnu ekrana u Toast poruci govori korisniku da
 nije unio nista u bazu podataka te da ponovi postupak.


-------------------
MainActivity klasa
-------------------
-U ovoj klasi na pocetku napravimo varijable tipova onih podataka koje imamo u XML datoteci te im damo imena, u nasem slucaju
 to su EditText,Button i Spinner, preko tih imena cemo ih referencirati.Te varijable su prazne skroz dok im ne dodamo metodu
 findViewById koja odlazi u nasu XML dateteku te trazi koji atributi su spremljeni pod imenom koje smo naveli kao argument te
 metode.Na ovaj nacin smo atribute iz XML-a pretvorili u Java objekte.Poslije toga smo definirali onClick metodnu na gumb unutar
 koje smo naveli sto ce se sve desavati kada kliknemo na taj gumb.Prilikom klika na gumb sve ono sto je upisano u EditText se sprema
 u string varijablu label te se ona provjerava pomocu if else narebe.Ako je korisnik kliknuo gumb nakon sto je upisao tekst u EditText
 taj podatak se sprema u bazu podataka, a ako kojim slucajem nije unio tekst prilikom klika na gumb izbacit ce mu se Toast poruka da
 unese tekst.U loadSpinnerData() metodi pravimo objekt baze podataka te pomocu adaptera unosimo podatek u bazu gdje adapter objekt ima
 sve potrebno za unos tih podataka.I na kraju imamo dvije metode koje smo morali implementirati jer smo nasljedili klasu
 AdapterView.onItemSelectedListener jer je ona abstraktna.Implementirali smo onNothingSelected() metodu kojoj nismo dodali nista unutar
 tijela, te smo implementirali onItemSelected() u kojoj smo definirali sta ce se desiti kada kliknemo bilo koji redak unutar padajuceg
 menija, a to je da ce na dnu ekrana izaci Toast poruka u kojoj ce biti navedeno onaj tekst koji je pisao.


---------------------
DatabaseHelper klasa
---------------------
-DatabaseHelper klasu mozemo zamislit kao klasu u kojoj se odvijaju sve operacije unosenja podataka u bazu.
 Na pocetku klase smo definirali neke konstante preko kojih cemo referencirati određene SQL naredbe.Npr u slucaju
 kada zelimo imenovati kako ce se nasa tablica zvati necemo napisati "labels" nego DATABASE_NAME jer smo u tu
 konstantu spremili string "label".Isto tako smo i za ostale dijelove tablice kao sto su redci i stupci.
 Kada napravimo neku podklasu prvo moramo stvoriti instancu nadklase, a to radimo tako da unutar konstruktora
 pozovemo konstruktor nadklase, a to radimo pomocu kljucne rijeci super() kojoj kao parametre predajemo context,
 ime baze podataka i verziju baze podataka.Ovo sve nam je bilo potrebno za instaciranje objekta ove klase.Nakon toga
 smo dosli do implemenitranih metoda koje smo morali implementirati jer je DatabaseHelper klasa podklasa klase
 SQLiteOpenHelper koja je abstraktna sto znaci da moramo implementirati sve metode koje se u njoj nalaze.Prva od
 metoda je onCreate(), ova metoda se poziva kada se baza podataka prvi puta pravi i unutar ove metode moraju biti sve
 narebe sto se tice unosenja,brisanja,azuriranja podataka u bazi.Nakon toga dolazi onUpgrade() metoda koja se poziva
 kada podaci unutar baze moraju biti azurirani, odnosno kada se verzija baze podataka mjenja.Kada otvorimo bazu i
 vidimo da u njoj nisu neki podaci koji zelimo da budu, ne moramo dropati bazu i ispocetka ju praviti nego ju samo
 azuriramo, a to azuriranje bi se trebalo izvrsiti u tijelu ove metode.Poslije toga dolazi metoda insertLabel koja
 nam sluzi za unosenje onoga sto unesemo u EditText u bazu podataka.Prvo smo stvorili objekt od klase
 SQLiteDatabase db koji nam zapravo predstavlja bazu podataka te preko metode getWritaableDatabase omogucili da se
 u tu bazu mogu unositi podaci, ali i iscitavati iz nje.Nakon toga smo stvorili objekt values tipa ContentValue koji
 u sebe prima podatke u obliku key/value parova s pomocu metode put.Key/value parovi zamisljamo tako da nam key predstavlja
 neki string, neki opis ili ime neke vrijednosti, a value je ta vrijednost koju key opisuje.Nakon toga na objektu db
 koji kao sto sam vec rekao predstavlja bazu podataka dodajemo metodu insert koja je mozemo to tako reci najbitnija, sve
 dok nismo pozvali metodu insert zapravo jos nista nije bilo spremljeno u bazu, do ovog trenutka smo mi samo pripremali
 sto ce biti ubaceno, a insert metoda to ubacuje u bazu.Kao parametre joj predajemo ime baze podataka i podatke koje treba
 spremiti te naposljetku na db objekt dodaje close() metodu koja prekida konekciju objekt s bazom podataka.Na kraju ove klase
 napravili smo metodu getAllLabels() koja je zaduzena za povlacenje svih podataka iz baze i slaganja u List-u.Prva naredba u
 ovoj klasi nam samo govori da smo stvorili objekt list u kojem ce biti svi podaci koje povucemo iz baze.U drugoj naredbi smo
 stvorili varijablu selectQuery tipa String koja jednostavno u sebi sadrzava query naredbe, nista drugo.Nakon toga smo s
 getReadableDatabase rekli da cemo iscitavati podatke iz baze, a da ih necemo upisivati.Te smo naposljetku napravili Cursor
 objekt pomocu kojeg smo navigirali koje tocno podatke zelimo povuci iz baze i obavezno zatvorili konekciju s bazom i cursorom
 pomocu metode close().




 */