package spinner.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;

//ovo je nasa DatabaseHelper klasa koja nasljeđuje SQLiteOpenHelper klasu
//SQLiteOpenHelper klasa je tipa abstract i zbog toga sve metode koje su navedene
//unutar nje se moraju implementirati unutar one klase koja ju nasljeđuje
//DatabaseHelper je klasa koju moramo stvoriti jer se u njoj odvijaju sve
//SQL operacije:kreiranje,azuriranje,upisivanje i iscitavanje podataka iz tablica
//ova klasa je zaduzena za sve bitnije operacije sto se ticu baze podataka
public class DatabaseHelper extends SQLiteOpenHelper {

    //ovdje smo deklarirali konstantne reference koje u sebe
    //spremaju neke vrijednosti.Npr kada zelimo imenovati kako ce
    //se nasa tablica s podacima zvati umjesto da pisemo "labels"
    //mi smo tu vrijenost spremili u konstanu tipa String imena DATABASE_NAME
    //i sad kad god zelimo negdje naznaciti ime tablice to onda
    //referenciramo preko imena DATABASE_NAME,isto tako i za ostale konstante
    private static final int DATABASE_VERSTION = 1;
    private static final String DATABASE_NAME = "spinner Example";
    private static final String TABLE_NAME = "labels";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";


