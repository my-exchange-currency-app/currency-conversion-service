package com.demo.skyros.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@Setter
@ConfigurationProperties("limits-service")
public class LimitServiceConfiguration {

    private int minimum;
    private int maximum;

}


