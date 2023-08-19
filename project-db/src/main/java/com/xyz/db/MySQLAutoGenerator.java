package com.xyz.db;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @author ZKKzs
 * @since 2023/8/2
 **/
public class MySQLAutoGenerator {
    // todo: mysql
    private static final String url = "jdbc:mysql://localhost:3306/java_base?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";

    // todo: username
    private static final String userName = "your_username";

    // todo: password
    private static final String password = "your_password";

    // todo: your name(author)
    private static final String author = "ZKKzs";

    // todo: modify absolute path
    /**
     * project path (absolute)
     */
    private static final String absolutePath = "E:\\Project\\JavaBase";

    // todo: db module path
    /**
     * db module path
     */
    private static final String dbPath = "\\project-db\\src\\main\\java\\com\\xyz\\db\\mysql\\xml";

    // todo: web module path
    /**
     * web module path
     */
    private static final String webPath = "\\project-web\\src\\main\\java";

    // todo: web package
    /**
     * web module package
     */
    private static final String parent = "com.xyz.web";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder ->
                        builder.author(author)
                                .fileOverride()
                                .outputDir(absolutePath + webPath))
                .packageConfig(builder ->
                        builder.parent(parent)
                                .pathInfo(Collections.singletonMap(OutputFile.xml, absolutePath + dbPath)))
                .strategyConfig(builder ->
                        // todo: table name @database: java_base.sql
                        builder.addInclude("db_admin","db_user"))
                .execute();
    }
}
