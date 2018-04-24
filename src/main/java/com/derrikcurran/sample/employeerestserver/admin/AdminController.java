package com.derrikcurran.sample.employeerestserver.admin;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("network")
    public NetworkDetails getNetworkDetails() {
        return NetworkDetails.build();
    }
}
