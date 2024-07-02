package com.demo.file.controller.auth;

import com.demo.file.entity.CustomerEntity;
import com.demo.file.entity.RoleEntity;
import com.demo.file.entity.UserEntity;
import com.demo.file.payload.dto.UserDto;
import com.demo.file.service.CustomerService;
import com.demo.file.service.RoleService;
import com.demo.file.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, CustomerService customerService, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customerService = customerService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login";
    }

    @RequestMapping(value = "register")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/register";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String checkOtp(@ModelAttribute("user") UserDto user, Model model) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail((String) user.getEmail());
        userEntity.setPassword(passwordEncoder.encode((String) user.getPassword()));
        userEntity.setPhoneNumber(user.getPhone());
        userEntity.setStatus(1);

        RoleEntity client = roleService.findById(3L).get();

        Set<RoleEntity> roleEntities = new HashSet<>();

        roleEntities.add(client);
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName(user.getFullName());
        customerEntity.setStatus(1);
        customerEntity.setAddress(user.getAddress());
        customerEntity.setGender("MALE");
        userEntity.setRoleEntities(roleEntities);
        userEntity.setCreatedBy("SUPER_ADMIN");
        userEntity.setUpdatedBy("SUPER_ADMIN");
        customerEntity.setCreatedBy("SUPER_ADMIN");
        customerEntity.setUpdatedBy("SUPER_ADMIN");
        UserEntity saveUser = userService.saveUser(userEntity);
        customerEntity.setUserEntity(saveUser);
        customerEntity = customerService.saveEntity(customerEntity);

        customerEntity = customerService.saveEntity(customerEntity);
        saveUser.setCustomerEntity(customerEntity);
        userService.saveUser(saveUser);
        return "redirect:/login";
    }

}
