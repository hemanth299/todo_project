package myservlet_pack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DbUtil;
import dao.TodoDao;
import model.Todo;
import mypac.MyUtil;

@WebServlet("/add")
public class AddTodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve form parameters
        	String roll_no = request.getParameter("roll_no");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String dateStr = request.getParameter("last_date");

            // Convert date using MyUtil (handles null values safely)
            Date lastDate = MyUtil.parseDate(dateStr);

            // Create a Todo object (ID is auto-generated in DB)
            Todo todo = new Todo(roll_no,title, description, lastDate, "Pending");

            // Use a single database connection
            Connection conn = DbUtil.getConnection();
            TodoDao dao = new TodoDao(conn);
            dao.addTodo(todo);

            // Redirect to list page after successful addition
            response.sendRedirect("list");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to add task: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
