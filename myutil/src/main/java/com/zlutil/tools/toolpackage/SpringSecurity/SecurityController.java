package com.zlutil.tools.toolpackage.SpringSecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @GetMapping("/admin/v1/info")
    public String normalLogin() {

        return "Admin Response";
    }

    @GetMapping("/user/v1/info")
    public String normalLogout() {

        return "User Response";
    }
}
