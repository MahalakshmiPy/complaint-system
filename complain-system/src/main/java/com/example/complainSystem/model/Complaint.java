package com.example.complainSystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String category;  // Service, Infrastructure, Admin

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private String status = "Pending";  // Default status

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo; // IT or Admin updating it

    // Constructors
    public Complaint() {}
    
    public Complaint(User user, String category, String description) {
        this.user = user;
        this.category = category;
        this.description = description;
        this.status = "Pending";
    }

    // Getters and Setters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User assignedTo) { this.assignedTo = assignedTo; }
}
