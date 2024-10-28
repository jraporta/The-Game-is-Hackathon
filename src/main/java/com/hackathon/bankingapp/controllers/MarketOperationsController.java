package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.BuyAssetsDTO;
import com.hackathon.bankingapp.dto.SellAssetsDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class MarketOperationsController {

    @PostMapping("/api/account/buy-asset")
    public ResponseEntity<String> buyAsset(@AuthenticationPrincipal UserDetails userDetails,
                                           @Valid @RequestBody BuyAssetsDTO buyAssetsDTO){
        return ResponseEntity.ok("Asset purchase successful.");
    }

    @PostMapping("/api/account/sell-asset")
    public ResponseEntity<String> sellAsset(@AuthenticationPrincipal UserDetails userDetails,
                                            @Valid @RequestBody SellAssetsDTO buyAssetsDTO){
        return ResponseEntity.ok("Asset sale successful.");
    }

    @GetMapping("/market/prices")
    public void assetInformation(){

    }

    @GetMapping("/market/prices/{symbol}")
    public BigDecimal allAssetInformation(@PathVariable String symbol){
        return new BigDecimal("1175.95");
    }

}
