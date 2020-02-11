package allapotter;

import java.text.DecimalFormat;

public class Allapot {

    //0 - üres
    //1 - fal
    //2 - doboz
    //3 - cél
    //5 - doboz a célon
    //4 - játékos
    //7 - játékos célon

    public int[][] allapot_tomb;

    public int utoljara_mozgatott_lada_x;
    public int utoljara_mozgatott_lada_y;

    public TavolsagSzamito tavolsagSzamito = new TavolsagSzamito();

    public Allapot(int[][] allapot_tomb, int utoljara_mozgatott_lada_x, int utoljara_mozgatott_lada_y){
        //System.out.println(utoljara_mozgatott_lada_x+", "+utoljara_mozgatott_lada_y);
        this.allapot_tomb = allapot_tomb;
        this.utoljara_mozgatott_lada_x = utoljara_mozgatott_lada_x;
        this.utoljara_mozgatott_lada_y = utoljara_mozgatott_lada_y;
    }

    public int magassag() {
        return this.allapot_tomb.length;
    }

    public int szelesseg() {
        return this.allapot_tomb[0].length;
    }

    public double heurisztika(){
        int lada_a_celon = 0;
        for(int i = 0; i < allapot_tomb.length; i++){
            for(int j = 0; j < allapot_tomb[0].length; j++){
                if(allapot_tomb[i][j] == 5) lada_a_celon++;
            }
        }
        int temp = (int) (tavolsagSzamito.atlagTavolsagSzamol(this.allapot_tomb)*100);
        return (20 - (double) temp/100) * 2 + lada_a_celon;
    }

    //akkor van nyertes cél állapotban, ha nincsenek üres cél helyek és nem áll a játékos az egyik célhelyen
    public boolean nyertes_cel(){
        for(int i=0; i<magassag(); i++) {
            for (int j = 0; j < szelesseg(); j++) {
                if((allapot_tomb[i][j]==3) || (allapot_tomb[i][j]==7)) return false;
            }
        }
        return true;
    }

    public boolean vesztes_cel(){
        //ha nem mozgattunk dobozt nem jöhetett létre vesztes cél állapot
        if(utoljara_mozgatott_lada_x==-1 && utoljara_mozgatott_lada_y==-1) return false;
            //megnézzük, hogy a láda sarokba lépett-e ("sarok" pozicio vagy "4x4"-es pozicio)
            //ha "4x4" jött létre és a "4x4" bármelyik eleme 2-es értékű (nem "láda a célon") akkor vesztes célállapot jön létre
        else if(
                ((ladaFolott()==2 || ladaFolott()==1 || ladaFolott()==5) && (ladaJobbon()==2 || ladaJobbon()==1 || ladaJobbon()==5) && (ladaJFSarkan()==2 || ladaJFSarkan()==1 || ladaJFSarkan()==5) && (ladaFolott()==2 || ladaJobbon()==2 || ladaJFSarkan()==2 || ladaPoz()==2))
                        || ((ladaFolott()==2 || ladaFolott()==1 || ladaFolott()==5) && (ladaBalon()==2 || ladaBalon()==1 || ladaBalon()==5) && (ladaBFSarkan()==2 || ladaBFSarkan()==1 || ladaBFSarkan()==5) && (ladaFolott()==2 || ladaBalon()==2 || ladaBFSarkan()==2 || ladaPoz()==2))
                        || ((ladaAlatt()==2 || ladaAlatt()==1 || ladaAlatt()==5) && (ladaJobbon()==2 || ladaJobbon()==1 || ladaJobbon()==5) && (ladaJASarkan()==2 || ladaJASarkan()==1 || ladaJASarkan()==5) && (ladaAlatt()==2 || ladaJobbon()==2 || ladaJASarkan()==2 || ladaPoz()==2))
                        || ((ladaAlatt()==2 || ladaAlatt()==1 || ladaAlatt()==5) && (ladaBalon()==2 || ladaBalon()==1 || ladaBalon()==5) && (ladaBASarkan()==2 || ladaBASarkan()==1 || ladaBASarkan()==5) && (ladaAlatt()==2 || ladaBalon()==2 || ladaBASarkan()==2 || ladaPoz()==2))
        ) {
            System.out.println("SAROK VAGY 4x4");
            return true;
        }
            //megnézzük, hogy nem került-e a láda "C" pozícióba
        else {
            if(ladaJobbon()==1 && ladaPoz()!=5)
                if(jobbFelVeg() && jobbLeVeg()){System.out.println("JOBBVEG"); return true;}
            if(ladaBalon()==1 && ladaPoz()!=5)
                if(balFelVeg() && balLeVeg()){System.out.println("BALVEG"); return true;}
            if(ladaFolott()==1 && ladaPoz()!=5)
                if(felJobbVeg() && felBalVeg()){System.out.println("FELVEG"); return true;}
            if(ladaAlatt()==1 && ladaPoz()!=5)
                if(leJobbVeg() && leBalVeg()){System.out.println("LEVEG"); return true;}
        }

        return false;
    }

