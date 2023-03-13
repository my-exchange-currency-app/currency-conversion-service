package com.demo.skyros.controller;

import com.demo.skyros.service.CurrencyConversionService;
import com.demo.skyros.vo.CurrencyExchangeVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    Logger log = Logger.getLogger(CurrencyConversionController.class.getSimpleName());
    @Autowired
    private CurrencyConversionService currencyConversionService;

    @GetMapping("currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyExchangeVO exchangeCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
        CurrencyExchangeVO currencyExchangeVO = currencyConversionService.convertCurrency(from, to, quantity);
        return currencyExchangeVO;
    }
}
