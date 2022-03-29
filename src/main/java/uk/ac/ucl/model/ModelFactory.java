package uk.ac.ucl.model;

import uk.ac.ucl.Config;

import java.io.File;
import java.io.IOException;

public class ModelFactory {
    private static Model model;
    public static Model getModel() throws IOException {
        if (model == null){
            model = new Model();
            model.readFile(new File(Config.datapath));
        }
        return model;
    }
}
