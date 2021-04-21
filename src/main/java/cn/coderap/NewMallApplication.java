package cn.coderap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by yw
 * 2021/4/16
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.coderap.mapper"})
public class NewMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewMallApplication.class, args);
    }
}
