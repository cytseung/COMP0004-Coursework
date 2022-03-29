package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.util.NoteSorter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/")
public class ListServlet extends HttpServlet {
    //    private class sortByCreatedAt implements Comparator<Note>{
//        public int compare(Note a, Note b){
//            return a.getCreatedAt().compareTo(b.getCreatedAt());
//        }
//    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String sort = request.getParameter("sort");
        Model model = ModelFactory.getModel();
        List<Note> notes = model.getNotes();
        if (sort == null || sort.equals("created") || sort.isEmpty()) {
            Collections.sort(notes, new NoteSorter.sortByCreatedAt());
        } else {
            Collections.sort(notes, new NoteSorter.sortByTitle());
        }
        if (notes != null) {
            request.setAttribute("notes", notes);
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/list.jsp");
        dispatch.forward(request, response);
    }
}
