package com.demo.file.controller;

import com.demo.file.entity.FileEntity;
import com.demo.file.service.FileService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
public class HomeController {
    private final FileService fileService;

    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("")
    private String indexHome(Model model) {
        List<FileEntity> productEntities = fileService.getTop4();
        model.addAttribute("products", productEntities);
        return "guest/index";
    }
    @GetMapping("/home")
    private String homePage(Model model) {
        List<FileEntity> fileEntities = fileService.getTop4();
        model.addAttribute("files", fileEntities);
        return "guest/index";
    }

    @GetMapping("/index")
    private String index(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        if(authorities.stream().anyMatch(authority -> "ROLE_SUPER_ADMIN".equals(authority.getAuthority())) || authorities.stream().anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()))) {
            model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
            return "redirect:/admin/home";
        }
        else {
            if(!Objects.equals(username, "")) {
                model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
                return "redirect:/client/home";
            }
            else {
                return "guest/index";
            }
        }
    }

    @GetMapping("/403")
    public String error() {
        return "403";
    }


}
