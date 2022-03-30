package uk.ac.ucl.servlets;



import uk.ac.ucl.model.Model;
import uk.ac.ucl.model.ModelFactory;
import uk.ac.ucl.model.Note;
import uk.ac.ucl.model.util.NoteDeleter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "delete", urlPatterns = {"/delete/*"})
public class DeleteServlet extends HttpServlet {


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String id = request.getParameter("id");
        Model model = ModelFactory.getModel();
        Note note = model.getNote(id);


        if (new NoteDeleter(note).delete()){
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
