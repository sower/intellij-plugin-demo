package me.ylem.intellij.demo.jcef;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.cef.browser.CefBrowser;

/**
 * 开发工具对话框
 *
 * @since 2024/11/02
 **/
public class DevToolsDialog extends JDialog {

    CefBrowser devTools;

    public DevToolsDialog(JFrame owner, String title, CefBrowser browser) {
        this(owner, title, browser, null);
    }

    public DevToolsDialog(JFrame owner, String title, CefBrowser browser, Point inspectAt) {
        super(owner, title, false);

        setLayout(new BorderLayout());
        // 使用Toolkit可以获得本机系统的屏幕的参数
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        // 居中显示
        setLocationRelativeTo(owner);

        // 获取当前浏览器的开发工具的实例
        devTools = browser.getDevTools(inspectAt);
        add(devTools.getUIComponent());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void dispose() {
        devTools.doClose();
        super.dispose();
    }
}
