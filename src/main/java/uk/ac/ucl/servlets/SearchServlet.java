package uk.ac.ucl.servlets;

import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.NoteSorter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "search", urlPatterns = {"/search/*"})
public class SearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/search.jsp");
        dispatch.forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String keyword = request.getParameter("search");
        String sort = request.getParameter("sort");
        String mode = request.getParameter("mode");
        Model model = ModelFactory.getModel();
        List<Note> notes = model.searchFor(keyword);
        if (sort == null || sort.equals("created") || sort.isEmpty()) {
            Collections.sort(notes, new NoteSorter.sortByCreatedAt());
        } else {
            Collections.sort(notes, new NoteSorter.sortByTitle());
        }
        if (mode != null && mode.equals("summary")){
            request.setAttribute("summary", true);
        }else if (mode != null && mode.equals("full")){
            request.setAttribute("full", true);
        }
        if (notes != null) {
            request.setAttribute("notes", notes);
        }
        ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/search.jsp");
        dispatch.forward(request, response);
    }
}
