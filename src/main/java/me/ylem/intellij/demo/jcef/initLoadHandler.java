package me.ylem.intellij.demo.jcef;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.ui.jcef.JBCefJSQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.network.CefRequest.TransitionType;


/**
 * INIT负载处理程序
 *
 * @since 2025/02/26
 **/
@Slf4j
public class initLoadHandler extends CefLoadHandlerAdapter {

    @Override
    public void onLoadingStateChange(CefBrowser browser, boolean isLoading, boolean canGoBack,
        boolean canGoForward) {
    }

    @Override
    public void onLoadStart(CefBrowser browser, CefFrame frame, TransitionType transitionType) {
        log.info("onLoadStart {} - {} - {}", browser.getURL(), frame.getURL(), transitionType);
        // IDEA info
        ApplicationInfo instance = ApplicationInfo.getInstance();
        String apiVersion = instance.getApiVersion();
        String versionName = instance.getVersionName();
        String fullApplicationName = instance.getFullApplicationName();
        String fullVersion = instance.getFullVersion();
        String shortVersion = instance.getShortVersion();

        String[] scripts = new String[]{"window.ide='IDEA';",
            "window.ideVersion='" + versionName + "';",
            "window.ideFullVersion='" + fullVersion + "';",
            "window.ideFullApplicationName='" + fullApplicationName + "';",
            "window.ideApiVersion='" + apiVersion + "';",
            "window.ideShortVersion='" + shortVersion + "';",};

        initCef(browser, scripts);
    }

    void initCef(CefBrowser browser, String... scripts) {
        for (String script : scripts) {
            browser.executeJavaScript(script, null, 0);
        }
    }


    @Override
    public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
        super.onLoadEnd(browser, frame, httpStatusCode);
    }

    @Override
    public void onLoadError(CefBrowser browser, CefFrame frame, ErrorCode errorCode,
        String errorText, String failedUrl) {
        super.onLoadError(browser, frame, errorCode, errorText, failedUrl);
    }

}
