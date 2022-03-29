package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "detail", urlPatterns = {"/note/*"})
public class DetailServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String param = request.getPathInfo();
        String id = param.substring(1);
        System.out.println(id);
        Model model = ModelFactory.getModel();
        List<Note> notes = model.getNotes();
        System.out.println(notes);
        if (notes != null) {
//            Note note = notes.stream().filter(n -> (n.getId() == id)).findFirst().orElse(null);
            Note note = model.getNote(id);
            System.out.println(note.getClass());
            request.setAttribute("note", note);
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/detail.jsp");
        dispatch.forward(request, response);
    }
}
