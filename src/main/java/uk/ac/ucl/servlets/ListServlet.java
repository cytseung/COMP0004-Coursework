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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/list.html")
public class ListServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Model model = ModelFactory.getModel();
        List<Note> dummy = new ArrayList<>();
        Note n1 = new Note();
        n1.setTitle("12345");
        dummy.add(n1);
        model.setNotes(dummy);
        File f = new File("./data/notes");
        f.createNewFile();
        model.saveNotes(f);
        model.readFile(f);
        List<Note> notes = model.getNotes();
        if (notes != null)
            request.setAttribute("notes", notes);



        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/list.jsp");
        dispatch.forward(request, response);
    }
}
