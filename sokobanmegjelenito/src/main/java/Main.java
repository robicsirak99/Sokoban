import allapotter.Allapot;
import allapotter.Operator;
import beolvaso.PalyaBeolvaso;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import megjelenito.Megjelenito;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application{

    public static List<Operator> OPERATOROK = new ArrayList<Operator>();
    public Allapot jelenlegi_allapot;

    Megjelenito megjelenito;

    @Override
    public void start(Stage stage) throws Exception {
        beolvasEsElokeszit();

        this.megjelenito = new Megjelenito(jelenlegi_allapot);

        Scene scene = new Scene(megjelenito,600,400);
        stage.setScene(scene);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case UP : {lep(0);break;}
                    case DOWN : {lep(1);break;}
                    case RIGHT : {lep(2);break;}
                    case LEFT : {lep(3);break;}
                }
            }
        });
        stage.show();
    }

    public void lep(int i){
        if(OPERATOROK.get(i).alkalmazhato(this.jelenlegi_allapot)){
            this.jelenlegi_allapot=OPERATOROK.get(i).alkalmaz(jelenlegi_allapot);
            myLog("LÉPÉS: "+i);
            myLog("JELENLEGI ALLAPOT HEURISZTIKAJA: " + this.jelenlegi_allapot.heurisztika());

            megjelenito.ujraRajzol(this.jelenlegi_allapot);
            if (jelenlegi_allapot.nyertes_cel())
                myLog("NYERT");
            if (jelenlegi_allapot.vesztes_cel())
                myLog("VESZTETT");
        } else
            myLog("EZ AZ OPERATOR NEM ALKALMAZHATO EBBEN AZ ALLAPOTBAN");
    }

    public void beolvasEsElokeszit(){
        PalyaBeolvaso palyaBeolvaso = new PalyaBeolvaso();
        myLog("...PALYABEOLVASO INCIALIZALVA...");

        int szint = 3;
        String[] jelenlegi_palya = palyaBeolvaso.beolvasKovetkezoSzint(szint);
        myLog("...BEOLVASOTT PALYA: " + szint + " ...");

        int palya_szelesseg = palyaBeolvaso.palya_szelesseg;
        int palya_magassag = palyaBeolvaso.palya_magassag;

        Allapot kezdoAllapot = kezdoAllapotKeszit(jelenlegi_palya, palya_magassag, palya_szelesseg);
        myLog("...KEZDOALLAPOT ELOKESZITVE...");

        this.jelenlegi_allapot = kezdoAllapot;

        OPERATOROK.add(new Operator('f'));
        OPERATOROK.add(new Operator('l'));
        OPERATOROK.add(new Operator('j'));
        OPERATOROK.add(new Operator('b'));
        myLog("...OPERATOROK HOZZAADVA...");
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void myLog(String string){
        System.out.println(string);
    }

    public static Allapot kezdoAllapotKeszit(String[] jelenlegi_palya,int magassag, int szelesseg){
        int[][] allapot_tomb = new int[magassag][szelesseg];
        for(int i=0; i<jelenlegi_palya.length; i++){
            for(int j=0; j<jelenlegi_palya[i].length(); j++){
                switch (jelenlegi_palya[i].charAt(j)) {
                    case '-' :{ allapot_tomb[i][j]=0; break; }
                    case '#' :{ allapot_tomb[i][j]=1; break; }
                    case '$' :{ allapot_tomb[i][j]=2; break; }
                    case '.' :{ allapot_tomb[i][j]=3; break; }
                    case '@' :{ allapot_tomb[i][j]=4; break; }
                }
            }
        }
        return new Allapot(allapot_tomb,-1,-1);
    }

    public static void allapotKirajzol(Allapot allapot){
        System.out.println("===============================");
        for(int i=0; i<allapot.allapot_tomb.length; i++){
            for(int j=0; j<allapot.allapot_tomb[i].length; j++){
                System.out.print(allapot.allapot_tomb[i][j]);
            }
            System.out.println();
        }
    }
}
