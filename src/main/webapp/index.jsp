<%@ page import="java.util.List" %>
<%@ page import="model.Todo" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>To-Do List</title>
    
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- FontAwesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Professional Styling -->
    <style>
        /* Dark Theme Background */
        body {
            background-color: #f4f4f9;
            font-family: 'Poppins', sans-serif;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        /* Card Style Container */
        .container {
            max-width: 1100px;
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
        }

        /* Subtle Input and Form Styling */
        .form-control, .form-select {
            border-radius: 8px;
            border: 1px solid #ccc;
        }

        /* Buttons */
        .btn {
            border-radius: 8px;
            transition: all 0.2s ease-in-out;
        }

        .btn-primary {
            background-color: #0056b3;
            border: none;
        }

        .btn-primary:hover {
            background-color: #003d80;
        }

        .btn-warning {
            background-color: #ffc107;
            border: none;
        }

        .btn-warning:hover {
            background-color: #e0a800;
        }

        .btn-danger {
            background-color: #dc3545;
            border: none;
        }

        .btn-danger:hover {
            background-color: #c82333;
        }

        /* Table Styling */
        .table {
            background: #ffffff;
            border-radius: 10px;
            overflow: hidden;
        }

        thead {
            background-color: #343a40;
            color: white;
        }

        tbody tr:hover {
            background-color: #f8f9fa;
            transition: 0.3s ease-in-out;
        }

        /* Modal Styling */
        .modal-content {
            border-radius: 12px;
        }

        .modal-header {
            background-color: #343a40;
            color: white;
            border-top-left-radius: 12px;
            border-top-right-radius: 12px;
        }
    </style>

    <script>
        function showUpdateForm(id, roll_no, title, description, lastDate, status) {
            document.getElementById("updateId").value = id;
            document.getElementById("updateRoll_no").value = roll_no;
            document.getElementById("updateTitle").value = title;
            document.getElementById("updateDescription").value = description;
            document.getElementById("updateLastDate").value = new Date(lastDate).toISOString().split("T")[0];
            document.getElementById("updateStatus").value = status;
            var updateModal = new bootstrap.Modal(document.getElementById("updateModal"));
            updateModal.show();
        }

        function validateRollNo(input) {
            const defaultPrefix = "22951A";
            const requiredLength = 10;
            const rollNo = input.value.trim();

            if (!rollNo.startsWith(defaultPrefix) || rollNo.length !== requiredLength) {
                alert("Invalid Roll No! It should start with '22951A' and be exactly 10 characters.");
                input.value = "";
            }
        }
    </script>
</head>
<body>

    <div class="container">
        <h2 class="text-center text-dark">🗒️ To-Do List</h2>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-danger"><%= errorMessage %></div>
        <% } %>

        <% List<?> rawTodos = (List<?>) request.getAttribute("todos"); %>

        <!-- Add Task Form -->
        <form action="add" method="post" class="mb-4 p-3 border rounded bg-light shadow-sm">
            <div class="row g-2">
                <div class="col-md-3">
                    <input type="text" name="roll_no" class="form-control" placeholder="Enter Roll No." required onblur="validateRollNo(this)">
                </div>
                <div class="col-md-3">
                    <input type="text" name="title" class="form-control" placeholder="Enter Task Title" required>
                </div>
                <div class="col-md-3">
                    <input type="text" name="description" class="form-control" placeholder="Enter Description">
                </div>
                <div class="col-md-2">
                    <input type="date" name="last_date" class="form-control">
                </div>
                <div class="col-md-1">
                    <button type="submit" class="btn btn-primary w-100"><i class="fas fa-plus"></i></button>
                </div>
            </div>
        </form>

        <!-- To-Do List Table -->
        <table class="table table-striped text-center">
            <thead>
                <tr>
                    <th>Roll</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Last Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% if (rawTodos != null && !rawTodos.isEmpty()) { 
                    for (Object obj : rawTodos) { 
                        if (obj instanceof Todo) {
                            Todo todo = (Todo) obj; %>
                    <tr>
                        <td><%= todo.getRoll_no() %></td>
                        <td><%= todo.getTitle() %></td>
                        <td><%= todo.getDescription() %></td>
                        <td><%= todo.getLastDateFormatted() %></td>
                        <td>
                            <span class="badge <%= todo.getStatus().equals("Completed") ? "bg-success" : "bg-warning text-dark" %>">
                                <%= todo.getStatus() %>
                            </span>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-warning" 
                                onclick="showUpdateForm('<%= todo.getId() %>', '<%= todo.getRoll_no() %>', '<%= todo.getTitle() %>', '<%= todo.getDescription() %>', '<%= todo.getLastDateFormatted() %>', '<%= todo.getStatus() %>')">
                                <i class="fas fa-edit"></i>
                            </button>
                            <a href="delete?id=<%= todo.getId() %>" class="btn btn-sm btn-danger">
                                <i class="fas fa-trash-alt"></i>
                            </a>
                        </td>
                    </tr>
                <% } } } else { %>
                    <tr>
                        <td colspan="6" class="text-center">No tasks found.</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <!-- Modal for Update Form -->
    <div class="modal fade" id="updateModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Update Task</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form action="update" method="post">
                        <input type="hidden" name="id" id="updateId">
                        <input type="text" name="roll_no" id="updateRoll_no" class="form-control mb-2" required onblur="validateRollNo(this)">
                        <input type="text" name="title" id="updateTitle" class="form-control mb-2" required>
                        <input type="text" name="description" id="updateDescription" class="form-control mb-2">
                        <input type="date" name="last_date" id="updateLastDate" class="form-control mb-2">
                        <button type="submit" class="btn btn-success w-100">Save Changes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
