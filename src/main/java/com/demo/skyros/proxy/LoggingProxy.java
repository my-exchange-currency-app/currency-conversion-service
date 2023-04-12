package com.demo.skyros.proxy;

import com.demo.skyros.vo.RequestVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "logging-service", path = "/request")
public interface LoggingProxy {

    @PostMapping("add")
    void saveClientRequest(@RequestBody RequestVO requestVO);

}
