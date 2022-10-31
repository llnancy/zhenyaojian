package com.sunchaser.shushan.zhenyaojian.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static com.baomidou.mybatisplus.generator.config.rules.DbColumnType.INTEGER;

/**
 * mybatis plus 3.5.1 版本以上的代码生成器
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2022/3/9
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create(new DataSourceConfig
                        .Builder("jdbc:mysql://127.0.0.1:3306/sunchaser_zyj?useUnicode=true&characterEncoding=UTF-8&useSSL=false", "root", "123456")
                        // 自定义类型转换器：tinyint 生成 Integer 替换默认的 Boolean
                        .typeConvert(new MySqlTypeConvert() {
                            @Override
                            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                                IColumnType columnType = super.processTypeConvert(globalConfig, fieldType);
                                System.out.println(fieldType);
                                if (fieldType.contains("tinyint")) {
                                    columnType = INTEGER;
                                }
                                return columnType;
                            }
                        })
                        // 处理数据库关键字
                        .keyWordsHandler(new MySqlKeyWordsHandler())
                )
                .globalConfig(builder -> {
                    builder.author("sunchaser admin@lilu.org.cn") // 设置类文件头部注释的作者
                            .fileOverride() // 覆盖已生成文件（即将过时）3.5.2 版本不会进行覆盖
                            .commentDate(() -> "JDK8 " + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))) // 设置类文件头部注释的时间
                            .dateType(DateType.TIME_PACK) // 使用Java8的新时间类型LocalDateTime
                            .outputDir("./zhenyaojian-system/src/main/java"); // 指定输出目录（相对 or 绝对路径均可）
                })
                .packageConfig(builder -> {
                    builder.parent("com.sunchaser.shushan") // 设置父包名
                            .moduleName("zhenyaojian") // 设置父包模块名
                            .entity("repository.entity") // entity 包名
                            .mapper("repository.mapper") // mapper 包名
                            .service("service") // service 包名
                            .serviceImpl("service.impl") // service impl 包名
                            .controller("web.controller") // controller 包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "./zhenyaojian-system/src/main/resources/mapper")); // 指定 xml 文件生成的路径
                })
                .strategyConfig(builder -> {
                    builder.addTablePrefix("zyj_")
                            .entityBuilder()
                            .enableLombok() // 开启lombok
                            .formatFileName("%sEntity")
                            .logicDeleteColumnName("status") // 逻辑删除字段
                            // .superClass("com.sunchaser.shushan.wulingzhu.repository.entity.BaseEntity")
                            // .addSuperEntityColumns("id", "create_time", "update_time", "state")// 设置实体公共父类字段
                            .controllerBuilder()
                            .enableHyphenStyle() // 驼峰转连字符-
                            .enableRestStyle() // 使用@RestController
                            .serviceBuilder()
                            .formatServiceFileName("%sService");// service接口前面不带I
                })
                .templateConfig(builder -> {
                    builder.entity("/templates/entity.java") // 配置自定义的 entity 模板位置（不用带 .ftl 模板引擎后缀名），使用 @Data 注解
                            .mapper("/templates/mapper.java") // 自定义 mapper 模板位置，去掉默认的 <p></p> 标签
                            .service("/templates/service.java") // 自定义 service 模板位置，去掉默认的 <p></p> 标签
                            .serviceImpl("/templates/serviceImpl.java") // 自定义 serviceImpl 模板位置，去掉默认的 <p></p> 标签
                            .controller("/templates/controller.java"); // 自定义 controller 模板位置，去掉默认的 <p></p> 标签
                })
                // 使用 Freemarker 引擎模板，默认的是 Velocity 引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
