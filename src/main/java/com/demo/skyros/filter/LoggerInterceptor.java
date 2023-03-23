package com.demo.skyros.filter;

import com.demo.skyros.repo.ClientRequestRepo;
import com.demo.skyros.service.ClientRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);
    @Autowired
    private ClientRequestRepo clientRequestRepo;
    @Autowired
    private ClientRequestService clientRequestService;

    public LoggerInterceptor(ClientRequestRepo clientRequestRepo) {
        this.clientRequestRepo = clientRequestRepo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        getClientRequestService().saveClientRequest(request, "conversion");
        return super.preHandle(request, response, handler);
    }

    public ClientRequestService getClientRequestService() {
        return clientRequestService;
    }

    public void setClientRequestService(ClientRequestService clientRequestService) {
        this.clientRequestService = clientRequestService;
    }

    public ClientRequestRepo getClientRequestRepo() {
        return clientRequestRepo;
    }

    public void setClientRequestRepo(ClientRequestRepo clientRequestRepo) {
        this.clientRequestRepo = clientRequestRepo;
    }
}
