package com.demo.skyros.service;

import com.demo.skyros.proxy.CurrencyExchangeProxy;
import com.demo.skyros.vo.AppResponse;
import com.demo.skyros.vo.CurrencyExchangeVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

@Service
public class CurrencyConversionService {

    Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);
    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;

    public AppResponse convertCurrency(CurrencyExchangeVO vo) {
        BigDecimal quantity = vo.getQuantity();
        AppResponse appResponse = currencyExchangeProxy.exchangeCurrency(vo);
        CurrencyExchangeVO currencyExchangeVO = new CurrencyExchangeVO();
        if (HttpStatus.OK.equals(appResponse.getHttpStatus())) {
            currencyExchangeVO = mapper.convertValue(appResponse.getData(), CurrencyExchangeVO.class);
            BigDecimal conversionMultiple = currencyExchangeVO.getConversionMultiple();
            BigDecimal totalCalculatedAmount = conversionMultiple.multiply(quantity);
            currencyExchangeVO.setTotalCalculatedAmount(totalCalculatedAmount);
            currencyExchangeVO.setQuantity(quantity);
        } else {
            return appResponse;
        }
        return prepareAppResponse(currencyExchangeVO, null);
    }

    private AppResponse prepareAppResponse(Object data, String message) {
        AppResponse appResponse = new AppResponse(message);
        appResponse.setData(data);
        appResponse.setResponseDate(new Date());
        appResponse.setHttpStatus(HttpStatus.OK);
        return appResponse;
    }

    private CurrencyExchangeVO exchangeCurrency(String from, String to) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        String uri = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
        return restTemplate.getForObject(uri, CurrencyExchangeVO.class, uriVariables);
    }

}
