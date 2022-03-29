package uk.ac.ucl.model;

import uk.ac.ucl.Config;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    public Note getNote(String id) {
        return notes.stream().filter(note -> note.getId().equals(id)).findFirst().orElse(null);
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
        String kw = keyword.toLowerCase();
        return notes.stream()
                .filter(note -> (note.getTitle().toLowerCase().contains(kw)
//                        || (note instanceof TextNote && ((TextNote) note).getContent().get("text").contains(keyword)
//                        || (note instanceof URLNote && note.getUrl().toString().contains(keyword)
                        || (note.getContent().containsKey("text") && ((String) note.getContent().get("text")).toLowerCase().contains(kw))
                        || (note.getContent().containsKey("url") && ((URL) note.getContent().get("url")).toString().toLowerCase().contains(kw))
                        || note.getLabel().toLowerCase().contains(kw)))
                .collect(Collectors.toList());
    }
}
