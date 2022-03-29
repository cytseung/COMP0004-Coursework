package uk.ac.ucl.model;

import java.time.LocalDateTime;
import java.util.List;

public class NoteSaver {
    private Note note;

    public NoteSaver(Note note) {
        this.note = note;
    }

    public boolean save(String title, String label, Object content) {
        try {
            note.setTitle(title);
            note.setContent(content);
            note.setLabel(label);
            note.setCreatedAt(LocalDateTime.now());
            Model model = ModelFactory.getModel();
            List<Note> notes = model.getNotes();
            note.setId(notes.size() + 1);
            notes.add(note);
            model.setNotes(notes);
            model.save();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
