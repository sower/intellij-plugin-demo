package me.ylem.intellij.demo.jcef;

import com.intellij.ide.BrowserUtil;
import lombok.extern.slf4j.Slf4j;
import org.cef.browser.CefBrowser;

/**
 * 路由器请求处理程序
 *
 * @since 2024/11/05
 **/
@Slf4j
public class RouterRequestHandler {

    public static void handle(CefBrowser browser, CefRouterRequest request) {
        RouterCommand command = RouterCommand.of(request.getCommand());
        if (command == null) {
            log.warn("command not found: {}", request.getCommand());
            return;
        }

        switch (command) {
            case RELOAD:
                browser.reload();
                break;
            case OPEN_URL:
                BrowserUtil.browse(request.getData().get("url").toString());
                break;

            default:
                log.warn("command not support: {}", request.getCommand());
                break;
        }
    }

}
