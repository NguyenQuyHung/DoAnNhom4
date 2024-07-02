package com.demo.file.controller.client;

import com.demo.file.entity.FileEntity;
import com.demo.file.payload.dto.CustomerDto;
import com.demo.file.service.CustomerService;
import com.demo.file.service.FileService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/client/profile")
public class ClientProfileController {
    private final CustomerService customerService;

    public ClientProfileController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public String profile(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomerDto customerDto = customerService.getProfile(email);
        System.out.println(customerDto.getPhoneNumber());
        model.addAttribute("customer", customerDto);
        return "client/profile";
    }
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute(name = "customer") CustomerDto customerDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(customerDto.getPhoneNumber());
        if(!Objects.equals(email, customerDto.getEmail())) {
            customerService.updateProfile(customerDto, email);
            return "redirect:/home";
        }
        else {
            customerService.updateProfile(customerDto, email);
            return "redirect:/client/profile";
        }
    }
}
