package me.ylem.intellij.demo.jcef;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefBrowserBase;
import com.intellij.ui.jcef.JBCefClient;
import com.intellij.ui.jcef.JBCefJSQuery;
import com.intellij.util.io.jackson.JacksonUtil;
import java.util.Map;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.ylem.intellij.demo.util.JacksonUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;

/**
 * Browser
 *
 * @since 2025/03/06
 **/
@Getter
@Slf4j
public class Webview {

    Project project;
    JBCefClient jbCefClient = JBCefApp.getInstance().createClient();
    JBCefBrowser jbCefBrowser = JBCefBrowser.createBuilder().setClient(jbCefClient)
        .setOffScreenRendering(SystemInfo.isLinux).build();

    public Webview(Project project) {
        this.project = project;
        Disposer.register(project, jbCefClient);
        initBrowser(jbCefBrowser);
    }


    private void initBrowser(JBCefBrowser browser) {
        JBCefJSQuery jbCefJSQuery = JBCefJSQuery.create((JBCefBrowserBase) browser);
        // Listen for events sent from browser
        jbCefJSQuery.addHandler(msg -> {
            log.info("Received message from browser: {}", msg);
            return new JBCefJSQuery.Response(null);
        });

        CefBrowser cefBrowser = browser.getCefBrowser();
        jbCefClient.addLoadHandler(new initLoadHandler(), cefBrowser);
        jbCefClient.addKeyboardHandler(new KeyboardHandler(), cefBrowser);
        // 处理来自 Chromium 浏览器的消息和事件
        CefMessageRouter.CefMessageRouterConfig routerConfig = new CefMessageRouter.CefMessageRouterConfig();
        routerConfig.jsQueryFunction = "pluginQuery";
        CefMessageRouter messageRouter = CefMessageRouter.create(routerConfig,
            new MessageRouterHandler());
        jbCefClient.getCefClient().addMessageRouter(messageRouter);
        jbCefClient.addContextMenuHandler(new ContextMenuHandler(), cefBrowser);
    }

    public void loadHtml(String html) {
        jbCefBrowser.loadHTML(html);
    }

    public void loadURL(String url) {
        jbCefBrowser.loadURL(url);
    }

    @SneakyThrows
    public void sendToWebview(String messageType, Object data, String messageId) {
        Map<String, Object> msg = Map.of("messageType", messageType, "data", data, "messageId",
            messageId);
        String json = JacksonUtils.obj2json(msg);
        jbCefBrowser.getCefBrowser().executeJavaScript(buildJavaScript(json), null, 0);
    }

    String buildJavaScript(String jsonData) {
        return "window.postMessage(%s, '*');".formatted(jsonData);
    }

}
