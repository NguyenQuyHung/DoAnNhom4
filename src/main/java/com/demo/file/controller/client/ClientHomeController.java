package com.demo.file.controller.client;

import com.demo.file.entity.FileEntity;
import com.demo.file.service.FileService;
import com.demo.file.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client/home")
public class ClientHomeController {
    private final FileService fileService;
    private final UserService userService;

    public ClientHomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<FileEntity> fileEntities = fileService.getTop4();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("files", fileEntities);
        model.addAttribute("email", email);
        return "client/index";
    }



}
