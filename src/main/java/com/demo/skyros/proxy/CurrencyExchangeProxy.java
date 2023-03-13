package com.demo.skyros.proxy;

import com.demo.skyros.vo.CurrencyExchangeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-exchange-service"/*, url = "http://localhost:8000"*/)
public interface CurrencyExchangeProxy {

    @GetMapping("currency-exchange/from/{from}/to/{to}")
    CurrencyExchangeVO exchangeCurrency(@PathVariable String from, @PathVariable String to);
}
