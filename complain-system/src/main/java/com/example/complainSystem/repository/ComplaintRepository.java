package com.example.complainSystem.repository;

import com.example.complainSystem.model.Complaint;
import com.example.complainSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByUser(User user); // Get complaints of a specific user
    List<Complaint> findByAssignedTo(User assignedTo); // Get complaints assigned to IT/Admin
}
