package com.demo.file.controller.admin;

import com.demo.file.payload.dto.FileDto;
import com.demo.file.payload.dto.FileTypeDto;
import com.demo.file.service.CustomerService;
import com.demo.file.service.FileService;
import com.demo.file.service.FileTypeService;
import com.demo.file.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/fileType")
public class AdminFileTypeController {
    private final FileTypeService fileTypeService;
    private final FileService fileService;
    private final CustomerService customerService;
    private final UserService userService;

    public AdminFileTypeController(FileTypeService fileTypeService, FileService fileService, CustomerService customerService, UserService userService) {
        this.fileTypeService = fileTypeService;
        this.fileService = fileService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("")
    public String productPage(Model model) {
        model.addAttribute("users", userService.getAllAdmins());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        List<FileTypeDto> fileTypeDtos = fileTypeService.getAll();
        model.addAttribute("fileTypes", fileTypeDtos);
        return "admin/file-type/list-file-type";
    }
    @GetMapping("/insertFileTypePage")
    public String insertProductPage(Model model) {
        model.addAttribute("users", userService.getAllAdmins());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        FileTypeDto fileTypeDto = new FileTypeDto();
        model.addAttribute("fileType", fileTypeDto);
        return "admin/file-type/insert-file-type";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "fileType") FileTypeDto productDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        fileTypeService.save(productDto, username);
        return "redirect:/admin/fileType";
    }

    @GetMapping("/updateFileType/{id}")
    public String getFormUpdateProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        FileTypeDto fileTypeDto = fileTypeService.findById(id);
        model.addAttribute("users", userService.getAllAdmins());
        model.addAttribute("fileType", fileTypeDto);
        model.addAttribute("id", fileTypeDto.getId());
        return "admin/file-type/update-file-type";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "file") FileTypeDto fileDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        fileTypeService.update(fileDto, username);
        return "redirect:/admin/fileType/updateFileType/" + fileDto.getId();
    }

    @GetMapping("/deleteFileType/{id}")
    public String delete(@PathVariable("id") Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        fileTypeService.deleteById(id, username);
        return "redirect:/admin/fileType";
    }

}