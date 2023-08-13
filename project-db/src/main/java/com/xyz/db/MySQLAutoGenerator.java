package com.xyz.db;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @author ZKKzs
 * @since 2023/8/2
 **/
public class MySQLAutoGenerator {
    private static final String url = "jdbc:mysql://localhost:3306/java_base?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";

    private static final String userName = "your_username";

    private static final String password = "your_password";

    private static final String author = "ZKKzs";

    /**
     * project path (absolute)
     */
    private static final String absolutePath = "E:\\Project\\JavaBase";

    /**
     * db module path
     */
    private static final String dbPath = "\\project-db\\src\\main\\java\\com\\xyz\\db\\mysql\\xml";

    /**
     * web module path
     */
    private static final String webPath = "\\project-web\\src\\main\\java";

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
                        builder.addInclude("db_admin","db_user"))
                .execute();
    }
}
