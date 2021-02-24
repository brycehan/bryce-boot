package com.brycehan.boot.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Scanner;

/**
 * @author Bryce Han
 * @date 2021/2/23
 */
public class CodeGenerator {
    private static final String URL = "jdbc:mysql://192.168.0.104:3306/bryce_boot?serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String AUTHOR = "Bryce Han";
    private static final String OUTPUT_DIR = "D://generator";
    private static final String PARENT_PACKAGE = "com.brycehan.boot";
    private static final String TABLE_PREFIX = "brc";

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setUrl(URL);
        dataSource.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        autoGenerator.setDataSource(dataSource);

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(AUTHOR);
        globalConfig.setFileOverride(true);
        globalConfig.setOutputDir(OUTPUT_DIR);
        globalConfig.setOpen(false);
        globalConfig.setServiceName("%sService");
        globalConfig.setBaseResultMap(true);
        globalConfig.setBaseColumnList(true);
        autoGenerator.setGlobalConfig(globalConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(PARENT_PACKAGE);
        packageConfig.setModuleName(scanner("模块名"));
        autoGenerator.setPackageInfo(packageConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setEntitySerialVersionUID(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setInclude(scanner("表名（多个表名时，英文逗号分隔）"));
        strategyConfig.setTablePrefix(TABLE_PREFIX);
        autoGenerator.setStrategy(strategyConfig);

        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        autoGenerator.execute();
    }

    /**
     * 从控制台读取参数
     * @param tip
     * @return
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入");
        help.append(tip);
        help.append("：");

        System.out.println(help);
        if(scanner.hasNext()){
            String result = scanner.next();
            if(StringUtils.isNotBlank(result)){
                return result;
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}
