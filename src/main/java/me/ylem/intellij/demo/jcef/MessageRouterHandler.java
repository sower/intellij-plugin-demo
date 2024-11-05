package me.ylem.intellij.demo.jcef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intellij.openapi.application.ApplicationManager;
import lombok.extern.slf4j.Slf4j;
import me.ylem.intellij.demo.util.JacksonUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

/**
 * 消息路由器处理器
 *
 * @since 2024/11/05
 **/
@Slf4j
public class MessageRouterHandler extends CefMessageRouterHandlerAdapter {

    @Override
    public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request,
        boolean persistent, CefQueryCallback callback) {
        log.info("receive queryId: {}, request: {}", queryId, request);
        try {
            CefRouterRequest cefRouterRequest = JacksonUtils.json2obj(request,
                CefRouterRequest.class);
            ApplicationManager.getApplication()
                .invokeLater(() -> RouterRequestHandler.handle(browser, cefRouterRequest));
            callback.success(cefRouterRequest.getCommand());
            return true;
        } catch (JsonProcessingException e) {
            log.warn("Failed to parse request");
        }
        return false;
    }
}
