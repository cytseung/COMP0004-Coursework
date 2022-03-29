package uk.ac.ucl.model;

import javax.swing.*;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class TextNote extends AbstractNote<String> {
    @Override
    public Map<String, String> getContent(){
        Map<String, String> content_d = new HashMap<>();
        content_d.put("text", content);
        return content_d;
    }
}
