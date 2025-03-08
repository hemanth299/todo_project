package dao;

import java.sql.*;
import java.util.*;
import model.Todo;

public class TodoDao {
    private Connection conn;

    // Constructor to initialize DAO with a connection
    public TodoDao(Connection conn) {
        this.conn = conn;
    }

    // Retrieve all todos
    public List<Todo> getAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todos ORDER BY created_at DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Todo todo = new Todo(
                    rs.getInt("id"),
                    rs.getString("roll_no"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDate("last_date"),
                    rs.getString("status"),
                    rs.getTimestamp("created_at") // ✅ Fetching created_at from DB
                );
                todos.add(todo);
            }
        }
        return todos;
    }

    // Retrieve a single todo by ID
    public Todo getTodoById(int id) throws SQLException {
        String sql = "SELECT * FROM todos WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Todo(
                        rs.getInt("id"),
                        rs.getString("roll_no"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("last_date"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at") // ✅ Fetching created_at
                    );
                }
            }
        }
        return null; // Return null if no todo is found
    }

    // Add a new todo
    public void addTodo(Todo todo) throws SQLException {
        String sql = "INSERT INTO todos (roll_no,title, description, last_date, status) VALUES (?,?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getRoll_no());
            ps.setString(3, todo.getDescription());

            // Convert java.util.Date to java.sql.Date
            if (todo.getLastDate() != null) {
                ps.setDate(4, new java.sql.Date(todo.getLastDate().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, todo.getStatus());
            ps.executeUpdate();
        }
    }

    // Delete a todo by ID
    public void deleteTodo(int id) throws SQLException {
        String sql = "DELETE FROM todos WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ✅ New: Update title, description, last date, and status
    public boolean updateTodo(Todo todo) throws SQLException {
        String sql = "UPDATE todos SET roll_no=?,title=?, description=?, last_date=?, status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, todo.getRoll_no());
            ps.setString(2, todo.getTitle());
            ps.setString(3, todo.getDescription());

            // Convert java.util.Date to java.sql.Date
            if (todo.getLastDate() != null) {
                ps.setDate(4, new java.sql.Date(todo.getLastDate().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, todo.getStatus());
            ps.setInt(6, todo.getId());

            return ps.executeUpdate() > 0;  // Returns true if update is successful
        }
    }
}
