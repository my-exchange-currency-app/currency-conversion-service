package com.demo.skyros.controller;

import com.demo.skyros.service.CurrencyConversionService;
import com.demo.skyros.vo.AppResponse;
import com.demo.skyros.vo.CurrencyExchangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("conversion")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @PostMapping("convert")
    public AppResponse exchangeCurrency(@RequestBody CurrencyExchangeVO vo) {
        return currencyConversionService.convertCurrency(vo);
    }

    @GetMapping("limit")
    public AppResponse getLimit() {
        return currencyConversionService.getLimit();
    }
}
