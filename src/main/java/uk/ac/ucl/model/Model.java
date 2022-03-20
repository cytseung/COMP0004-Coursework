package uk.ac.ucl.model;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Model {
    // The example code in this class should be replaced by your Model class code.
    // The data should be stored in a suitable data structure.
    private List<Note> notes;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Optional<Note> getNote(int id) {
        return notes.stream().filter(note -> note.getId() == id).findFirst();
    }

    public void readFile(File file) throws FileNotFoundException {
        // Read a data file and store the data in your data structure.

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            notes = (List<Note>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveNotes(File file) {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Note> searchFor(String keyword) {
        return notes.stream()
                .filter(note -> (note.getTitle().contains(keyword)
                        || note.getText().contains(keyword)
                        || note.getUrl().toString().contains(keyword)))
                .collect(Collectors.toList());
    }
}
