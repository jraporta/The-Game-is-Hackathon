package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.PinCreateDTO;
import com.hackathon.bankingapp.dto.PinUpdateDTO;
import com.hackathon.bankingapp.services.PinService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PinController {

    private final PinService pinService;

    public PinController(PinService pinService) {
        this.pinService = pinService;
    }

    @PostMapping("/api/account/pin/create")
    public ResponseEntity<Map<String, String>> createPin(@Valid @RequestBody PinCreateDTO pinCreateDTO,
                                                         @AuthenticationPrincipal UserDetails userDetails){
        pinService.createPin(userDetails.getUsername(), pinCreateDTO);
        Map<String, String> response = new HashMap<>();
        response.put("msg", "PIN created successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping("/api/account/pin/update")
    public void updatePin(@RequestBody PinUpdateDTO pinUpdateDTO){
        System.out.println("He recibido un pin " + pinUpdateDTO.getNewPin());
    }

}
