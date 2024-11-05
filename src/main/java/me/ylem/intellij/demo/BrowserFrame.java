package me.ylem.intellij.demo;

import com.jetbrains.cef.JCefAppConfig;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.Serial;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import lombok.SneakyThrows;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefRendering;

/**
 * BrowserFrame
 *
 * @since 2024/09/08
 **/
public class BrowserFrame extends JFrame {

    @Serial
    private static final long serialVersionUID = 2541887783679247552L;

    private static volatile BrowserFrame instance;

    private String url = "https://map.baidu.com/";

    private CefApp app;
    private CefClient client;
    private CefBrowser browser;

    @SneakyThrows
    public BrowserFrame() {
        var args = JCefAppConfig.getInstance().getAppArgs();
        var settings = JCefAppConfig.getInstance().getCefSettings();
        settings.cache_path = System.getProperty("user.dir") + "/context";
        // 获取CefApp实例
        CefApp.startup(args);
        app = CefApp.getInstance(args, settings);
        // 创建客户端实例
        client = app.createClient();
        // 创建浏览器实例
        browser = client.createBrowser(url, CefRendering.DEFAULT, true);

        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.getContentPane().add(browser.getUIComponent(), BorderLayout.CENTER);
        BufferedImage image = ImageIO.read(this.getClass().getResource("/META-INF/pluginIcon.svg"));
        this.setIconImage(image);
        this.setTitle("JetBrains Runtime - JCEF");
        this.setSize(new Dimension(1280, 720));
        this.setMinimumSize(new Dimension(1150, 650));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2,
            (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
        this.setVisible(true);
        this.setResizable(true);
        // this.addWindowListener(new WindowAdapter() {
        //     @Override
        //     public void windowClosing(WindowEvent e) {
        //         app.dispose();
        //     }
        // });

    }

    public static BrowserFrame getInstance() {
        if (instance == null) {
            synchronized (BrowserFrame.class) {
                if (instance == null) {
                    instance = new BrowserFrame();
                }
            }
        }
        return instance;
    }

}
