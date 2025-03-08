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
import mypac.MyUtil;  // ✅ Import MyUtil for date conversion

@WebServlet("/update")
public class UpdateTodoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Validate and parse the task ID
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid task ID.");
            }
            int id = Integer.parseInt(idParam);

            // Retrieve updated task details
            String roll_no = request.getParameter("roll_no");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String lastDateStr = request.getParameter("last_date");
            String status = request.getParameter("status");

            // Validate inputs (optional: add more robust validation)
            if (title == null || title.trim().isEmpty() ||
                description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("Title and description cannot be empty.");
            }

            // Convert last_date using MyUtil (handles null safely)
            Date lastDate = MyUtil.parseDate(lastDateStr);

            if (status == null || status.trim().isEmpty()) {
                status = "Pending"; // Default value
            }

            // Create a Todo object
            Todo updatedTodo = new Todo(id,roll_no, title, description, lastDate, status, null); // ✅ Null for createdAt

            // Update the task in the database
            Connection conn = DbUtil.getConnection();
            TodoDao dao = new TodoDao(conn);
            boolean success = dao.updateTodo(updatedTodo);

            if (success) {
                response.sendRedirect("list"); // ✅ Redirect to list page
            } else {
                throw new Exception("Task update failed. Task may not exist.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid task ID format.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Failed to update task: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
