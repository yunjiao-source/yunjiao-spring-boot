package yunjiao.springboot.extension.common.util;

import yunjiao.springboot.extension.common.model.FontNameEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * {@link FontCache} 单元测试用例
 *
 * @author yangyunjiao
 */
public class FontCacheDemo {
    static void listSystemFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        System.out.println("可用的字体系列:");
        String[] fontFamilies = ge.getAvailableFontFamilyNames();
        for (int i = 0; i < 300 && i < fontFamilies.length; i++) { // 只打印前10个
            System.out.println(" - " + fontFamilies[i]);
        }
        System.out.println("... (总共: " + fontFamilies.length + " 种)");

    }

    static class FontSelector extends JFrame {
        private JComboBox<String> fontComboBox;
        private JList<Integer> sizeList;
        private JCheckBox boldCheckBox, italicCheckBox;
        private JTextArea previewTextArea;
        private JLabel previewLabel;

        public FontSelector() {
            listSystemFont();

            setTitle("字体选择器");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 500);
            setLocationRelativeTo(null);

            initUI();

            setVisible(true);
        }

        private void initUI() {
            // 主面板
            JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // 控制面板
            JPanel controlPanel = new JPanel(new GridLayout(2, 1, 5, 5));

            // 字体选择面板
            JPanel fontPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fontPanel.setBorder(BorderFactory.createTitledBorder("选择字体"));

            // 获取系统可用字体
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = Arrays.stream(FontNameEnum.values()).map(FontNameEnum::getName).toArray(String[]::new);


            fontComboBox = new JComboBox<>(fontNames);
            fontComboBox.setSelectedIndex(0);
            fontComboBox.addActionListener(e -> updateFont());

            fontPanel.add(new JLabel("字体:"));
            fontPanel.add(fontComboBox);

            // 大小和样式面板
            JPanel sizeStylePanel = new JPanel(new GridLayout(1, 2, 10, 5));

            // 字体大小
            JPanel sizePanel = new JPanel(new BorderLayout());
            sizePanel.setBorder(BorderFactory.createTitledBorder("选择大小"));

            Integer[] sizes = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
            sizeList = new JList<>(sizes);
            sizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            sizeList.setSelectedValue(16, true);
            sizeList.addListSelectionListener(e -> updateFont());

            JScrollPane sizeScrollPane = new JScrollPane(sizeList);
            sizeScrollPane.setPreferredSize(new Dimension(100, 100));
            sizePanel.add(sizeScrollPane, BorderLayout.CENTER);

            // 字体样式
            JPanel stylePanel = new JPanel(new GridLayout(2, 1, 5, 5));
            stylePanel.setBorder(BorderFactory.createTitledBorder("选择样式"));

            boldCheckBox = new JCheckBox("粗体");
            boldCheckBox.addActionListener(e -> updateFont());

            italicCheckBox = new JCheckBox("斜体");
            italicCheckBox.addActionListener(e -> updateFont());

            stylePanel.add(boldCheckBox);
            stylePanel.add(italicCheckBox);

            sizeStylePanel.add(sizePanel);
            sizeStylePanel.add(stylePanel);

            controlPanel.add(fontPanel);
            controlPanel.add(sizeStylePanel);

            // 预览面板
            JPanel previewPanel = new JPanel(new BorderLayout(5, 5));
            previewPanel.setBorder(BorderFactory.createTitledBorder("预览"));

            previewTextArea = new JTextArea("这是一段示例文本，用于展示字体效果。\n" +
                    "The quick brown fox jumps over the lazy dog.\n" +
                    "1234567890");
            previewTextArea.setLineWrap(true);
            previewTextArea.setWrapStyleWord(true);

            previewLabel = new JLabel("字体预览: ABCabc 123");
            previewLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JTabbedPane previewTabbedPane = new JTabbedPane();
            previewTabbedPane.addTab("文本区域", new JScrollPane(previewTextArea));
            previewTabbedPane.addTab("标签", previewLabel);

            previewPanel.add(previewTabbedPane, BorderLayout.CENTER);

            // 添加到主面板
            mainPanel.add(controlPanel, BorderLayout.NORTH);
            mainPanel.add(previewPanel, BorderLayout.CENTER);

            add(mainPanel);

            // 初始化字体
            updateFont();
        }

        private void updateFont() {
            String fontName = (String) fontComboBox.getSelectedItem();
            int fontSize = sizeList.getSelectedValue();
            int fontStyle = Font.PLAIN;

            if (boldCheckBox.isSelected()) {
                fontStyle |= Font.BOLD;
            }
            if (italicCheckBox.isSelected()) {
                fontStyle |= Font.ITALIC;
            }

            Font selectedFont = FontCache.getInstance().getFont(fontName, fontStyle, fontSize);

            previewTextArea.setFont(selectedFont);
            previewLabel.setFont(selectedFont);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FontSelector().setVisible(true));
    }

}
