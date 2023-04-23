package com.demo.skyros.filter;

import com.demo.skyros.vo.RequestVO;
import com.google.common.io.CharStreams;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Setter
@Getter
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private static final String REQUEST_ID = "REQUEST_ID";
    //@Autowired
    //private LoggingProxy loggingProxy;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${log.url.add}")
    private String url;
    @Value("${log.save.request}")
    private boolean saveRequest;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (saveRequest) {
            saveClientRequest(request);
        }
        return super.preHandle(request, response, handler);
    }

    public void saveClientRequest(HttpServletRequest request) {
        try {
            String body = CharStreams.toString(request.getReader());
            RequestVO requestVO = new RequestVO();
            requestVO.setRequestBody(body);
            requestVO.setTag("CONVERSION");
            requestVO.setRequestId(request.getHeader(REQUEST_ID));
            saveClientRequest(requestVO);
            //getLoggingProxy().saveClientRequest(requestVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveClientRequest(RequestVO requestVO) {
        restTemplate.postForObject(getUrl(), requestVO, void.class);
    }

}
