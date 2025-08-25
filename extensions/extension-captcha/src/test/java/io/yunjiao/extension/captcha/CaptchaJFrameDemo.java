package io.yunjiao.extension.captcha;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import io.yunjiao.extension.captcha.hutool.*;
import io.yunjiao.extension.common.captcha.CaptchaData;
import io.yunjiao.extension.common.model.ColorType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public CaptchaJFrameDemo() {
        setTitle("验证码生成器");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 300);
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
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // 创建验证码显示面板
        captchaPanel = new JPanel(new GridLayout(3, 2, 10, 10));
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

        captchaPanel.revalidate();
        captchaPanel.repaint();
    }

    private void decodeImage(CaptchaData captchaData) {
        ImageIcon icon = new ImageIcon(captchaData.getCaptchaImage());

        JLabel label = new JLabel(icon);
        label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        captchaPanel.add(label);
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
