package uk.ac.ucl.model;

import javax.swing.*;

import java.io.Serializable;
import java.net.URL;

//POJO
public class Note implements Serializable {
    static private int counter = 1;
    private int id = counter++;
    private String title;
    private String label;
    private String text;
    private URL url;
    private ImageIcon image;
    private byte[] data;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getText() {
        return text;
    }

    public URL getUrl() {
        return url;
    }

    public ImageIcon getImage() {
        return image;
    }

    public byte[] getData() {
        return data;
    }
    public String getTitle() {
        return title;
    }


    public void setText(String text) {
        this.text = text;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString(){
        return "Note " + id + ": " + title;
    }




}
