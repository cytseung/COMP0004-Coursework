package uk.ac.ucl.model.util;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
            Model model = ModelFactory.getModel();
            if (note.getId().isEmpty()) {
//            create operation
                note.setId(UUID.randomUUID().toString());
                note.setCreatedAt(LocalDateTime.now());
                List<Note> notes = model.getNotes();
                notes.add(note);
                model.setNotes(notes);
            } else {
//                edit operation
                List<Note> notes = model.getNotes();
//                replace existing with new note
                Note existing = model.getNote(note.getId());
                note.setCreatedAt(existing.getCreatedAt());
                note.setUpdatedAt(LocalDateTime.now());
                notes.remove(model.getNote(note.getId()));
                notes.add(note);
                model.setNotes(notes);
            }
            model.save();
            return true;


        } catch (Exception e) {
            return false;
        }
    }
}
