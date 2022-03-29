package uk.ac.ucl.servlets;

import uk.ac.ucl.model.*;

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
import java.util.*;
import java.util.regex.Pattern;

@MultipartConfig
@WebServlet(name = "create", urlPatterns = {"/create/*"})
public class CreateServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/create.jsp");
        dispatch.forward(request, response);
    }

    private static Byte[] toObject(byte[] bytesPrims) {
        Byte[] bytesObjects = new Byte[bytesPrims.length];
        Arrays.setAll(bytesObjects, n -> bytesPrims[n]);
        return bytesObjects;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("here");
        String title = request.getParameter("title");
        String label = request.getParameter("label");
        String text = request.getParameter("text");
        Part imagePart = request.getPart("image");
        URL url = new URL(request.getParameter("url"));
        Byte[] imageByteArray = null;
        if (imagePart != null) {
            System.out.println(imagePart.getContentType());
            System.out.println(imagePart.getSubmittedFileName());
            String contentType = imagePart.getContentType();
            Pattern pattern = Pattern.compile("^image/.*$");
            System.out.println(pattern.matcher("image/jpeg").find());
            if (pattern.matcher(contentType).find()) {
//                file type is image
                System.out.println("contains an image");
                InputStream imageContent = imagePart.getInputStream();
                imageByteArray = toObject(imageContent.readAllBytes());
            } else {
                System.out.println("does not contain an image");
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
            }else if (c.containsKey("image")){
                note = new ImageNote();
                content = imageByteArray;
            }else if (c.containsKey("url")){
                note = new URLNote();
                content = url;
            }
        }



//        Object content = null;
//        System.out.println(text == null);
//        if (!text.isEmpty()) {
//            System.out.println(1);
//            note = new TextNote();
//            content = text;
//        }
//        if (imagePart != null) {
//            System.out.println(2);
//            if (note == null) {
//                System.out.println(3);
//                note = new ImageNote();
//                content = imageByteArray;
//            } else {
////                text + image note
//                System.out.println(4);
//                Map<String, Object> c = new HashMap<>();
//                Note imageNote = new ImageNote();
//                CompositeNote cnote = new CompositeNote();
//                cnote.add(note, imageNote);
//                c.put("text", text);
//                c.put("image", imageByteArray);
//                note = cnote;
//                content = c;
//            }
//        }


        if (new NoteSaver(note).save(title, label, content)) {
            response.sendRedirect("/");
        } else {
            request.setAttribute("errors", "an error occurred when saving the note");

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/create.jsp");
            dispatch.forward(request, response);
            response.sendRedirect("/create");
        }
    }


}
