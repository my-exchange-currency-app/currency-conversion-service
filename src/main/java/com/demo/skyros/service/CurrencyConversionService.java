package com.demo.skyros.service;

import com.demo.skyros.proxy.CurrencyExchangeProxy;
import com.demo.skyros.proxy.CurrencyMailProxy;
import com.demo.skyros.vo.CurrencyExchangeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class CurrencyConversionService {

    Logger logger = LoggerFactory.getLogger(CurrencyConversionService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CurrencyExchangeProxy currencyExchangeProxy;
    @Autowired
    private CurrencyMailProxy currencyMailProxy;
    @Value("${send.mail.per.request}")
    private boolean sendMailPerRequest;

    public CurrencyExchangeVO convertCurrency(String from, String to, BigDecimal quantity) {
        //CurrencyExchangeVO currencyExchangeVO = exchangeCurrency(from, to);
        CurrencyExchangeVO currencyExchangeVO = currencyExchangeProxy.exchangeCurrency(from, to);
        BigDecimal conversionMultiple = currencyExchangeVO.getConversionMultiple();
        BigDecimal totalCalculatedAmount = conversionMultiple.multiply(quantity);
        currencyExchangeVO.setTotalCalculatedAmount(totalCalculatedAmount);
        currencyExchangeVO.setQuantity(quantity);
        sendMail(currencyExchangeVO);
        return currencyExchangeVO;
    }

    private CurrencyExchangeVO exchangeCurrency(String from, String to) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        String uri = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
        return restTemplate.getForObject(uri, CurrencyExchangeVO.class, uriVariables);
    }

    private void sendMail(CurrencyExchangeVO currencyExchangeVO) {
        logger.info("sendMailPerRequest = [" + sendMailPerRequest + "]");
        try {
            if (sendMailPerRequest) {
                currencyMailProxy.sendMail(currencyExchangeVO);
            }
        } catch (Exception ex) {
            logger.error("sendMail failed with [" + ex.getMessage() + "]");
        } finally {
            logger.info("sendMail done");
        }
    }
}
