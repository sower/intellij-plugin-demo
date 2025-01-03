package me.ylem.intellij.demo.jcef;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefClient;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.cef.browser.CefMessageRouter;
import org.jetbrains.annotations.NotNull;

/**
 * JcefToolWindowFactory
 *
 * @since 2024/09/15
 **/
public class JcefToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 此处方法将会在点击ToolWindow的时候触发
        ContentManager contentManager = toolWindow.getContentManager();
        Content labelContent = contentManager.getFactory().createContent(getCef(), "JCEF", true);
        contentManager.addContent(labelContent);
    }

    private JPanel getCef() {
        JPanel content = new JPanel(new BorderLayout());
        // 判断所处的IDEA环境是否支持JCEF
        if (!JBCefApp.isSupported()) {
            content.add(new JLabel("当前环境不支持JCEF", SwingConstants.CENTER));
            return content;
        }
        // 创建 JBCefBrowser
        JBCefBrowser jbCefBrowser = new JBCefBrowser();
        initBrowser(jbCefBrowser);
        // 将 JBCefBrowser 的UI控件设置到Panel中
        content.add(jbCefBrowser.getComponent(), BorderLayout.CENTER);
        // 加载URL
        jbCefBrowser.loadURL("https://papers.co");
        return content;
    }

    private void initBrowser(JBCefBrowser browser) {
        JBCefClient jbCefClient = browser.getJBCefClient();
        jbCefClient.addKeyboardHandler(new KeyboardHandler(), browser.getCefBrowser());

        // 处理来自 Chromium 浏览器的消息和事件
        CefMessageRouter.CefMessageRouterConfig routerConfig =
            new CefMessageRouter.CefMessageRouterConfig();
        routerConfig.jsQueryFunction = "pluginQuery";
        CefMessageRouter messageRouter = CefMessageRouter.create(routerConfig,
            new MessageRouterHandler());
        jbCefClient.getCefClient().addMessageRouter(messageRouter);
    }

}

