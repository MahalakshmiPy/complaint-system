package com.example.complainSystem.controller;

import com.example.complainSystem.model.Complaint;
import com.example.complainSystem.model.User;
import com.example.complainSystem.repository.ComplaintRepository;
import com.example.complainSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    // 1️⃣ View all complaints
    @GetMapping("/complaints")
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // 2️⃣ Assign complaint to IT user
    @PostMapping("/assign")
    public String assignComplaint(
            @RequestParam Long complaintId,
            @RequestParam String itUsername) {

        Optional<Complaint> complaintOpt = complaintRepository.findById(complaintId);
        Optional<User> itUserOpt = userRepository.findByUsername(itUsername);

        if (complaintOpt.isEmpty()) {
            return "Complaint not found";
        }

        if (itUserOpt.isEmpty()) {
            return "IT user not found";
        }

        Complaint complaint = complaintOpt.get();
        User itUser = itUserOpt.get();

        complaint.setAssignedTo(itUser);
        complaint.setStatus("IN_PROGRESS");

        complaintRepository.save(complaint);
        return "Complaint assigned to IT successfully";
    }

    // Clear all complaints
@PostMapping("/clearAll")
public String clearAllComplaints() {
    List<Complaint> complaints = complaintRepository.findAll();
    complaints.forEach(c -> c.setStatus("CLEARED")); // or delete if you want
    complaintRepository.saveAll(complaints);
    return "All complaints cleared successfully";
}


    // 3️⃣ Update complaint status (by Admin or IT)
    @PostMapping("/updateStatus")
    public String updateStatus(
            @RequestParam Long complaintId,
            @RequestParam String status) {

        Optional<Complaint> complaintOpt = complaintRepository.findById(complaintId);

        if (complaintOpt.isEmpty()) {
            return "Complaint not found";
        }

        Complaint complaint = complaintOpt.get();

        // Validate status
        if (!status.equalsIgnoreCase("PENDING") &&
            !status.equalsIgnoreCase("IN_PROGRESS") &&
            !status.equalsIgnoreCase("RESOLVED") &&
            !status.equalsIgnoreCase("REJECTED")) {
            return "Invalid status";
        }

        complaint.setStatus(status.toUpperCase());
        complaintRepository.save(complaint);

        return "Complaint status updated to " + status.toUpperCase();
    }

    // 4️⃣ Optional: View complaints assigned to a specific IT user
    @GetMapping("/complaints/assigned")
    public List<Complaint> getAssignedComplaints(@RequestParam String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return List.of(); // return empty list if user not found
        }
        return complaintRepository.findByAssignedTo(userOpt.get());
    }
}
