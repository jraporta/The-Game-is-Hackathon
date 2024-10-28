package com.hackathon.bankingapp.controllers;

import com.hackathon.bankingapp.dto.BuyAssetsDTO;
import com.hackathon.bankingapp.dto.MarketPricesDTO;
import com.hackathon.bankingapp.dto.SellAssetsDTO;
import com.hackathon.bankingapp.services.MarketOperationsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


@RestController
public class MarketOperationsController {

    private MarketOperationsService marketOperationsService;

    public MarketOperationsController(MarketOperationsService marketOperationsService) {
        this.marketOperationsService = marketOperationsService;
    }

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
    public ResponseEntity<Map<String, Double>> allAssetInformation(){
        return ResponseEntity.ok(marketOperationsService.allAssetInformation());
    }

    @GetMapping("/market/prices/{symbol}")
    public ResponseEntity<Double> assetInformation(@PathVariable String symbol) {
            return ResponseEntity.ok(marketOperationsService.assetInformation(symbol));
    }

}
