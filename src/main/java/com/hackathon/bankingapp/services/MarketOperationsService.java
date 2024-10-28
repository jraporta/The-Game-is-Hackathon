package com.hackathon.bankingapp.services;

import com.hackathon.bankingapp.dto.MarketPricesDTO;
import com.hackathon.bankingapp.exceptions.UnknownSymbolException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class MarketOperationsService {

    static final String API_URL = "https://faas-lon1-917a94a7.doserverless.co/api/v1/web/fn-e0f31110-7521-4cb9-86a2-645f66eefb63/default/market-prices-simulator";
    private final RestTemplate restTemplate;

    public MarketOperationsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Map<String, Double> allAssetInformation() {
        return restTemplate.getForObject(API_URL, Map.class);
    }

    public Double assetInformation(String symbol) {
        Map<String, Double> assets = restTemplate.getForObject(API_URL, Map.class);
        Double price = assets.get(symbol.toUpperCase());
        if (price == null) throw new UnknownSymbolException("Unknown symbol: " + symbol);
        return price;
    }
}
