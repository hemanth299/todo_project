package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Todo {
    private int id;
    private String roll_no;
    private String title;
    private String description;
    private Date lastDate;  
    private String status;
    private Timestamp createdAt; // ✅ Changed to Timestamp
	

    // Default Constructor
    public Todo() {}

    // Constructor for retrieving from DB
    public Todo(int id,String roll_no, String title, String description, Date lastDate, String status, Timestamp createdAt) {
        this.id = id;
        this.roll_no = roll_no;
        this.title = title;
        this.description = description;
        this.lastDate = lastDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Constructor for inserting into DB (ID & createdAt auto-generated)
    public Todo(String roll_no,String title, String description, Date lastDate, String status) {
        this.title = title;
        this.roll_no = roll_no;
        this.description = description;
        this.lastDate = lastDate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getRoll_no() {return roll_no;}
    public void setRoll_no(String roll_no) { this.roll_no = roll_no;} 

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Date getLastDate() { return lastDate; }
    public void setLastDate(Date lastDate) { this.lastDate = lastDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    // Format lastDate for display
    public String getLastDateFormatted() {
        return (lastDate != null) ? lastDate.toString() : "N/A";
    }

    // Format createdAt for display
    public String getCreatedAtFormatted() {
        return (createdAt != null) ? createdAt.toString() : "N/A";
    }
}
