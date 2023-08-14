SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `db_admin`;
CREATE TABLE `db_admin`
(
    `id`              int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id`         int(11) NOT NULL COMMENT '用户ID',
    `admin_level`     char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'normal' COMMENT '用户等级',
    `permission_time` datetime(0) NOT NULL COMMENT '权限升级时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `db_user`;
CREATE TABLE `db_user`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT 'default_user' COMMENT '用户名',
    `password`      varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `phone`         char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci      NOT NULL COMMENT '手机号',
    `email`         varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL COMMENT '电子邮箱',
    `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `email`(`email`) USING BTREE,
    UNIQUE INDEX `phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;