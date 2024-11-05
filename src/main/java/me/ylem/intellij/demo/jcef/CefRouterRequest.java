package me.ylem.intellij.demo.jcef;

import java.util.Map;
import lombok.Data;

/**
 * CEF路由器请求
 *
 * @since 2024/11/05
 **/
@Data
public class CefRouterRequest {

    /**
     * 指令
     */
    private String command;

    /**
     * 数据
     */
    private Map<String, Object> data;

}
