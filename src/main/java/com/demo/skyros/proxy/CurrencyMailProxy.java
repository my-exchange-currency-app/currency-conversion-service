package com.demo.skyros.proxy;

import com.demo.skyros.vo.CurrencyExchangeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "currency-mail-service", url = "http://localhost:9999")
public interface CurrencyMailProxy {

    @PostMapping("sendMail")
    CurrencyExchangeVO sendMail(@RequestBody CurrencyExchangeVO currencyExchangeVO);
}
