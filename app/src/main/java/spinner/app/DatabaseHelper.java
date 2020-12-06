package spinner.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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


    //
    public DatabaseHelper(@Nullable Context context) {
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
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
