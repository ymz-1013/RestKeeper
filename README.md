# RestKeeper

餐掌柜智能餐饮管理系统主要为餐饮集团或门店提供全套的互联网智能化收银、管理相关解决方案。主要功能包括点餐、收银、会员管理、移动支付等。



## 第1章 基础架构搭建

技术架构：

![img](file:///G:/baiduDownload/06-%E9%A1%B9%E7%9B%AE%E5%BA%93_%E3%80%90%E9%A4%90%E9%A5%AE%E8%A1%8C%E4%B8%9A%E3%80%91%E9%A4%90%E6%8E%8C%E6%9F%9C%E9%A1%B9%E7%9B%AE/%E9%A4%90%E5%90%A7%E8%AF%BE%E4%BB%B6/day1/%E8%AE%B2%E4%B9%89%EF%BC%88html%EF%BC%89/assets/1-8.png)

目标1：了解餐掌柜需求设计&系统架构设计

目标2：了解Spring Cloud Alibaba整体技术栈组成

目标3：掌握nacos对于注册中心&配置中心的使用

目标4：了解Lombok的使用

目标5：掌握Mybatis-plus的使用

目标6：掌握Spring Cloud Alibaba与dubbo和Mybatis-plus整合

目标7：掌握swagger的使用

目标8：掌握统一异常处理

### 前端结果进行统一的封装

因为后端需要与前端进行对接，所以后端的数据返回格式需要与前端进行沟通，这也是开发过程中不可缺少的一步。 

```java
/**
 * 返回结果通用对象封装
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<D> implements Serializable {

    private static final long serialVersionUID = -2145852753254617071L;

    //是否成功
    private boolean success;

    //异常信息
    private String message;

    //200表示正常,300接口返回false，400调用异常
    private int code;

    //泛型，用来存储原有controller返回值
    private D data;

    public BaseResponse() {
        this.code = 200;
        this.message = "success";
        this.success = true;
    }


    public BaseResponse(D data){
        this();
        this.data = data;
    }

    public BaseResponse(boolean success){
        this();
        this.success = success;
        if(success == false){
            this.message = "操作失败";
            this.code = 300;
        }
    }

    public BaseResponse(boolean success,String msg){
        this(success);
        this.message = msg;
    }

    public BaseResponse(int code,String msg){
        this(false,msg);
        this.code = code;
    }
}
```

虽然有了数据返回实体类，但是该实体类应该如何返回呢？因为当前所有的controller对于数据返回的类型有boolean、Result、PageVO。因此当前的解决思路是设置返回值拦截，进行数据格式组装。

新建**ResponseAdvisor**类，用于进行返回结果统一转换。@RestControllerAdvice用于在结果返回前对结果进行处理

```java
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
```



### 统一异常处理

1）在**restkeeper_common_web** ->**response** -> **exception**下 新建**GlobalExceptionHandler**

```java
/**
 * 异常拦截
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object Exception(Exception ex) {
        Map<String, Object> errorMap = new HashMap<String, Object>();
        errorMap.put("errCode", "00000");
        errorMap.put("errMessage", ex.getMessage());
        return errorMap;
    }
}

```

2）在**restkeeper_operator_web** 中追加scanBasePackages = {"com.restkeeper"}

```java
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = {"com.restkeeper"})
@EnableDiscoveryClient
public class OperatorCenterWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperatorCenterWebApplication.class, args);
    }
}
```

进行异常测试结果如下：

![1574825045519](file:///G:/baiduDownload/06-%E9%A1%B9%E7%9B%AE%E5%BA%93_%E3%80%90%E9%A4%90%E9%A5%AE%E8%A1%8C%E4%B8%9A%E3%80%91%E9%A4%90%E6%8E%8C%E6%9F%9C%E9%A1%B9%E7%9B%AE/%E9%A4%90%E5%90%A7%E8%AF%BE%E4%BB%B6/day1/%E8%AE%B2%E4%B9%89%EF%BC%88html%EF%BC%89/assets/1574825045519.png)

3） 上面的结果并没有给出异常具体的信息，对于异常信息的响应，最好也是定义实体类的形式进行数据返回，因此新建ExceptionResponse异常信息响应类.

```java
@Data
public class ExceptionResponse{
    private String msg;
    public ExceptionResponse(String msg){
        this.msg = msg;
    }
}
```

4） 修改全局异常处理类**GlobalExceptionHandler**

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object exception(Exception e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMessage());
        return exceptionResponse;
    }
}
```

5）修改**ResponseAdvisor**类，添加**ExceptionResponse**类型判断

```java
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
```

异常测试结果如下

![1576138088598](file:///G:/baiduDownload/06-%E9%A1%B9%E7%9B%AE%E5%BA%93_%E3%80%90%E9%A4%90%E9%A5%AE%E8%A1%8C%E4%B8%9A%E3%80%91%E9%A4%90%E6%8E%8C%E6%9F%9C%E9%A1%B9%E7%9B%AE/%E9%A4%90%E5%90%A7%E8%AF%BE%E4%BB%B6/day1/%E8%AE%B2%E4%B9%89%EF%BC%88html%EF%BC%89/assets/1576138088598.png)
