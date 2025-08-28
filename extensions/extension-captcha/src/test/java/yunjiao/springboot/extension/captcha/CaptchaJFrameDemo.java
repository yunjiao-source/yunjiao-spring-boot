package yunjiao.springboot.extension.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.anji.captcha.model.common.Const;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import yunjiao.springboot.extension.captcha.anji.BlockPuzzleCaptchaService;
import yunjiao.springboot.extension.captcha.anji.ClickWorkCaptchaService;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.model.ColorType;
import yunjiao.springboot.extension.captcha.hutool.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

/**
 * 可视化界面
 *
 * @author yangyunjiao
 */
public class CaptchaJFrameDemo extends JFrame  {
    private JPanel captchaPanel;

    private JButton generateButton;

    private LineCaptchaService lineCaptchaService;

    private CircleCaptchaService circleCaptchaService;

    private ShearCaptchaService shearCaptchaService;

    private GifCaptchaService gifCaptchaService;

    private BlockPuzzleCaptchaService blockPuzzleCaptchaService;

    private ClickWorkCaptchaService clickWorkCaptchaService;

    public CaptchaJFrameDemo() {
        setTitle("验证码生成器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        initUI();
        initService();

        // 初始生成验证码
        generateCaptchas();
    }

    private void initService() {
        Font font = new Font(null, Font.PLAIN, 36);
        LineCaptchaBuilder lcb = new LineCaptchaBuilder();
        lcb.setWidth(250);
        lcb.setHeight(50);
        lcb.setInterfereCount(60);
        lcb.setBackgroundColor(ColorType.white);
        lcb.setFuzziness(2);
        lcb.setValidIgnoreCase(true);
        lcb.setFont(font);
        lcb.setGenerator(CodeGeneratorType.numAndChar.apply(5));
        lineCaptchaService = new LineCaptchaService(lcb);

        CircleCaptchaBuilder ccb = new CircleCaptchaBuilder();
        ccb.setWidth(250);
        ccb.setHeight(50);
        ccb.setInterfereCount(30);
        ccb.setBackgroundColor(ColorType.white);
        ccb.setFuzziness(2);
        ccb.setValidIgnoreCase(true);
        ccb.setFont(font);
        ccb.setGenerator(CodeGeneratorType.numAndChar.apply(5));
        circleCaptchaService = new CircleCaptchaService(ccb);

        ShearCaptchaBuilder scb = new ShearCaptchaBuilder();
        scb.setWidth(250);
        scb.setHeight(50);
        scb.setInterfereCount(4);
        scb.setBackgroundColor(ColorType.white);
        scb.setFuzziness(2);
        scb.setValidIgnoreCase(true);
        scb.setFont(font);
        scb.setGenerator(CodeGeneratorType.numAndChar.apply(5));

        shearCaptchaService = new ShearCaptchaService(scb);

        GifCaptchaBuilder gcb = new GifCaptchaBuilder();
        gcb.setWidth(250);
        gcb.setHeight(50);
        gcb.setInterfereCount(10);
        gcb.setBackgroundColor(ColorType.white);
        gcb.setFuzziness(2);
        gcb.setValidIgnoreCase(true);
        gcb.setFont(font);
        gcb.setGenerator(CodeGeneratorType.numAndChar.apply(5));
        gcb.setQuality(10);
        gcb.setRepeat(0);
        gcb.setMinColor(0);
        gcb.setMaxColor(255);

        gifCaptchaService = new GifCaptchaService(gcb);

        Properties config = new Properties();
        config.put(Const.CAPTCHA_CACHETYPE, "local");
        config.put(Const.CAPTCHA_WATER_MARK, "我的水印");
        config.put(Const.CAPTCHA_FONT_TYPE, "WenQuanZhengHei.ttf");
        config.put(Const.CAPTCHA_TYPE, "default");
        config.put(Const.CAPTCHA_INTERFERENCE_OPTIONS, "0");
        config.put(Const.ORIGINAL_PATH_JIGSAW, "");
        config.put(Const.ORIGINAL_PATH_PIC_CLICK, "");
        config.put(Const.CAPTCHA_SLIP_OFFSET, "5");
        config.put(Const.CAPTCHA_AES_STATUS, "false");
        config.put(Const.CAPTCHA_WATER_FONT, "WenQuanZhengHei.ttf");
        // CacheUtil的定时任务存在泄露的问题
        config.put(Const.CAPTCHA_TIMING_CLEAR_SECOND, "0");

        config.put(Const.CAPTCHA_FONT_SIZE, "25");
        config.put(Const.CAPTCHA_FONT_STYLE, Font.BOLD);
        config.put(Const.CAPTCHA_WORD_COUNT, "4");

        CaptchaService captchaService = CaptchaServiceFactory.getInstance(config);
        CaptchaCacheService captchaCacheService = CaptchaServiceFactory.getCache("local");
        blockPuzzleCaptchaService = new BlockPuzzleCaptchaService(captchaService,
                captchaCacheService,
                5);
        clickWorkCaptchaService = new ClickWorkCaptchaService(captchaService,
                captchaCacheService,
                5);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建验证码显示面板
        captchaPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        captchaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(captchaPanel, BorderLayout.CENTER);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        generateButton = new JButton("生成验证码");
        generateButton.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCaptchas();
            }
        });
        buttonPanel.add(generateButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void generateCaptchas() {
        captchaPanel.removeAll();

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(250, 50);
        ImageIcon icon = new ImageIcon(lineCaptcha.getImageBytes());

        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        captchaPanel.add(label);

        CaptchaData captchaData = lineCaptchaService.draw();
        decodeImage(captchaData);

        captchaData = circleCaptchaService.draw();
        decodeImage(captchaData);

        captchaData = shearCaptchaService.draw();
        decodeImage(captchaData);

        captchaData = gifCaptchaService.draw();
        decodeImage(captchaData);

        captchaData = blockPuzzleCaptchaService.draw();
        decodeImage(captchaData);

        captchaData = clickWorkCaptchaService.draw();
        decodeImage(captchaData);

        captchaPanel.revalidate();
        captchaPanel.repaint();
    }

    private void decodeImage(CaptchaData captchaData) {
        ImageIcon icon = new ImageIcon(captchaData.captchaImage());

        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        captchaPanel.add(label);

        if (captchaData.backgroundImage() != null) {
            icon = new ImageIcon(captchaData.backgroundImage());

            label = new JLabel(icon);
            label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            captchaPanel.add(label);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new CaptchaJFrameDemo().setVisible(true);
            }
        });
    }
}
