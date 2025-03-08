package myservlet_pack;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DbUtil;
import dao.TodoDao;

@WebServlet("/delete")
public class DeleteTodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Validate and parse the task ID
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid task ID.");
            }

            int id = Integer.parseInt(idParam);

            // Get database connection & delete the task
            Connection conn = DbUtil.getConnection();
            TodoDao dao = new TodoDao(conn);
            dao.deleteTodo(id);

            // Redirect back to the task list after deletion
            response.sendRedirect("list");

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid task ID format.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to delete task: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
