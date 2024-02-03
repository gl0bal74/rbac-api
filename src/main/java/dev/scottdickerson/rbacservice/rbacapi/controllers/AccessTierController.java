package dev.scottdickerson.rbacservice.rbacapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessTierController {
    @RequestMapping("/access-tier")
    public ResponseEntity<String> getAccessTier() {
        return ResponseEntity.ok("Access Tier");
    }
}
