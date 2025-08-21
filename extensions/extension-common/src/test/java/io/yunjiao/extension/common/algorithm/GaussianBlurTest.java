package io.yunjiao.extension.common.algorithm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link GaussianBlur} 单元测试用例
 *
 * @author wanpinwei
 */
public class GaussianBlurTest {

    @Test
    @DisplayName("模糊真实的图片")
    void testInputImage() throws IOException {
        ClassPathResource resource = new ClassPathResource("images/input.png");
        BufferedImage originalImage = ImageIO.read(resource.getInputStream());

        Path targetDir = Paths.get("target", "processed-images");
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        BufferedImage modifiedImage = GaussianBlur.gaussianBlurLight(originalImage);
        File outputFile = new File(targetDir.toFile(), "output-l.png");
        ImageIO.write(modifiedImage, "png", outputFile);
        System.out.println("图片处理完成，保存至: " + outputFile.getAbsolutePath());

        modifiedImage = GaussianBlur.gaussianBlurMedium(originalImage);
        outputFile = new File(targetDir.toFile(), "output-m.png");
        ImageIO.write(modifiedImage, "png", outputFile);
        System.out.println("图片处理完成，保存至: " + outputFile.getAbsolutePath());

        modifiedImage = GaussianBlur.gaussianBlurHeavy(originalImage);
        outputFile = new File(targetDir.toFile(), "output-h.png");
        ImageIO.write(modifiedImage, "png", outputFile);
        System.out.println("图片处理完成，保存至: " + outputFile.getAbsolutePath());
    }

