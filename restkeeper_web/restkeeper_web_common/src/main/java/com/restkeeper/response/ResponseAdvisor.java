package com.restkeeper.response;


import com.restkeeper.response.exception.ExceptionResponse;
import com.restkeeper.response.vo.PageVO;
import com.restkeeper.utils.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.restkeeper")
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {
    //判断哪些内容需要进行拦截
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    //body表示PageVO
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body instanceof Result){
            return body;
        }
        if (body instanceof Boolean){
            boolean result = (boolean)body;
            return new BaseResponse<Boolean>(result);
        }
        if (body instanceof PageVO){
            return new BaseResponse<>(body);
        }
        if (body instanceof ExceptionResponse){
            return new BaseResponse<>(400,((ExceptionResponse) body).getMsg());
        }
        return new BaseResponse<>(body);
    }
}
