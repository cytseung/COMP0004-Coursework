package uk.ac.ucl.model;

import uk.ac.ucl.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Model {
    // The example code in this class should be replaced by your Model class code.
    // The data should be stored in a suitable data structure.
    private List<Note> notes = new ArrayList<>();

    private void saveNotes(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Optional<Note> getNote(int id) {
        return notes.stream().filter(note -> note.getId() == id).findFirst();
    }

    public void readFile(File file) throws IOException {
        // Read a data file and store the data in your data structure.
        file.createNewFile();
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            notes = (List<Note>) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException {
        File f = new File(Config.datapath);
        f.createNewFile();
        saveNotes(f);
    }

    public List<Note> searchFor(String keyword) {
        return notes.stream()
                .filter(note -> (note.getTitle().contains(keyword)
//                        || note.getText().contains(keyword)
//                        || note.getUrl().toString().contains(keyword)
                        || note.getLabel().contains(keyword)))
                .collect(Collectors.toList());
    }
}
