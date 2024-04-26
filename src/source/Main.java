package source;

import source.model.ImplementedShapeModel;
import source.userInterface.GUI;
import source.model.ShapeModel;

public class Main {
    public static void main(String[] args) {
        ShapeModel model = new ImplementedShapeModel();
        GUI gui = new GUI(model);
        gui.start();
    }
}
