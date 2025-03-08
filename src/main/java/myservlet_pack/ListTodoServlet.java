package myservlet_pack;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DbUtil;
import dao.TodoDao;
import model.Todo;

@WebServlet("/list")
public class ListTodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get database connection & fetch tasks
            Connection conn = DbUtil.getConnection();
            TodoDao dao = new TodoDao(conn);
            List<Todo> todos = dao.getAllTodos();

            // Set attributes for JSP
            request.setAttribute("todos", todos);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching tasks: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
