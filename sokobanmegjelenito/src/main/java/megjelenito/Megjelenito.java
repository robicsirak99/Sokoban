package megjelenito;

import allapotter.Allapot;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Megjelenito extends Pane {

    public Megjelenito(Allapot allapot){
        for(int i=0; i<allapot.magassag(); i++){
            for(int j=0; j<allapot.szelesseg(); j++){
                Rectangle rectangle = new Rectangle(30, 30);
                switch (allapot.allapot_tomb[i][j]){
                    case 0: { rectangle.setFill(Color.WHITE); break; }
                    case 1: { rectangle.setFill(Color.GRAY); break; }
                    case 2: { rectangle.setFill(Color.BROWN); break; }
                    case 3: { rectangle.setFill(Color.GREEN); break; }
                    case 4: { rectangle.setFill(Color.BLUE); break; }
                    case 5: { rectangle.setFill(Color.BROWN); break; }
                    case 7: { rectangle.setFill(Color.BLUE); break; }
                }
                rectangle.setLayoutX(j*30);
                rectangle.setLayoutY(i*30);
                getChildren().add(rectangle);
            }
        }
    }

    public void ujraRajzol(Allapot allapot){
        for(int i=0; i<allapot.magassag(); i++){
            for(int j=0; j<allapot.szelesseg(); j++){
                Rectangle rectangle = new Rectangle(30, 30);
                switch (allapot.allapot_tomb[i][j]){
                    case 0: { rectangle.setFill(Color.WHITE); break; }
                    case 1: { rectangle.setFill(Color.GRAY); break; }
                    case 2: { rectangle.setFill(Color.BROWN); break; }
                    case 3: { rectangle.setFill(Color.GREEN); break; }
                    case 4: { rectangle.setFill(Color.BLUE); break; }
                    case 5: { rectangle.setFill(Color.BROWN); break; }
                    case 7: { rectangle.setFill(Color.BLUE); break; }
                }
                rectangle.setLayoutX(j*30);
                rectangle.setLayoutY(i*30);
                getChildren().add(rectangle);
            }
        }
    }

}
