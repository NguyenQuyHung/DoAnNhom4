package com.demo.file.controller.admin;

import com.demo.file.service.CustomerService;
import com.demo.file.service.FileService;
import com.demo.file.payload.dto.*;
import com.demo.file.service.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/file")
public class AdminFileController {
    private final FileService fileService;
    private final FileTypeService fileTypeService;
    private final CustomerService customerService;
    private final UserService userService;

    public AdminFileController(FileService fileService, FileTypeService fileTypeService, CustomerService customerService, UserService userService) {
        this.fileService = fileService;
        this.fileTypeService = fileTypeService;
        this.customerService = customerService;
        this.userService = userService;
    }

    @GetMapping("")
    public String productPage(Model model) {
        model.addAttribute("users", userService.getAllAdmins());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        List<FileDto> fileDtos = fileService.getAll();
        model.addAttribute("files", fileDtos);
        return "admin/file/list-file";
    }
    @GetMapping("/insertFilePage")
    public String insertProductPage(Model model) {
        model.addAttribute("users", userService.getAllAdmins());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("email", email);
        FileDto file = new FileDto();
        model.addAttribute("file", file);
        model.addAttribute("fileTypes", fileTypeService.getAll());
        return "admin/file/insert-file";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute(name = "file") FileDto productDto, @RequestParam(value = "file", required = false) MultipartFile file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        fileService.save(productDto, file, username);
        return "redirect:/admin/file";
    }

    @GetMapping("/updateFile/{id}")
    public String getFormUpdateProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("email", SecurityContextHolder.getContext().getAuthentication().getName());
        FileDto fileDto = fileService.findById(id);
        model.addAttribute("users", userService.getAllAdmins());
        model.addAttribute("file", fileDto);
        model.addAttribute("id", fileDto.getId());
        model.addAttribute("fileTypes", fileTypeService.getAll());

        return "admin/file/update-file";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute(name = "file") FileDto fileDto, @RequestParam(value = "file", required = false) MultipartFile file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        fileService.update(fileDto, file, username);
        return "redirect:/admin/file/updateFile/" + fileDto.getId();
    }

    @GetMapping("/deleteFile/{id}")
    public String delete(@PathVariable("id") Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        fileService.deleteById(id, username);
        return "redirect:/admin/file";
    }

}