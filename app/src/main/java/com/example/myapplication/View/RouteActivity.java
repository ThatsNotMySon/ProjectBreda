package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.example.myapplication.Model.Datamanagement.Database;
import com.example.myapplication.Model.Datamanagement.DatabaseManager;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RouteActivity extends LanguageActivity {

    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private ArrayList<Database> databases;
    private RouteAdapter routeAdapter;
    private ArrayList<Database> finallist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);


        int orientation = getResources().getConfiguration().orientation;

        this.databaseManager = DatabaseManager.with(getApplicationContext());
        fillDatabase();

        databases = new ArrayList<>();
        databases = (ArrayList<Database>) this.databaseManager.allWaypoints();
        finallist = new ArrayList<>();

        int count = 0;
        for(Database database : databases){
            if(database.getRouteID() > count){
                finallist.add(database);
                count = database.getRouteID();
            }
        }


        recyclerView = findViewById(R.id.RoutesRecycle);

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }else{
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    this)
            );
        }

        this.routeAdapter = new RouteAdapter(finallist,databases);
        routeAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(this.routeAdapter);
        Log.d("recyclerview", "recylerviewset");
    }

    public void fillDatabase(){
        databaseManager.removeData();
        databaseManager.addData(new Database(51.594112f, 4.779417f, "VVV Beginpunt tot maart 2019", "VVV startpoint until march 2019", 1));
        databaseManager.addData(new Database(51.593278f, 4.779388f, "Liefdeszuster \n Symbolisch beeld voor het religieus verleden van Breda. De Liefdezuster geeft de dienstverlening weer, zoals de Gasthuiszusters die eeuwenlang in de praktijk brachten. Het opschrift is verwarrend, waarschijnlijk zijn twee religieuze orden met elkaar verward en samengevoegd.",
                "Liefdeszuster \n" +
                        "Symbolic image for the religious past of Breda. De Liefdezuster displays the services, such as the Gasthuiszusters who put them into practice for centuries. The inscription is confusing, probably two religious orders are confused and merged.", 1));
        databaseManager.addData(new Database(51.592624f, 4.779659f, "Nassau Baronie monument \n Het monument werd geplaatst ter herinnering aan de 500-jarige band (1904) tussen Breda en het huis Oranje-Nassau. In 1404 werd Engelbrecht I van Nassau-Dillenburg gehuldigd als heer van Breda.\n" +
                "\n" +
                "Het beeld werd gemaakt in de vorm van een burcht en bekroond met een leeuw in een Hollandse tuin. Naast de namen en familiewapens van Engelbrecht en zijn vrouw Johanna van Polanen, staan op het monument de wapens van de gemeenten in de Baronie van Breda.\n" +
                "\n" +
                "Het monument werd 3 juli 1905 onthuld door koningin Wilhelmina.",
                "Nassau Baronie monument \n The monument was placed in memory of the 500-year bond (1904) between Breda and the Oranje-Nassau house. In 1404, Engelbrecht I of Nassau-Dillenburg was honored as lord of Breda.\n" +
                        "\n" +
                        "The statue was made in the shape of a castle and crowned with a lion in a Dutch garden. In addition to the names and family arms of Engelbrecht and his wife Johanna van Polanen, the monument also contains the arms of the municipalities in the Baronie van Breda.\n" +
                        "\n" +
                        "The monument was unveiled on July 3, 1905 by Queen Wilhelmina.", 1));
        databaseManager.addData(new Database(51.592833f, 4.778472f, "The light house \n Lighthouse (Nederlands: Vuurtoren) is een kunstwerk van de Italiaanse architect Aldo Rossi in Park Valkenberg in Breda. Het is opvallend dat deze vuurtoren in Breda staat, een stad die geen geschiedenis kent met de zee en dus nooit een vuurtoren heeft gehad.",
                "The light house \n Lighthouse (Dutch: Vuurtoren) is a work of art by the Italian architect Aldo Rossi in Park Valkenberg in Breda. It is striking that this lighthouse is in Breda, a city that has no history with the sea and therefore has never had a lighthouse. ", 1));
        databaseManager.addData(new Database(51.590612f, 4.776167f, "Kasteel van Breda \n Het Kasteel van Breda is een kasteel in de Nederlandse provincie Noord-Brabant, gelegen in het centrale stadsdeel van Breda aan het Kasteelplein in de wijk Valkenberg. Sinds 1826 is de Koninklijke Militaire Academie (KMA) gevestigd in het kasteel, waardoor het kasteel niet vrij toegankelijk is voor publiek. De uitzonderingen op deze regel zijn de jaarlijkse Brabantse Kastelendag (waarop het Kasteel van Breda voor één dag toegankelijk is) en rondleidingen via de lokale VVV (beide onder begeleiding van een gids). Veel van de oorspronkelijke versterkingen zijn niet langer aanwezig; slechts aan het Spanjaardsgat zijn nog twee zevenhoekige torens zichtbaar. Het kasteel staat in de 'Top 100 van de Rijksdienst voor de Monumentenzorg' uit 1990.",
                "Kasteel van breda \n The Castle of Breda is a castle in the Dutch province of Noord-Brabant, located in the central district of Breda on the Kasteelplein in the Valkenberg district. The Royal Military Academy (KMA) has been located in the castle since 1826, making the castle not freely accessible to the public. The exceptions to this rule are the annual Brabant Castle Day (on which the Castle of Breda is accessible for one day) and guided tours via the local tourist office (both accompanied by a guide). Many of the original reinforcements are no longer present; two heptagonal towers are still visible at the Spanjaardsgat. The castle is in the 'Top 100 of the National Heritage Office' from 1990.", 1));
        databaseManager.addData(new Database(51.589695f, 4.776138f, "Stadhouderspoort \n De Stadhouderspoort is een poort die het Binnenhof en Buitenhof in Den Haag met elkaar verbindt. Het is een van de vier (de meest westelijke) nog bestaande poorten van het Binnenhofcomplex.",
                "Stadhouderspoort \n The Stadhouderspoort is a gate that connects the Binnenhof and Buitenhof in The Hague. It is one of the four (the most westerly) still existing gates of the Binnenhof complex.", 1));
        databaseManager.addData(new Database(51.590028f, 4.774362f, "Huis van Brecht (rechter zijde) \n Het Huis van Brecht is een gebouw in het centrum van Breda op het terrein van het Kasteel van Breda. Het is gemaakt van steen en dateert uit de tweede helft van de veertiende eeuw. Gouvaert van Brecht kocht het in 1530 en vergrootte de woning met een galerij. Na de list met het Turfschip van Breda vlucht de Spaanse familie naar Luik en werd het huis van het Bredase stadsbestuur.",
                "Huis van Brecht (Right side) \n The Huis van Brecht is a building in the center of Breda on the grounds of the Castle of Breda. It is made of stone and dates from the second half of the fourteenth century. Gouvaert van Brecht bought it in 1530 and enlarged the house with a gallery. After the list with the Peat ship from Breda, the Spanish family fled to Liège and became the home of the Breda city administration.", 1));
        databaseManager.addData(new Database(51.590195f, 4.773445f, "Spanjaardsgat (rechter zijde) \n Het Spanjaardsgat is een waterpoort die ligt tussen de Granaattoren en de Duiventoren van het Kasteel van Breda in het centrum van Breda. De waterpoort is normaliter alleen vanaf de buitenkant te zien, maar bij rondleidingen over het kasteelterrien ook vanaf de binnenkant. Het ligt bij de Haven.",
                "Spanjaardsgat (Right side) \n The Spanjaardsgat is a water gate that lies between the Grenade Tower and the Pigeon Tower of the Castle of Breda in the center of Breda. The water gate can normally only be seen from the outside, but with tours of the castle terrien also from the inside. It is located at the Port.", 1));
        databaseManager.addData(new Database(51.589833f, 4.773333f, "Begin Vismarkt \n De Vismarkt is een van de drie open ruimtes in de binnenstad van Groningen. Het is een langwerpig, rechthoekig plein. Aan de oostzijde wordt de markt begrensd door bebouwing die wordt onderbroken door het Koude gat en Tussen beide markten, aan de westzijde wordt de markt afgesloten door de Korenbeurs, met daarachter de Der Aa-kerk.",
                "Begin Vismarkt \n The Vismarkt is one of three open spaces in the center of Groningen. It is an elongated, rectangular square. On the east side, the market is bordered by buildings interrupted by the Cold Hole and Between the two markets, on the west side, the market is closed by the Korenbeurs, followed by the Der Aa church.", 1));
        databaseManager.addData(new Database(51.589362f, 4.774445f, "Begin Havermarkt \n De Havermarkt is een pleintje in de binnenstad van Breda. Het ligt vlak bij de Grote of Onze-Lieve-Vrouwekerk en de Haven. Het is een pleintje met rondom cafés met terrassen en een bekende uitgaansplek in Breda. Onder meer is er het monumentale pand de Vogel Struys met asymmetrische trapgevel uit 1665.",
                "Begin Havemarkt \n The Havermarkt is a square in the center of Breda. It is close to the Grote or Onze-Lieve-Vrouwekerk and the Haven. It is a small square with cafés with terraces all around and a famous nightlife spot in Breda. Among other things, there is the monumental building the Vogel Struys with asymmetrical stepped gable from 1665.", 1));
        databaseManager.addData(new Database(51.588833f, 4.775278f, "Grote Kerk \n De Grote of Onze-Lieve-Vrouwekerk is het belangrijkste monument van Breda. Zij heeft bovendien een nationale betekenis. Een onderdeel van de kerk wordt gevormd door de Prinsenkapel, die het mausoleum is van de vroege voorvaderen van de Nederlandse koninklijke familie, het geslacht van Nassau-Dillenburg.",
                "Grote Kerk \n The Grote or Onze-Lieve-Vrouwekerk is the most important monument in Breda. It also has national significance. A part of the church is formed by the Prinsenkapel, which is the mausoleum of the early ancestors of the Dutch royal family, the Nassau-Dillenburg family.", 1));
        databaseManager.addData(new Database(51.588205f, 4.775272f, "Het Poortje \n Poortje uit 1632 met drie burchten en de spreuk \"En attendant\" gebeeldhouwd familiewapen boven op zandstenen waterlijst in Breda",
                "Het Poortje \n \n" +
                        "Little gate from 1632 with three fortresses and the saying \"En attendant\" carved family crest on top of sandstone water list in Breda", 1));
        databaseManager.addData(new Database(51.587173f, 4.775802f, "Ridderstraat \n De Ridderstraat is een belangrijke winkelstraat in het hart van Breda. De Ginnekenstraat, Eindstraat, Karrestraat Torenstraat en Vismarktstraat moeten dateren uit de twaalfde eeuw. Men veronderstelt algemeen dat deze straten onderdeel uitmaakten van de prestedelijke nederzetting als ontsluitingsroute vanuit het zuiden. Hiervan takte een tweede weg af, veronderstelt men, de route Ridderstraat – Sint Janstraat – Veemarktstraat. Deze straten vormen dus de oudste generatie straten in het centrum.",
                "Ridderstraat \n The Ridderstraat is an important shopping street in the heart of Breda. The Ginnekenstraat, Eindstraat, Karrestraat Torenstraat and Vismarktstraat must date from the twelfth century. It is generally assumed that these streets were part of the prestige settlement as an access route from the south. A second road took off, it is assumed, the route Ridderstraat - Sint Janstraat - Veemarktstraat. These streets therefore form the oldest generation of streets in the center.", 1));
        databaseManager.addData(new Database(51.587417f, 4.776555f, "Grote Markt \n De Grote Markt is het belangrijkste plein van de Nederlandse stad Breda. Het ligt dicht bij de Havermarkt en de Reigerstraat in het centrum van de stad.\n" +
                "\n" +
                "Hier staan de beroemde Onze Lievevrouwekerk met het praalgraf van Engelbrecht II van Nassau en het oude stadhuis van Breda. Ook staan op de Grote Markt enkele monumentale panden die veelal een horeca-gelegenheid huisvesten. Direct achter enkele panden aan de Grote Markt staat ingesloten tussen de gebouwen de Lutherse kerk.",
                "Grote Markt \n The Grote Markt is the most important square in the Dutch city of Breda. It is close to the Havermarkt and Reigerstraat in the center of the city.\n" +
                        "\n" +
                        "Here is the famous Church of Our Lady with the tomb of Engelbrecht II of Nassau and the old town hall of Breda. There are also a number of monumental buildings on the Grote Markt that often house a catering establishment. Immediately behind a few buildings on the Grote Markt is the Lutheran church enclosed between the buildings.", 1));
        databaseManager.addData(new Database(51.588028f, 4.776333f, "Bevrijdingsmonument \n Het standbeeld van Judith, het monument ter herinnering van de Bredase gevallenen in de Tweede Wereldoorlog op de Grote Markt, werd onthuld op 24 juni 1952. Het is vervaardigd door Niel Steenbergen (1911-1997). Het is het bekendste van de vele vredesgedenktekens van zijn hand. Deze joodse vrouw (aldus het apocriefe Bijbelboek) wist door list door te dringen tot de legertent van de Assyrische bevelhebber Holofernes. 's Nachts onthoofdde zij hem, waarna de vijandelijke troepen op de vlucht sloegen. Breda kent een groot aantal andere oorlogsmonumenten, zoals De Vlucht in het Valkenberg, de tank en het Poolse monument in het Wilhelminapark, de Poolse kapel aan de Claudius Prinsenlaan, de Sint-Joostkapel aan de Ginnekenstraat, meerdere bevrijdingsmonumenten en bevrijdingskapellen in de dorpen en een aantal plaquettes ter herinnering aan personen.",
                "Bevrijdingsmonument \n The statue of Judith, the memorial to the fallen soldiers of Breda during the Second World War on the Grote Markt, was unveiled on 24 June 1952. It was made by Niel Steenbergen (1911-1997). It is the best known of the many peace memorials of his hand. This Jewish woman (according to the Apocrypha Bible Book) managed to penetrate the list of the Assyrian commander Holofernes' army tent. At night she beheaded him, after which the enemy troops fled. Breda has a large number of other war monuments, such as De Vlucht in the Valkenberg, the tank and the Polish monument in the Wilhelminapark, the Polish chapel on the Claudius Prinsenlaan, the Sint-Joost chapel on the Ginnekenstraat, several liberation monuments and liberation chapels in the villages and a number of plaques to remind people.", 2));
        databaseManager.addData(new Database(51.588750f, 4.776112f, "Stadhuis \n Het stadhuis van Breda staat op de Grote Markt in het centrum van Breda. Dit historische gebouw heeft een trap aan de voorkant. Hier vinden trouwerijen van de Bredase burgers plaats en ontvangsten van de burgemeester van Breda. Ernaast ligt een klein straatje met poort om aan de achterzijde van het stadhuis te komen.\n" +
                "\n" +
                "Het stadhuis heeft een stenen bordestrap met op de balustrade, leeuwen die de wapens van Breda en Brabant dragen. In het stadhuis is een hal en een trappenhuis.",
                "Stadhuis \n The city hall of Breda is on the Grote Markt in the center of Breda. This historic building has a staircase at the front. This is where weddings of Breda citizens take place and receipts from the mayor of Breda. Next to it is a small street with a gate to get to the rear of the town hall.\n" +
                        "\n" +
                        "The town hall has a stone steps with on the balustrade lions bearing the arms of Breda and Brabant. In the town hall is a hall and a stairwell.", 2));
        databaseManager.addData(new Database(51.587638f, 4.777250f, "Antonius van Paduakerk \n De Antonius van Paduakerk[1] is een rooms-katholieke kerk aan de Groesbeekseweg in Nijmegen. Het neogotische gebouw werd in 1916-1917 opgetrokken naar een ontwerp van de Rotterdamse architect Jos Margry. De kerk is toegewijd aan de heilige Antonius van Padua. De bouw werd ondersteund door de Kerkbouw-stichting, die tot doel had kerken te bouwen die gewijd werden aan deze heilige.",
                "Antonius van Paduakerk \n The Antonius van Padua church [1] is a Roman Catholic church on the Groesbeekseweg in Nijmegen. The neo-gothic building was erected in 1916-1917 to a design by the Rotterdam architect Jos Margry. The church is dedicated to St. Anthony of Padua. The construction was supported by the Kerkbouw foundation, whose purpose was to build churches dedicated to this saint.", 2));

    }

}
