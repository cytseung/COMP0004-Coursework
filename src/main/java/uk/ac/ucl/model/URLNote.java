package uk.ac.ucl.model;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class URLNote extends AbstractNote<URL>{
    @Override
    public Map<String, URL> getContent(){
        Map<String, URL> content_d = new HashMap<>();
        content_d.put("url", content);
        return content_d;
    }
}