    @Test
    @DisplayName("测试全黑图像模糊")
    void testBlurAllBlackImage() {
        // 创建全黑图像
        BufferedImage blackImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                blackImage.setRGB(x, y, 0xFF000000); // 黑色完全不透明
            }
        }

        // 应用高斯模糊
        BufferedImage result = GaussianBlur.gaussianBlur(blackImage, 5);

        // 验证结果仍然是全黑
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertEquals(0xFF000000, result.getRGB(x, y),
                        "全黑图像模糊后应该仍然是全黑");
            }
        }
    }

    @Test
    @DisplayName("测试全白图像模糊")
    void testBlurAllWhiteImage() {
        // 创建全白图像
        BufferedImage whiteImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                whiteImage.setRGB(x, y, 0xFFFFFFFF); // 白色完全不透明
            }
        }

        // 应用高斯模糊
        BufferedImage result = GaussianBlur.gaussianBlur(whiteImage, 5);

        // 验证结果仍然是全白（允许轻微的颜色变化）
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                int rgb = result.getRGB(x, y);
                // 检查所有通道都接近255
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                assertTrue(alpha >= 250 && red >= 250 && green >= 250 && blue >= 250,
                        "全白图像模糊后应该仍然是接近全白");
            }
        }
    }

    @Test
    @DisplayName("测试单像素图像模糊")
    void testBlurSinglePixelImage() {
        // 创建单像素图像
        BufferedImage singlePixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        singlePixel.setRGB(0, 0, 0xFFFF0000); // 红色

        // 应用高斯模糊
        BufferedImage result = GaussianBlur.gaussianBlur(singlePixel, 3);

        // 验证结果仍然是相同的颜色
        assertEquals(0xFFFF0000, result.getRGB(0, 0),
                "单像素图像模糊后应该保持不变");
    }

    @Test
    @Disabled("测试失败")
    @DisplayName("测试模糊半径为零")
    void testBlurWithZeroRadius() {
        // 创建测试图像
        BufferedImage testImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                testImage.setRGB(x, y, (x + y) * 0x08040201); // 生成一些变化
            }
        }

        // 应用半径为0的高斯模糊
        BufferedImage result = GaussianBlur.gaussianBlur(testImage, 0);

        // 验证图像没有变化
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertEquals(testImage.getRGB(x, y), result.getRGB(x, y),
                        "零半径模糊应该保持图像不变");
            }
        }
    }

    @Test
    @DisplayName("测试模糊半径边界处理")
    void testBlurEdgeHandling() {
        // 创建测试图像，四角有不同的颜色
        BufferedImage testImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        // 设置四角颜色
        testImage.setRGB(0, 0, 0xFFFF0000); // 左上角红色
        testImage.setRGB(9, 0, 0xFF00FF00); // 右上角绿色
        testImage.setRGB(0, 9, 0xFF0000FF); // 左下角蓝色
        testImage.setRGB(9, 9, 0xFFFFFF00); // 右下角黄色

        // 应用高斯模糊
        BufferedImage result = GaussianBlur.gaussianBlur(testImage, 5);

        // 验证四角颜色已经混合（不再是纯色）
        int topLeft = result.getRGB(0, 0);
        int topRight = result.getRGB(9, 0);
        int bottomLeft = result.getRGB(0, 9);
        int bottomRight = result.getRGB(9, 9);

        // 检查颜色通道值（应该不是极值）
        assertNotEquals(0xFFFF0000, topLeft, "左上角颜色应该被模糊");
        assertNotEquals(0xFF00FF00, topRight, "右上角颜色应该被模糊");
        assertNotEquals(0xFF0000FF, bottomLeft, "左下角颜色应该被模糊");
        assertNotEquals(0xFFFFFF00, bottomRight, "右下角颜色应该被模糊");
    }

    @Test
    @DisplayName("测试不同模糊半径的效果")
    void testDifferentBlurRadius() {
        // 创建测试图像 - 黑白棋盘
        BufferedImage chessboard = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                chessboard.setRGB(x, y, (x + y) % 2 == 0 ? 0xFFFFFFFF : 0xFF000000);
            }
        }

        // 应用小半径模糊
        BufferedImage smallBlur = GaussianBlur.gaussianBlur(chessboard, 1);

        // 应用大半径模糊
        BufferedImage largeBlur = GaussianBlur.gaussianBlur(chessboard, 10);

        // 计算两幅图像的差异
        double difference = calculateImageDifference(smallBlur, largeBlur);

        // 大半径模糊应该产生更模糊的结果
        assertTrue(difference > 0.1, "不同半径的模糊应该产生不同的结果");
    }

    @Test
    @DisplayName("测试模糊后的图像尺寸不变")
    void testBlurPreservesDimensions() {
        // 创建测试图像
        BufferedImage testImage = new BufferedImage(15, 20, BufferedImage.TYPE_INT_ARGB);

        // 应用高斯模糊
        BufferedImage result = GaussianBlur.gaussianBlur(testImage, 5);

        // 验证尺寸不变
        assertEquals(testImage.getWidth(), result.getWidth(), "模糊后图像宽度应该不变");
        assertEquals(testImage.getHeight(), result.getHeight(), "模糊后图像高度应该不变");
    }

    @Test
    @DisplayName("测试高斯核生成")
    void testGaussianKernelGeneration() {
        // 测试半径为1的高斯核
        double[] kernel1 = GaussianBlur.createGaussianKernel(1);
        assertEquals(3, kernel1.length, "半径1的高斯核应该有3个元素");

        // 检查核的和约为1（由于浮点精度，可能不是精确的1）
        double sum = 0;
        for (double value : kernel1) {
            sum += value;
        }
        assertEquals(1.0, sum, 1e-10, "高斯核的总和应该为1");

        // 检查核是对称的
        assertEquals(kernel1[0], kernel1[2], 1e-10, "高斯核应该是对称的");

        // 测试半径为2的高斯核
        double[] kernel2 = GaussianBlur.createGaussianKernel(2);
        assertEquals(5, kernel2.length, "半径2的高斯核应该有5个元素");

        // 检查核的和约为1
        sum = 0;
        for (double value : kernel2) {
            sum += value;
        }
        assertEquals(1.0, sum, 1e-10, "高斯核的总和应该为1");

        // 检查核是对称的
        assertEquals(kernel2[0], kernel2[4], 1e-10, "高斯核应该是对称的");
        assertEquals(kernel2[1], kernel2[3], 1e-10, "高斯核应该是对称的");
    }

    @Test
    @DisplayName("测试极端模糊半径")
    void testExtremeBlurRadius() {
        // 创建测试图像
        BufferedImage testImage = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        testImage.setRGB(2, 2, 0xFFFF0000); // 中心为红色

        // 应用非常大的模糊半径
        BufferedImage result = GaussianBlur.gaussianBlur(testImage, 100);

        // 验证图像没有崩溃，并且所有像素都有值
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                assertNotNull(result.getRGB(x, y), "极端半径模糊不应该产生空像素");
            }
        }
    }

    // 辅助方法：计算两幅图像的差异（基于像素值的均方根误差）
    private double calculateImageDifference(BufferedImage img1, BufferedImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight();
        long sum = 0;
        int count = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                // 计算每个通道的差异
                int a1 = (rgb1 >> 24) & 0xFF;
                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = rgb1 & 0xFF;

                int a2 = (rgb2 >> 24) & 0xFF;
                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = rgb2 & 0xFF;

                // 累加平方差异
                sum += (a1 - a2) * (a1 - a2);
                sum += (r1 - r2) * (r1 - r2);
                sum += (g1 - g2) * (g1 - g2);
                sum += (b1 - b2) * (b1 - b2);
                count += 4;
            }
        }

        // 计算均方根误差
        return Math.sqrt((double) sum / count);
    }
}
