package com.demo.skyros.service;

import com.demo.skyros.proxy.CurrencyExchangeProxy;
import com.demo.skyros.vo.CurrencyExchangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class CurrencyConversionService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    public CurrencyExchangeVO convertCurrency(String from, String to, BigDecimal quantity) {
        //CurrencyExchangeVO currencyExchangeVO = exchangeCurrency(from, to);
        CurrencyExchangeVO currencyExchangeVO = currencyExchangeProxy.exchangeCurrency(from, to);
        BigDecimal conversionMultiple = currencyExchangeVO.getConversionMultiple();
        BigDecimal totalCalculatedAmount = conversionMultiple.multiply(quantity);
        currencyExchangeVO.setTotalCalculatedAmount(totalCalculatedAmount);
        currencyExchangeVO.setQuantity(quantity);
        return currencyExchangeVO;
    }

    private CurrencyExchangeVO exchangeCurrency(String from, String to) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        String uri = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
        return restTemplate.getForObject(uri, CurrencyExchangeVO.class, uriVariables);
    }
}
