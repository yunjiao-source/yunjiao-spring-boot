package yunjiao.springboot.example.tika;

import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * 示例
 *
 * @author yangyunjiao
 */
@Slf4j
@Component
public class TikaCommandLineRunner implements CommandLineRunner {
    @Autowired
    private Tika tika;

    @Override
    public void run(String... args) throws Exception {
        Assert.notNull(tika, "Tika 配置失败");
    }


}
