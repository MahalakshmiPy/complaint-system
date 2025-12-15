package com.example.complainSystem.controller;

import com.example.complainSystem.model.Complaint;
import com.example.complainSystem.model.User;
import com.example.complainSystem.service.ComplaintService;
import com.example.complainSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;

    // Submit a complaint
    @PostMapping("/submit")
    public String submitComplaint(@RequestParam String username,
                                  @RequestParam String category,
                                  @RequestParam String description) {

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent()) {
            complaintService.submitComplaint(userOpt.get(), category, description);
            return "Complaint submitted successfully!";
        }
        return "User not found";
    }

    // View complaints for a user
    @GetMapping("/user")
    public List<Complaint> getUserComplaints(@RequestParam String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        return userOpt.map(user -> complaintService.getUserComplaints(user)).orElse(null);
    }

    // Update complaint status or assign to IT
    @PostMapping("/update")
    public String updateComplaint(@RequestParam Long complaintId,
                                  @RequestParam(required = false) String status,
                                  @RequestParam(required = false) String assignedToUsername) {

        Optional<Complaint> complaintOpt = complaintService.getComplaintById(complaintId);
        if (complaintOpt.isEmpty()) return "Complaint not found";

        Complaint complaint = complaintOpt.get();

        if (status != null) complaint.setStatus(status);

        if (assignedToUsername != null) {
            Optional<User> assignedUserOpt = userService.findByUsername(assignedToUsername);
            if (assignedUserOpt.isEmpty()) return "Assigned user not found";
            complaint.setAssignedTo(assignedUserOpt.get());
        }

        complaintService.updateComplaint(complaint);
        return "Complaint updated successfully!";
    }

    // View complaints assigned to a staff/admin
    @GetMapping("/assigned")
    public List<Complaint> getAssignedComplaints(@RequestParam String username) {
        Optional<User> userOpt = userService.findByUsername(username);
        return userOpt.map(user -> complaintService.getAssignedComplaints(user)).orElse(null);
    }
}
