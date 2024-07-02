package com.demo.file.controller.admin;

import com.demo.file.entity.UserEntity;
import com.demo.file.payload.dto.CustomerDto;
import com.demo.file.payload.dto.UserDto;
import com.demo.file.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    private final FileService fileService;
    private final UserService userService;
    private final CustomerService customerService;

    public AdminHomeController(FileService fileService, UserService userService, CustomerService customerService) {
        this.fileService = fileService;
        this.userService = userService;
        this.customerService = customerService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        model.addAttribute("countProducts", fileService.countProductActive());
        model.addAttribute("countCustomers", customerService.countCustomers());
        UserEntity user = userService.findByEmail(email).get();
        return "admin/dashboard";
    }

    @GetMapping("/profile")
    public String adminProfile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);

        UserDto userDto = userService.getProfile(email);
        model.addAttribute("user", userDto);
        return "admin/profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfileAdmin(@ModelAttribute(name = "user")UserDto userDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateProfile(userDto, email);
        return "redirect:/admin/profile";
    }
}