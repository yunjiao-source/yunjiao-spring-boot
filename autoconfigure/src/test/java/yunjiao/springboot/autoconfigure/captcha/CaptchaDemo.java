package yunjiao.springboot.autoconfigure.captcha;

import com.anji.captcha.service.CaptchaCacheService;
import yunjiao.springboot.extension.common.captcha.CaptchaCategory;
import yunjiao.springboot.extension.common.captcha.CaptchaData;
import yunjiao.springboot.extension.common.captcha.CaptchaService;
import yunjiao.springboot.extension.common.util.EnumCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码示例
 *
 * @author yangyunjiao
 */
public class CaptchaDemo {
    static class CaptchaFrame extends JFrame {

        private CaptchaServiceFactory captchaServiceFactory;
        private CaptchaData currentCaptcha;

        private JComboBox<String> typeComboBox;
        private JLabel captchaImageLabel;
        private JLabel backgroundImageLabel;
        private JTextField inputField;
        private JButton verifyButton;
        private JButton refreshButton;

        public CaptchaFrame() {
            buildService();

            setTitle("验证码校验系统");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 350);
            setLocationRelativeTo(null);

            initUI();
            refreshCaptcha();
        }

        private void buildService() {
            HutoolCaptchaConfiguration hutoolCaptchaConfiguration = new HutoolCaptchaConfiguration();
            HutoolCaptchaProperties hutoolCaptchaProperties = new HutoolCaptchaProperties();
            Map<CaptchaCategory, CaptchaService> services = new HashMap<>();

            services.put(CaptchaCategory.line, hutoolCaptchaConfiguration.lineCaptchaService(hutoolCaptchaProperties));
            services.put(CaptchaCategory.circle, hutoolCaptchaConfiguration.circleCaptchaService(hutoolCaptchaProperties));
            services.put(CaptchaCategory.shear, hutoolCaptchaConfiguration.sheareCaptchaService(hutoolCaptchaProperties));
            services.put(CaptchaCategory.gif, hutoolCaptchaConfiguration.gifCaptchaService(hutoolCaptchaProperties));

            AnjiCaptchaConfiguration anjiCaptchaConfiguration = new AnjiCaptchaConfiguration();
            AnjiCaptchaProperties anjiCaptchaProperties = new AnjiCaptchaProperties();
            CaptchaCacheService captchaCacheService = com.anji.captcha.service.impl.CaptchaServiceFactory.getCache("local");
            com.anji.captcha.service.CaptchaService captchaService = anjiCaptchaConfiguration.captchaService(anjiCaptchaProperties);

            services.put(CaptchaCategory.blockPuzzle, anjiCaptchaConfiguration.blockPuzzleCaptchaService(captchaService, captchaCacheService, anjiCaptchaProperties));
            services.put(CaptchaCategory.clickWord, anjiCaptchaConfiguration.clickWorkCaptchaService(captchaService, captchaCacheService, anjiCaptchaProperties));
            captchaServiceFactory = new CaptchaServiceFactory(services);
        }

        private void initUI() {
            setLayout(new BorderLayout(10, 10));

            // 顶部面板 - 验证码类型选择
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.add(new JLabel("验证码类型:"));

            String[] captchaName = Arrays.stream(CaptchaCategory.values()).map(CaptchaCategory::getDescription).toArray(String[]::new);

            typeComboBox = new JComboBox<>(captchaName);
            topPanel.add(typeComboBox);

            refreshButton = new JButton("刷新验证码");
            refreshButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    refreshCaptcha();
                }
            });
            topPanel.add(refreshButton);

            add(topPanel, BorderLayout.NORTH);

            // 中间面板 - 验证码图片显示
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            captchaImageLabel = new JLabel();
            captchaImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            backgroundImageLabel = new JLabel();
            backgroundImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(captchaImageLabel, BorderLayout.CENTER);
            centerPanel.add(backgroundImageLabel, BorderLayout.SOUTH);

            add(centerPanel, BorderLayout.CENTER);

            // 底部面板 - 输入和验证
            JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
            bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

            JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
            inputPanel.add(new JLabel("请输入验证码:"), BorderLayout.WEST);
            inputField = new JTextField();
            inputPanel.add(inputField, BorderLayout.CENTER);

            verifyButton = new JButton("验证");
            verifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    verifyCaptcha();
                }
            });
            inputPanel.add(verifyButton, BorderLayout.EAST);

            bottomPanel.add(inputPanel, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        private void refreshCaptcha() {
            String selectedType = (String) typeComboBox.getSelectedItem();
            CaptchaCategory category = EnumCache.getInstance()
                    .lookupByValue(CaptchaCategory.class, selectedType, CaptchaCategory::getDescription);
            currentCaptcha = captchaServiceFactory.findService(category).draw();

            try {
                ImageIcon icon = new ImageIcon(currentCaptcha.captchaImage());
                captchaImageLabel.setIcon(icon);

                if (currentCaptcha.backgroundImage() != null) {
                    icon = new ImageIcon(currentCaptcha.backgroundImage());
                    backgroundImageLabel.setIcon(icon);
                } else {
                    backgroundImageLabel.setIcon(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "加载验证码图片失败", "错误", JOptionPane.ERROR_MESSAGE);
            }

            inputField.setText("");
        }

        private void verifyCaptcha() {
            String userInput = inputField.getText().trim();
            if (userInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入验证码", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String selectedType = (String) typeComboBox.getSelectedItem();
            CaptchaCategory category = EnumCache.getInstance()
                    .lookupByValue(CaptchaCategory.class, selectedType, CaptchaCategory::getDescription);

            boolean isValid = captchaServiceFactory.findService(category).verify(currentCaptcha.code(), userInput);
            if (isValid) {
                JOptionPane.showMessageDialog(this, "验证码正确!", "成功", JOptionPane.INFORMATION_MESSAGE);
                refreshCaptcha();
            } else {
                JOptionPane.showMessageDialog(this, "验证码错误，请重新输入", "错误", JOptionPane.ERROR_MESSAGE);
                inputField.setText("");
                inputField.requestFocus();
            }
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CaptchaFrame().setVisible(true));
    }
}