    //kada zelimo stvoriti neku podklasu prvo moramo stvoriti instancu nadklase
    //da bi to napravili,prva linija unutar konstruktora mora zvati konstruktor
    //nadklase.To radimo preko kljucne rijeci super()
    //context -->referenca na kontekst,kako bi taj kontekst mogao biti prosljeđen roditelju
    //DATABASE_NAME -->konstanta klase,ime baze podataka(String)
    //DATABASE_VERSION -->konstanta klase,verzija baze podataka(int)
    DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSTION);
    }

    //ova metoda se poziva u trenutku kada podaci unutar baze podataka jos ne postoje
    //te u trenutku kada se ona prvi put stvara.Ako ova metoda vrati dobar rezultat(ne baca Exception)
    //onda se moze smatrati da je baza podataka uspjesno napravljena s određenim brojem verzije
    //tijelo ove metode je mjesto gdje bi se trebalo odviti pravljenje tablica i popunjavanje tih
    //tablica vrijednostima raspisivanje niza query operacija za upisivanje,azurianje...
    //SQLiteDatabase db --> kao parametar u ovu metodu se dodaje objekt tipa SQLiteDatabase db
    //db nam zapravo predstavlja bazu podataka koju smo upravo stvorili, sto znaci da bazu
    // podataka predajemo kao argument
    @Override
    public void onCreate(SQLiteDatabase db) {

        //ovdje smo stvorili CREATE_ITEM_TABLE  varijablu u koji ce biti tipa string,u nju ce biti
        //pohranjeno sve s desne strane.Na desnoj strani imamo naredbu CREATE TABLE koja je zaduzena
        //za kreiranje tablice, na nju nadodamo konstatnu TABLE_NAME u kojoj je pohranjeno ime koje
        //zelimo za tablicu, nakon toga nam dolazi zagrada, a u zagradi se navodi sve sto zelimo da
        //bude u tablici,znaci u tablici ce biti COLUMN_ID koji u sebi ima ime koje ce predstavljati
        //njegov redak,on biti tipa int i i bit ce jedinstven,nakon toga imamo redak koji ce
        //predstavljati ime a iskazan je kao COLUMN_NAME u koji je spremljen tekst "name"
        //sve te naredbe su spremljene u varijablu CREATE_ITEM_TABLE i te naredbe sada
        //referenciramo preko toga imena
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT)";

        //ova metoda izvrsava samo jednu SQL operaciju koja NIJE SELECT ili bilo koja druga
        //SQL operacija koja vraca neku vrijednost jer ova metoda ne vraca vrijednost
        //mi smo samo u varijablu CREATE_ITEM_TABLE spremili naredbu,ali ju mi nismo jos
        //izvrsili, a izvrsiti ju mozemo jedino preko metode execSQL() kojoj kao parameter
        //navodimo tu varijablu CREATE_ITEM_TABLE koja u sebi sadrzi sve potrebno za stvaranje
        //nove tablice sa potrebnim podacima o redcima i imenima redaka
        db.execSQL(CREATE_ITEM_TABLE);

    }

    //ova metoda je pozvana kada baza podataka mora biti azurirana,
    //odnosno kada se verzija baze podataka promjenila,automatski se promjenio i version number
    //u tijelu ove metode treba biti kod koji ce azurirati staru verziju
    //baze podataka,tj. napraviti novu verziju
    //kada otvorimo bazu podataka i vidimo da neki zeljeni podaci se ne nalaze u njoj
    //umjesto da ju obrisemo i napravimp novu sa novim zeljenim podacima, mozemo samo
    //ovu staru azurirati bez brisanja te ce joj se zbog toga promjeniti i version number,
    //a upravo to radimo pomocu onUpgrade metode
    //primjer: imamo aplikaciju na google play-u i zelimo ju azurirati a to zahtjeva i azuriranje
    //baze podataka, kada ju azuriramo u aplikacijama korisnika koji vec koriste staru verziju ce
    //biti pozvana onUpgrade metoda i baza podataka ce se azurirati
    //SQliteDatabase db --> baza podataka
    //oldVersion --> stara verzija baze podataka koja je tipa int
    //newVersion --> nova verzija baze podataka koja je tipa int
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //ovdje smo unutar metode execSQL koja izvrsava naredbe koje su joj predane kao parametar
        //naveli sto se treba izvrsiti.U nasem slucaju to je naredba DROP TABLE IF EXIST te joj
        //nakon te SQL naredbe dodajemo konstantu TABLE NAME u kojoj je spremljeno ime teblice
        //DROP TABLE IF EXIST  naredba brise cijelu tablicu ako ta tablica postoji sa navedenim imenom
        //znaci kada se pozove metoda onUpgrade prvo sto se napravi je da se izbrise tablica i
        //kreira se nova sto cemo vidjeti u sljedecem koraku
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //ovu metodu smo vec deklarirali,to je metoda koja je zasluzena za kreiranje tablice
        //kao parametar joj dodamo bazu podataka te ju ova metoda stvori,ona u svom tijelu ima
        //sve potrebne podatke kako bi kreirala novu tablicu
        onCreate(db);

    }


    public void insertLabel(String label){

        //stvorili smo varijablu db na lijevoj strani u koju spremamo ono sto nam je na desnoj strani
        //na desnoj strani se nalazi metoda getWritableDatabase koja nam omogucava da
        // stvorimo(ako ne postoji)/otvorimo(ako postoji) i omogucimo upisivanje u nju
        //prvi puta kada pozovemo ovu metodu izvrsavaju se onCreate(),onUpgrade() i onOpen()
        //getWritalbeDatabase() metodu smijemo pozivaju vise puta,kada god zelimo upisivati u bazu podataka
        //baza podata zapravo uopce nije stvorena ili otvrana dok se ne pozove metoda
        //getWritableDatabase ili getReadableDatabase,obje metode vracaju SQLiteDatabase
        SQLiteDatabase db = this.getWritableDatabase();


        //ova klasa se koristi za spremanje seta podataka  unutar objekta u obliku key/value parova
        //koje ContentResolver moze procesuirati
        //kada napravimo objekt od klase ContentValues kao u nasem slucaju mozemo pozvati metodu put()
        //koja sprema podatke u obliku key/value parova.key mora predstavljati ime jedne kolone u tablici
        //a value je vrijednost koja se unosi u tu tablicu
        ContentValues values = new ContentValues();

        //kao sto sam vec naveo gore ova metoda prima dva argumenta,na mjesto prvog parametra trebamo unijeti
        //ime tog retka,a kao drugi parameter unosimo vrijednost koju zelimo unjeti u taj redak
        values.put(COLUMN_NAME,label);


        //posto objekt klase Content values sluzi za upisivanje i azuriranje podataka u bazi pomocu
        //metode put(),s tom metodom onda odredimo sta sve zelimo azurirati u bazi i onda to unosimo
        //u objekt pomocu metode put() u obliku key/value parova
        //onda kada stvorimo objekt te klase moramo ga predati insert metodi kao argument,i ta metoda
        //unosi sve podatke koji su unešeni u objekt tipa ContentValues, u nasem slucaju objekt values
        db.insert(TABLE_NAME,null,values);

        //metoda close() sluzi za prekid konekcije sa bazom podataka
        db.close();
    }

    //List je interface koji nam omogucuje da spremamo podatke u tocno zeljenom redosljedu
    //List mozemo zamisliti kao array, samo sto array sprema varijable dok List sprema objekte
    //moguce je unijeti vise puta isti objekt, tj. moguce ga je duplicirati
    public List<String> getAllLabels(){

        //na desnoj strani nama je List, a na lijevoj ArrayList zbog toga sto je List
        //interface a ArrayList "implements" List
        //znaci na ovaj smo nacin stvorili objekt u memoriji tipa ArrayList i rekli
        //da ce u sebi pohranjivati stringove,a referecirali smo ga pomocu imena list
        //koji je tipa List
        //s desne strane nam mogu biti ili ArrayList ili LinkedList jer oni implemetiraju List
        //ovakav nacin je veoma dobar ako imamo veliki kod i zelimo mjenjati između ArrayList i LinkedList
        //onda to mozemo lako napraviti jer ce ostatak koda gledati taj tip kao da je List
        List<String> list = new ArrayList<String>();


        //ovdje smo deklarirali varijablu selectQuery tipa string i u nju smo pohranili sve sto
        //se nalazi na desnoj strani, a na desnoj strani se nalazi naredba "SELECT * FROM " koja
        //uzima sve podatke iz tablice cije je ime nadodano nakon naredbe
        //i sada kada god zelimo "izvuci" sve podatke iz neke tablice to referenciramo odnosno
        //tu naredbu zovemo preko imena selectQuery
        String selectQuery = "SELECT * FROM " + TABLE_NAME;


        //ovdje smo deklalirali varijablu db tipa SQLiteDatabase u koju smo spremili vrijdnost
        //koja je vracena pomocu metode getReadableDatabase()
        //this.getReadableDatabase() --> kreira ili otvara bazu podataka, ova metoda vraca isti objekt
        //                               kao i getWritableDatabase osim ako ne dode do nekog problema
        //                               kao npr, ako je memorija puna onda se ne mogu unositi novi podaci
        //                               u bazu nego se mogu jedini isčitavati, u tom slucaju ce biti vracen
        //                               samo onaj objekt koji moze citati bazu podatak,a ne moze upisivati
        SQLiteDatabase db = this.getReadableDatabase();



        //kada zelimo izvuci neke podatke iz baze podataka pomocu naredbe SELECT tada ce baza podataka
        //stvoriti Cursor objekt i vratiti referencu tog objekta
        //osnovna zadaca Cursor-a je da pokazuje na jedan redak rezultata koji je "izvucen" iz baze
        //npr kada zelimo updatati neki redak u tablici, cursor stavimo u taj redak i azuriramo taj redak
        //u tablici na kojem je cursor,svaki "query" u bazi podataka vraca objekt tipa Cursor i taj objekt
        //ukazuje na jedan redak
        Cursor cursor = db.rawQuery(selectQuery,null);

        //cursor je objekt koji u sebi sadrzava sve podatke koje koji su se izvukli iz baze prilikom
        //izvrsavanja operacija.Cursor ne zamisljamo kao neku funkcionalnost, nego kao nešto što
        //uzima podatke iz baze podataka na vrlo efikasan nacin
        //moveToFirst -->ova metoda govori cursor objektu da se pomakne u prvi red
        //               ova metoda radi dvije stvari, prva je da nam govori ima li podataka u bazi
        //               koju smo "query-ali", ako ona vraca false to znaci da nismo dobro "izvukli" podatke ili da nema podataka u tablici
        //               i druga je naravno da stavlja cursor na prvo mjesto(ako baza nije prazna i ima podataka)
        if (cursor.moveToFirst()){
            do {
                //ovaj dio koda nam je zasluzan za dodavanje podataka u drugi redak tablice
                list.add(cursor.getString(1));

                //moveToNext() metoda pomice cursor na sljedeci redak
            }while (cursor.moveToNext());
        }

        //
        cursor.close();

        //
        db.close();

        //
        return list;
    }

}
