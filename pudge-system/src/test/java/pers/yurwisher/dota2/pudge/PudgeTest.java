package pers.yurwisher.dota2.pudge;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author yq
 * @date 2020/09/21 14:18
 * @description 测试
 * @since V1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PudgeTest {

    public static void main(String[] args) {
        File file = FileUtil.file("D:\\attachment\\dev\\2020-09-06\\1599444966250144575.jpg");
        String type = FileTypeUtil.getType(file);
        System.out.println(type);
    }
}
