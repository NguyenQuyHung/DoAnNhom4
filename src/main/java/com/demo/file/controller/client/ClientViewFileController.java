package com.demo.file.controller.client;

import com.demo.file.entity.FileEntity;
import com.demo.file.payload.dto.FileDto;
import com.demo.file.service.CustomerService;
import com.demo.file.service.FileService;
import com.demo.file.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client/file")
public class ClientViewFileController {
    private final FileService fileService;
    private final CustomerService customerService;
    private final UserService userService;

    public ClientViewFileController(CustomerService customerService, FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        List<FileDto> fileDtos = fileService.getAllIfActive();
        System.out.println(fileDtos);
        model.addAttribute("files", fileDtos);
        return "client/view-files";
    }

    @GetMapping("/detail/{fileId}")
    public String detailProductGuest(@PathVariable(name = "fileId") Long fileId, Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        FileEntity fileEntity = fileService.findEntityById(fileId);
        model.addAttribute("file", fileEntity);
        Long id = userService.findIdByEmail(email);
        model.addAttribute("fileId", fileId);
        model.addAttribute("userId", id);
        return "client/file-details";
    }
}
