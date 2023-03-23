package com.demo.skyros.filter;

import com.demo.skyros.entity.ClientRequestEntity;
import com.demo.skyros.entity.EntityAudit;
import com.demo.skyros.repo.ClientRequestRepo;
import com.demo.skyros.vo.CurrencyExchangeVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID = "REQUEST_ID";
    Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    @Autowired
    private ClientRequestRepo clientRequestRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        saveClientRequest(request, response);
        return super.preHandle(request, response, handler);
    }

    private void saveClientRequest(HttpServletRequest request, HttpServletResponse response) {
        String[] pathList = request.getRequestURI().split("/");
        ClientRequestEntity clientRequest = new ClientRequestEntity();
        clientRequest.setTag("conversion");
        clientRequest.setRequestId(request.getHeader(REQUEST_ID));
        CurrencyExchangeVO currencyExchangeVO = prepareCurrencyExchangeVO(pathList);
        clientRequest.setRequestBody(gson.toJson(currencyExchangeVO));
        clientRequest.setAudit(prepareAudit());
        getClientRequestRepo().save(clientRequest);
    }

    private String prepareRequestBody(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception ex) {
            logger.error("failed to prepare requestBody", ex.getMessage());
        }
        return null;
    }

    CurrencyExchangeVO prepareCurrencyExchangeVO(String[] pathList) {

        CurrencyExchangeVO currencyExchangeVO = new CurrencyExchangeVO();
        for (int i = 0; i < pathList.length; i++) {
            if (pathList[i].equals("from")) {
                String from = pathList[i + 1];
                currencyExchangeVO.setFrom(from);
            }
            if (pathList[i].equals("to")) {
                String to = pathList[i + 1];
                currencyExchangeVO.setTo(to);
            }
            if (pathList[i].equals("quantity")) {
                String quantity = pathList[i + 1];
                currencyExchangeVO.setQuantity(new BigDecimal(quantity));
            }
        }
        return currencyExchangeVO;
    }

    private EntityAudit prepareAudit() {
        EntityAudit audit = new EntityAudit();
        audit.setCreatedBy("system");
        audit.setCreatedDate(new Date());
        audit.setLastModifiedBy("system");
        audit.setLastModifiedDate(new Date());
        return audit;
    }

    public ClientRequestRepo getClientRequestRepo() {
        return clientRequestRepo;
    }

    public void setClientRequestRepo(ClientRequestRepo clientRequestRepo) {
        this.clientRequestRepo = clientRequestRepo;
    }

}