    public int ladaFolott(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x-1][utoljara_mozgatott_lada_y];
    }
    public int ladaAlatt(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x+1][utoljara_mozgatott_lada_y];
    }
    public int ladaJobbon(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y+1];
    }
    public int ladaBalon(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y-1];
    }
    public int ladaJFSarkan(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x-1][utoljara_mozgatott_lada_y+1];
    }
    public int ladaBFSarkan(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x-1][utoljara_mozgatott_lada_y-1];
    }
    public int ladaJASarkan(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x+1][utoljara_mozgatott_lada_y+1];
    }
    public int ladaBASarkan(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x+1][utoljara_mozgatott_lada_y-1];
    }
    public int ladaPoz(){
        return this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y];
    }

    public boolean jobbFelVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y + 1] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y + 1] == 1){
                return true;
            } else return false;
        }
    }
    public boolean jobbLeVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y + 1] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y + 1] == 1){
                return true;
            } else return false;
        }
    }
    public boolean balFelVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y - 1] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x - k][utoljara_mozgatott_lada_y - 1] == 1){
                return true;
            } else return false;
        }
    }
    public boolean balLeVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y - 1] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x + k][utoljara_mozgatott_lada_y - 1] == 1){
                return true;
            } else return false;
        }
    }
    public boolean felJobbVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y + k] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x - 1][utoljara_mozgatott_lada_y + k] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y + k] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x - 1][utoljara_mozgatott_lada_y + k] == 1){
                return true;
            } else return false;
        }
    }
    public boolean felBalVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y - k] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x - 1][utoljara_mozgatott_lada_y - k] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y - k] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x - 1][utoljara_mozgatott_lada_y - k] == 1){
                return true;
            } else return false;
        }
    }
    public boolean leJobbVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y + k] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x + 1][utoljara_mozgatott_lada_y + k] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y + k] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x + 1][utoljara_mozgatott_lada_y + k] == 1){
                return true;
            } else return false;
        }
    }
    public boolean leBalVeg(){
        int k=1;
        while(true){
            if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y - k] == 0 && this.allapot_tomb[utoljara_mozgatott_lada_x + 1][utoljara_mozgatott_lada_y - k] == 1)
                k++;
            else if(this.allapot_tomb[utoljara_mozgatott_lada_x][utoljara_mozgatott_lada_y - k] == 1 && this.allapot_tomb[utoljara_mozgatott_lada_x + 1][utoljara_mozgatott_lada_y - k] == 1){
                return true;
            } else return false;
        }
    }

    public void allapotKirajzol(){
        for(int i=0; i<this.allapot_tomb.length; i++){
            for(int j=0; j<this.allapot_tomb[0].length; j++){
                switch (this.allapot_tomb[i][j]){
                    case 0 : { System.out.print(" "); break; }
                    case 1 : { System.out.print("#"); break; }
                    case 2 : { System.out.print("X"); break; }
                    case 3 : { System.out.print("0"); break; }
                    case 4 : { System.out.print("@"); break; }
                    case 5 : { System.out.print("X"); break; }
                    case 7 : { System.out.print("@"); break; }
                }
            }
            System.out.println();
        }
        System.out.println("===============================");
    }

}