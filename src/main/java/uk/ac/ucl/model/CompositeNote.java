package uk.ac.ucl.model;

import java.util.*;

public class CompositeNote extends AbstractNote<Map<String, Object>> {
    private List<Note> children = new ArrayList<>();

    public CompositeNote(Note... notes) {
        super();
        add(notes);
    }

    public void add(Note note) {
        children.add(note);
    }

    public void add(Note... notes) {
        children.addAll(Arrays.asList(notes));
    }

    public void remove(Note note) {
        children.remove(note);
    }

    public void remove(Note... notes) {
        children.removeAll(Arrays.asList(notes));
    }

    public List<Note> getChildren() {
        return children;
    }

    @Override
    public Map getContent() {
        Map contents = new HashMap();
        for (Note n : children) {
            contents.putAll(n.getContent());
        }
        return contents;
    }

    //    public void setContent(Map<String, Object> content) {
//        System.out.println("hey");
//        for (Note n : children) {
//            for (Map.Entry entry : content.entrySet()) {
//                if (entry.getKey() == "text")
//                    n.setContent(entry.getValue());
//                else if (entry.getKey() == "image")
//                    n.setContent(entry.getValue());
//            }
//
//        }
//    }
    @Override
    public void setContent(Map<String, Object> content) {
        for (Note n : children) {
            for (Map.Entry entry : content.entrySet()) {
                if (entry.getKey() == "text" && n instanceof TextNote) {
                    n.setContent(entry.getValue());
                } else if (entry.getKey() == "image" && n instanceof ImageNote) {
                    n.setContent(entry.getValue());
                } else if (entry.getKey() == "url" && n instanceof URLNote) {
                    n.setContent(entry.getValue());
                }
            }
        }
    }
}
