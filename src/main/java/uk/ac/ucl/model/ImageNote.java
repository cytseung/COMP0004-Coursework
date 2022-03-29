package uk.ac.ucl.model;


import java.util.HashMap;
import java.util.Map;

public class ImageNote extends AbstractNote<Byte[]>{
    @Override
    public Map<String, Byte[]> getContent(){
        Map<String, Byte[]> content_d = new HashMap<>();
        content_d.put("image", content);
        return content_d;
    }
}
