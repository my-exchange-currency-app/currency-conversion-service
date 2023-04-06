package com.demo.skyros.proxy;

import com.demo.skyros.vo.AppResponse;
import com.demo.skyros.vo.CurrencyExchangeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "currency-exchange-service", path = "/currency")
public interface CurrencyExchangeProxy {

    @PostMapping("exchange")
    AppResponse exchangeCurrency(@RequestBody CurrencyExchangeVO currencyExchangeVO);
}
