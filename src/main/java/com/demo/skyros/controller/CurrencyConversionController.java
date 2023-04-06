package com.demo.skyros.controller;

import com.demo.skyros.service.CurrencyConversionService;
import com.demo.skyros.vo.AppResponse;
import com.demo.skyros.vo.CurrencyExchangeVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("currency")
public class CurrencyConversionController {

    Logger log = Logger.getLogger(CurrencyConversionController.class.getSimpleName());
    @Autowired
    private CurrencyConversionService currencyConversionService;

    @PostMapping("conversion")
    public AppResponse exchangeCurrency(@RequestBody CurrencyExchangeVO vo) {
        return currencyConversionService.convertCurrency(vo);
    }
}
