package uk.ac.ucl.servlets;

import uk.ac.ucl.model.*;
import uk.ac.ucl.model.util.NoteSaver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@MultipartConfig
@WebServlet(name = "edit", urlPatterns = {"/edit/*"})
public class UpdateServlet extends HttpServlet {
    private static Byte[] toObject(byte[] bytesPrims) {
        Byte[] bytesObjects = new Byte[bytesPrims.length];
        Arrays.setAll(bytesObjects, n -> bytesPrims[n]);
        return bytesObjects;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String param = request.getPathInfo();
        String id = param.substring(1);
        Model model = ModelFactory.getModel();
        List<Note> notes = model.getNotes();
        if (notes != null) {
            Note note = model.getNote(id);
            request.setAttribute("note", note);
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/edit.jsp");
        dispatch.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String label = request.getParameter("label");
        String text = request.getParameter("text");
        String hasOriginal = request.getParameter("hasOriginal");
        Boolean hasOriginalImage = Boolean.parseBoolean(hasOriginal);
        Part imagePart = request.getPart("image");
        URL url = null;
        if (!request.getParameter("url").isEmpty()) {
            url = new URL(request.getParameter("url"));
        }

        Byte[] imageByteArray = null;
        if (imagePart != null) {
            String contentType = imagePart.getContentType();
            Pattern pattern = Pattern.compile("^image/.*$");
            if (pattern.matcher(contentType).find()) {
//                file type is image
                InputStream imageContent = imagePart.getInputStream();
                imageByteArray = toObject(imageContent.readAllBytes());
            } else {
                imagePart = null;
            }
        }


        Object content = null;
//        create a map for contents
        Map<String, Object> c = new HashMap<>();
        if (!text.isEmpty()) {
            c.put("text", text);
        }
        if (imageByteArray != null) {
            c.put("image", imageByteArray);
        }else if (hasOriginalImage) {
            Model model = ModelFactory.getModel();
            List<Note> notes = model.getNotes();
            if (notes != null) {
                Note originalNote = model.getNote(id);
                c.put("image", originalNote.getContent().get("image"));
            }
        }
        if (url != null) {
            c.put("url", url);
        }

        Note note = null;

        if (c.size() > 1) {
//            composite note
            CompositeNote cnote = new CompositeNote();
            for (Map.Entry entry : c.entrySet()) {
                if (entry.getKey() == "text") {
                    Note tnote = new TextNote();
                    cnote.add(tnote);
                } else if (entry.getKey() == "image") {
                    Note inote = new ImageNote();
                    cnote.add(inote);
                } else if (entry.getKey() == "url") {
                    Note unote = new URLNote();
                    cnote.add(unote);
                }
            }
            content = c;
            note = cnote;
        } else if (c.size() == 1) {
//            leaf note
            if (c.containsKey("text")){
                note = new TextNote();
                content = text;
            }else if (c.containsKey("image") && imageByteArray != null){
                note = new ImageNote();
                content = imageByteArray;
            }else if (hasOriginalImage){
                note = new ImageNote();
                Model model = ModelFactory.getModel();
                List<Note> notes = model.getNotes();
                if (notes != null) {
                    Note originalNote = model.getNote(id);
                    content = originalNote.getContent().get("image");
                }
            }else if (c.containsKey("url")){
                note = new URLNote();
                content = url;
            }
        }
        if (note != null)
            note.setId(id);
        if (new NoteSaver(note).save(title, label, content)){
            response.sendRedirect("/");
        }else{
            request.setAttribute("errors", "an error occurred when saving the note");

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/edit.jsp");
            dispatch.forward(request, response);
            response.sendRedirect("/edit/" + id);
        }
    }
}
