package uk.ac.ucl.model.util;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

import java.util.List;

public class NoteDeleter {
    private Note note;

    public NoteDeleter(Note note) {
        this.note = note;
    }

    public boolean delete() {
        try {
            Model model = ModelFactory.getModel();
            List<Note> notes = model.getNotes();
            notes.remove(note);
            model.setNotes(notes);
            model.save();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
