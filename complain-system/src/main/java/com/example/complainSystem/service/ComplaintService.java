package com.example.complainSystem.service;

import com.example.complainSystem.model.Complaint;
import com.example.complainSystem.model.User;
import com.example.complainSystem.repository.ComplaintRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    // Submit new complaint
    public Complaint submitComplaint(User user, String category, String description) {
        Complaint complaint = new Complaint(user, category, description);
        return complaintRepository.save(complaint);
    }

    // Get complaints of a user
    public List<Complaint> getUserComplaints(User user) {
        return complaintRepository.findByUser(user);
    }

    // Get complaints assigned to a staff/admin
    public List<Complaint> getAssignedComplaints(User assignedTo) {
        return complaintRepository.findByAssignedTo(assignedTo);
    }

    // Update complaint status or assign it
    public Complaint updateComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    // Get complaint by ID
    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }
}
