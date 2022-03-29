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

    private static Byte[] toObject(byte[] bytesPrim){
        Byte[] bytesObjects = new Byte[bytesPrim.length];
        Arrays.setAll(bytesObjects, n -> bytesPrim[n]);
        return bytesObjects;
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("here");
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        String label = request.getParameter("label");
        Part imagePart = request.getPart("image");
        Byte[] imageByteArray = null;
        if (imagePart != null) {
            System.out.println(imagePart.getContentType());
            System.out.println(imagePart.getSubmittedFileName());
            String contentType = imagePart.getContentType();
            Collection<String> headerNames = imagePart.getHeaderNames();
            System.out.println(headerNames);
            String value1 = imagePart.getHeader("content-disposition");
            String value2 = imagePart.getHeader("content-type");
            System.out.println(value1);
            System.out.println(value2);
            Pattern pattern = Pattern.compile("^image/.*$");
            System.out.println(pattern.matcher("image/jpeg").find());
            if (pattern.matcher(contentType).find()) {
//                file type is image
                System.out.println("contains an image");
                InputStream imageContent = imagePart.getInputStream();
//                byte[] encoded = Base64.getEncoder().encode(imageContent.readAllBytes());
                imageByteArray =  toObject(imageContent.readAllBytes());
            } else {
                System.out.println("does not contain an image");
                imagePart = null;
            }
//            File f = new File(Config.imagepath);
//            f.createNewFile();
//            imagePart.write(Config.imagepath);
//            InputStream imageContent = imagePart.getInputStream();
//            imageByteArray = imageContent.readAllBytes();
//            System.out.println(imageByteArray);
//            BufferedImage newImage = ImageIO.read(imageContent);
//            System.out.println(newImage == null);
//            ImageIO.write(newImage, "jpg", new File(Config.imagepath));
        }
        Note note = null;
        Object content = null;
        System.out.println(text == null);
        if (!text.isEmpty()) {
            System.out.println(text);
            System.out.println(1);
            note = new TextNote();
            content = text;
        }
        if (imagePart != null) {
            System.out.println(2);
            if (note == null) {
                System.out.println(3);
                note = new ImageNote();
                content = imageByteArray;
            } else {
//                text + image note
                System.out.println(4);
                Map<String, Object> c = new HashMap<>();
                Note imageNote = new ImageNote();
                CompositeNote cnote = new CompositeNote();
                cnote.add(note, imageNote);
                c.put("text", text);
                c.put("image", imageByteArray);
                content = c;
            }
        }


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
