package io.yunjiao.extension.common.algorithm;

import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * {@link GaussianBlur} 可视化界面
 *
 * @author yangyunjiao
 */
public class GaussianBlurJFrameDemo extends JFrame {
    private BufferedImage originalImage;
    private BufferedImage blurredImage;
    private final JLabel imageLabel;
    private final JSlider radiusSlider;
    private final JComboBox<String> intensityComboBox;

    // 模糊强度枚举
    @Getter
    public enum BlurIntensity {
        LIGHT(1, 3),
        MEDIUM(4, 7),
        HEAVY(8, 15);

        private final int minRadius;
        private final int maxRadius;

        BlurIntensity(int minRadius, int maxRadius) {
            this.minRadius = minRadius;
            this.maxRadius = maxRadius;
        }

        public int getRadius() {
            return (minRadius + maxRadius) / 2; // 返回中间值作为默认半径
        }

    }

    public GaussianBlurJFrameDemo() {
        setTitle("Java高斯模糊算法 - 支持强度设置");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // 创建界面组件
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane(imageLabel);

        // 模糊强度选择
        intensityComboBox = new JComboBox<>(new String[]{"轻度模糊", "中度模糊", "重度模糊", "自定义"});
        intensityComboBox.setSelectedIndex(1); // 默认选择中度模糊
        intensityComboBox.addActionListener(e -> updateRadiusBasedOnIntensity());

        // 半径滑块
        radiusSlider = new JSlider(1, 30, BlurIntensity.MEDIUM.getRadius());
        radiusSlider.setMajorTickSpacing(5);
        radiusSlider.setMinorTickSpacing(1);
        radiusSlider.setPaintTicks(true);
        radiusSlider.setPaintLabels(true);
        radiusSlider.setBorder(BorderFactory.createTitledBorder("模糊半径"));

        // 更新滑块范围基于强度选择
        //updateRadiusRange();

        JButton openButton = new JButton("打开图片");
        JButton saveButton = new JButton("保存图片");

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        // 强度选择面板
        JPanel intensityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        intensityPanel.add(new JLabel("模糊强度:"));
        intensityPanel.add(intensityComboBox);

        // 控制面板
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(intensityPanel, BorderLayout.NORTH);
        controlPanel.add(radiusSlider, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 主布局
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // 添加事件监听器
        openButton.addActionListener(e -> openImage());
        saveButton.addActionListener(e -> saveImage());
        radiusSlider.addChangeListener(e -> {
            System.out.println(e);
            applyBlur();
        });
    }

    private void updateRadiusBasedOnIntensity() {
        int selectedIndex = intensityComboBox.getSelectedIndex();
        //updateRadiusRange();

        if (selectedIndex < 3) { // 不是"自定义"
            BlurIntensity intensity = BlurIntensity.values()[selectedIndex];
            radiusSlider.setValue(intensity.getRadius());
        }
    }

    private void updateRadiusRange() {
        int selectedIndex = intensityComboBox.getSelectedIndex();
        if (selectedIndex < 3) {
            BlurIntensity intensity = BlurIntensity.values()[selectedIndex];
            radiusSlider.setMinimum(intensity.getMinRadius());
            radiusSlider.setMaximum(intensity.getMaxRadius());
        } else {
            // 自定义模式下使用完整范围
            radiusSlider.setMinimum(1);
            radiusSlider.setMaximum(30);
        }
    }

    private void openImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("选择图片");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                originalImage = ImageIO.read(file);
                displayImage(originalImage);
                applyBlur();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "无法加载图片: " + ex.getMessage(),
                        "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveImage() {
        if (blurredImage == null) {
            JOptionPane.showMessageDialog(this, "没有图片可保存",
                    "警告", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("保存图片");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                ImageIO.write(blurredImage, "png", file);
                JOptionPane.showMessageDialog(this, "图片保存成功!",
                        "成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "无法保存图片: " + ex.getMessage(),
                        "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void applyBlur() {
        if (originalImage == null) return;

        int radius = radiusSlider.getValue();
        long startTime = System.currentTimeMillis();
        blurredImage = GaussianBlur.execute(originalImage, radius);
        long stopTome = System.currentTimeMillis();
        System.out.println("耗时：" + (stopTome - startTime)/1000);
        displayImage(blurredImage);
    }

    private void displayImage(BufferedImage image) {
        Image scaledImage = image.getScaledInstance(
                Math.min(800, image.getWidth()),
                Math.min(600, image.getHeight()),
                Image.SCALE_SMOOTH
        );
        ImageIcon icon = new ImageIcon(scaledImage);
        imageLabel.setIcon(icon);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GaussianBlurJFrameDemo app = new GaussianBlurJFrameDemo();
            app.setVisible(true);
        });
    }
}
