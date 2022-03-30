package uk.ac.ucl.model.util;


import uk.ac.ucl.model.Note;

import java.util.Comparator;

public class NoteSorter {
    public static class sortByCreatedAt implements Comparator<Note> {
        public int compare(Note a, Note b) {
            return a.getCreatedAt().compareTo(b.getCreatedAt());
        }
    }
    public static class sortByTitle implements Comparator<Note>{
        public int compare(Note a, Note b){
            return a.getTitle().compareToIgnoreCase(b.getTitle());
        }
    }
}
