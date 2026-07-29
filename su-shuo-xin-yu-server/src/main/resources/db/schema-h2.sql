-- ============================================================
-- 塑说心语 (Shuo Xin Yu) - 大吴泥塑文化传承平台
-- MySQL 8.0 数据库初始化脚本
-- ============================================================

-- ============================================================
-- 1. 用户表 (user)
-- ============================================================
CREATE TABLE IF NOT EXISTS `user` (
    `id`            BIGINT          NOT NULL AUTO_INCREMENT  COMMENT '用户ID',
    `openid`        VARCHAR(64)     DEFAULT NULL             COMMENT '微信OpenID',
    `nickname`      VARCHAR(64)     DEFAULT NULL             COMMENT '昵称',
    `avatar`        VARCHAR(512)    DEFAULT NULL             COMMENT '头像URL',
    `phone`         VARCHAR(20)     DEFAULT NULL             COMMENT '手机号',
    `gender`        TINYINT         DEFAULT 0               COMMENT '性别: 0-未知 1-男 2-女',
    `role`          VARCHAR(32)     NOT NULL DEFAULT 'visitor' COMMENT '角色: visitor/admin',
    `password`      VARCHAR(128)    DEFAULT NULL             COMMENT '密码(管理员)',
    `birth_year`    INT             DEFAULT NULL             COMMENT '出生年份',
    `province`      VARCHAR(32)     DEFAULT NULL             COMMENT '省份',
    `city`          VARCHAR(32)     DEFAULT NULL             COMMENT '城市',
    `last_login_at` DATETIME        DEFAULT NULL             COMMENT '最后登录时间',
    `deleted`       TINYINT         NOT NULL DEFAULT 0       COMMENT '逻辑删除: 0-否 1-是',
    `version`       INT             NOT NULL DEFAULT 0       COMMENT '乐观锁版本号',
    `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_openid` (`openid`),
    UNIQUE KEY `uk_phone` (`phone`),
    INDEX `user_idx_role` (`role`),
    INDEX `user_idx_create_time` (`create_time`)
) ;

-- ============================================================
-- 2. 轮播图表 (banner)
-- ============================================================
CREATE TABLE IF NOT EXISTS `banner` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '轮播图ID',
    `title`       VARCHAR(128) NOT NULL                  COMMENT '标题',
    `image_url`   VARCHAR(512) NOT NULL                  COMMENT '图片URL',
    `link_url`    VARCHAR(512) DEFAULT NULL              COMMENT '跳转链接',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序(越小越前)',
    `status`      TINYINT      NOT NULL DEFAULT 1        COMMENT '状态: 0-禁用 1-启用',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `banner_idx_status_sort` (`status`, `sort_order`)
) ;

-- ============================================================
-- 3. 文章分类表 (article_category)
-- ============================================================
CREATE TABLE IF NOT EXISTS `article_category` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '分类ID',
    `name`        VARCHAR(64)  NOT NULL                  COMMENT '分类名称',
    `description` VARCHAR(256) DEFAULT NULL              COMMENT '分类描述',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `status`      TINYINT      NOT NULL DEFAULT 1        COMMENT '状态: 0-禁用 1-启用',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `article_category_idx_status_sort` (`status`, `sort_order`)
) ;

-- ============================================================
-- 4. 文章表 (article)
-- ============================================================
CREATE TABLE IF NOT EXISTS `article` (
    `id`          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '文章ID',
    `category_id` BIGINT        NOT NULL                  COMMENT '分类ID',
    `title`       VARCHAR(256)  NOT NULL                  COMMENT '文章标题',
    `summary`     VARCHAR(512)  DEFAULT NULL              COMMENT '文章摘要',
    `cover_url`   VARCHAR(512)  DEFAULT NULL              COMMENT '封面图URL',
    `content`     CLOB      DEFAULT NULL              COMMENT '文章内容(HTML)',
    `author`      VARCHAR(64)   DEFAULT NULL              COMMENT '作者',
    `source`      VARCHAR(128)  DEFAULT NULL              COMMENT '来源',
    `view_count`  BIGINT        NOT NULL DEFAULT 0        COMMENT '浏览次数',
    `like_count`  BIGINT        NOT NULL DEFAULT 0        COMMENT '点赞数',
    `status`      TINYINT       NOT NULL DEFAULT 1        COMMENT '状态: 0-草稿 1-发布',
    `is_top`      TINYINT       NOT NULL DEFAULT 0        COMMENT '是否置顶: 0-否 1-是',
    `publish_time` DATETIME     DEFAULT NULL              COMMENT '发布时间',
    `deleted`     TINYINT       NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT           NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `article_idx_category` (`category_id`),
    INDEX `article_idx_status_time` (`status`, `publish_time`),
    INDEX `article_idx_is_top` (`is_top`, `status`),
    CONSTRAINT `article_fk_article_category` FOREIGN KEY (`category_id`) REFERENCES `article_category`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ;

-- ============================================================
-- 5. 生肖表 (zodiac)
-- ============================================================
CREATE TABLE IF NOT EXISTS `zodiac` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '生肖ID',
    `name`            VARCHAR(32)  NOT NULL                  COMMENT '生肖名称',
    `alias`           VARCHAR(32)  DEFAULT NULL              COMMENT '别名',
    `image_url`       VARCHAR(512) DEFAULT NULL              COMMENT '生肖泥塑图片URL',
    `description`     TEXT         DEFAULT NULL              COMMENT '生肖描述',
    `personality`     TEXT         DEFAULT NULL              COMMENT '性格特征',
    `lucky_color`     VARCHAR(32)  DEFAULT NULL              COMMENT '幸运色',
    `lucky_number`    VARCHAR(16)  DEFAULT NULL              COMMENT '幸运数字',
    `element`         VARCHAR(16)  DEFAULT NULL              COMMENT '五行属性',
    `sort_order`      INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `view_count`      BIGINT       NOT NULL DEFAULT 0        COMMENT '浏览次数',
    `status`          TINYINT      NOT NULL DEFAULT 1        COMMENT '状态',
    `deleted`         TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`         INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `zodiac_idx_sort` (`sort_order`)
) ;

-- ============================================================
-- 6. 生肖故事表 (zodiac_story)
-- ============================================================
CREATE TABLE IF NOT EXISTS `zodiac_story` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '故事ID',
    `zodiac_id`   BIGINT       NOT NULL                  COMMENT '生肖ID',
    `title`       VARCHAR(256) NOT NULL                  COMMENT '故事标题',
    `content`     CLOB     DEFAULT NULL              COMMENT '故事内容(HTML)',
    `image_url`   VARCHAR(512) DEFAULT NULL              COMMENT '故事配图URL',
    `video_url`   VARCHAR(512) DEFAULT NULL              COMMENT '视频URL',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `zodiac_story_idx_zodiac` (`zodiac_id`),
    CONSTRAINT `zodiac_story_fk_story_zodiac` FOREIGN KEY (`zodiac_id`) REFERENCES `zodiac`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ;

-- ============================================================
-- 7. 泥塑作品表 (clay_sculpture)
-- ============================================================
CREATE TABLE IF NOT EXISTS `clay_sculpture` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '作品ID',
    `zodiac_id`   BIGINT       DEFAULT NULL              COMMENT '关联生肖ID',
    `name`        VARCHAR(128) NOT NULL                  COMMENT '作品名称',
    `description` TEXT         DEFAULT NULL              COMMENT '作品描述',
    `image_url`   VARCHAR(512) NOT NULL                  COMMENT '作品图片URL',
    `craft_type`  VARCHAR(64)  DEFAULT NULL              COMMENT '工艺类型: 捏/贴/塑/彩',
    `artist`      VARCHAR(64)  DEFAULT NULL              COMMENT '作者/传承人',
    `year`        INT          DEFAULT NULL              COMMENT '创作年份',
    `material`    VARCHAR(64)  DEFAULT NULL              COMMENT '材质',
    `size_desc`   VARCHAR(64)  DEFAULT NULL              COMMENT '尺寸描述',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `status`      TINYINT      NOT NULL DEFAULT 1        COMMENT '状态',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `clay_sculpture_idx_zodiac` (`zodiac_id`),
    INDEX `clay_sculpture_idx_status_sort` (`status`, `sort_order`),
    CONSTRAINT `clay_sculpture_fk_sculpture_zodiac` FOREIGN KEY (`zodiac_id`) REFERENCES `zodiac`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ;

-- ============================================================
-- 8. 性格测试题目表 (personality_question)
-- ============================================================
CREATE TABLE IF NOT EXISTS `personality_question` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '题目ID',
    `question_text` VARCHAR(512) NOT NULL                  COMMENT '题目内容',
    `dimension`     VARCHAR(16)  NOT NULL                  COMMENT '维度: E/I, S/N, T/F, J/P, VALUE',
    `positive_score` VARCHAR(32) DEFAULT NULL              COMMENT '正向计分属性(如E/S/T/J/ACHIEVE)',
    `negative_score` VARCHAR(32) DEFAULT NULL              COMMENT '反向计分属性(如I/N/F/P/HARMONY)',
    `sort_order`    INT          NOT NULL DEFAULT 0        COMMENT '排序(题号)',
    `question_type` VARCHAR(32)  DEFAULT 'scenario'        COMMENT '题目类型: scenario/personality/fun',
    `status`        TINYINT      NOT NULL DEFAULT 1        COMMENT '状态',
    `deleted`       TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`       INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `personality_question_idx_dimension` (`dimension`),
    INDEX `personality_question_idx_sort` (`sort_order`)
) ;

-- ============================================================
-- 9. 性格测试选项表 (personality_option)
-- ============================================================
CREATE TABLE IF NOT EXISTS `personality_option` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '选项ID',
    `question_id` BIGINT       NOT NULL                  COMMENT '题目ID',
    `option_text` VARCHAR(256) NOT NULL                  COMMENT '选项内容',
    `score`       INT          NOT NULL DEFAULT 0        COMMENT '得分: -2到+2',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `personality_option_idx_question` (`question_id`),
    CONSTRAINT `personality_option_fk_option_question` FOREIGN KEY (`question_id`) REFERENCES `personality_question`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ;

-- ============================================================
-- 10. 性格测试结果表 (personality_result)
-- ============================================================
CREATE TABLE IF NOT EXISTS `personality_result` (
    `id`              BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '结果ID',
    `zodiac_id`       BIGINT       NOT NULL                  COMMENT '匹配生肖ID',
    `title`           VARCHAR(128) NOT NULL                  COMMENT '结果标题',
    `description`     TEXT         DEFAULT NULL              COMMENT '结果描述',
    `personality_tags` VARCHAR(256) DEFAULT NULL             COMMENT '性格标签(JSON数组)',
    `strengths`       TEXT         DEFAULT NULL              COMMENT '优势描述',
    `weaknesses`      TEXT         DEFAULT NULL              COMMENT '弱点描述',
    `career_advice`   TEXT         DEFAULT NULL              COMMENT '职业建议',
    `relationship_advice` TEXT     DEFAULT NULL              COMMENT '人际关系建议',
    `share_image_url` VARCHAR(512) DEFAULT NULL              COMMENT '分享海报模板URL',
    `min_score`       INT          DEFAULT NULL              COMMENT '最低分数阈值',
    `max_score`       INT          DEFAULT NULL              COMMENT '最高分数阈值',
    `deleted`         TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`         INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `personality_result_idx_zodiac` (`zodiac_id`),
    CONSTRAINT `personality_result_fk_result_zodiac` FOREIGN KEY (`zodiac_id`) REFERENCES `zodiac`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ;

-- ============================================================
-- 11. 测试记录表 (test_record)
-- ============================================================
CREATE TABLE IF NOT EXISTS `test_record` (
    `id`             BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '记录ID',
    `user_id`        BIGINT        DEFAULT NULL              COMMENT '用户ID',
    `zodiac_id`      BIGINT        NOT NULL                  COMMENT '匹配生肖ID',
    `result_id`      BIGINT        DEFAULT NULL              COMMENT '结果ID',
    `answers`        CLOB          DEFAULT NULL              COMMENT '答案快照(JSON)',
    `dimension_score` CLOB         DEFAULT NULL              COMMENT '维度得分(JSON)',
    `share_count`    INT           NOT NULL DEFAULT 0        COMMENT '分享次数',
    `test_duration`  INT           DEFAULT NULL              COMMENT '测试耗时(秒)',
    `deleted`        TINYINT       NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`        INT           NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `test_record_idx_user` (`user_id`),
    INDEX `test_record_idx_zodiac` (`zodiac_id`),
    INDEX `test_record_idx_create_time` (`create_time`),
    CONSTRAINT `test_record_fk_record_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `test_record_fk_record_zodiac` FOREIGN KEY (`zodiac_id`) REFERENCES `zodiac`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ;

-- ============================================================
-- 12. 产品分类表 (product_category)
-- ============================================================
CREATE TABLE IF NOT EXISTS `product_category` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '分类ID',
    `name`        VARCHAR(64)  NOT NULL                  COMMENT '分类名称',
    `description` VARCHAR(256) DEFAULT NULL              COMMENT '分类描述',
    `icon_url`    VARCHAR(512) DEFAULT NULL              COMMENT '分类图标URL',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `status`      TINYINT      NOT NULL DEFAULT 1        COMMENT '状态',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `product_category_idx_status_sort` (`status`, `sort_order`)
) ;

-- ============================================================
-- 13. 产品表 (product)
-- ============================================================
CREATE TABLE IF NOT EXISTS `product` (
    `id`          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '产品ID',
    `category_id` BIGINT        NOT NULL                  COMMENT '分类ID',
    `zodiac_id`   BIGINT        DEFAULT NULL              COMMENT '关联生肖ID',
    `name`        VARCHAR(256)  NOT NULL                  COMMENT '产品名称',
    `description` TEXT          DEFAULT NULL              COMMENT '产品描述',
    `detail`      CLOB      DEFAULT NULL              COMMENT '产品详情(HTML)',
    `image_url`   VARCHAR(512)  NOT NULL                  COMMENT '产品主图URL',
    `images`      CLOB          DEFAULT NULL              COMMENT '产品图片集(JSON数组)',
    `price`       DECIMAL(10,2) NOT NULL DEFAULT 0.00     COMMENT '价格',
    `original_price` DECIMAL(10,2) DEFAULT NULL           COMMENT '原价',
    `stock`       INT           NOT NULL DEFAULT 0        COMMENT '库存',
    `sales`       INT           NOT NULL DEFAULT 0        COMMENT '销量',
    `view_count`  BIGINT        NOT NULL DEFAULT 0        COMMENT '浏览次数',
    `is_hot`      TINYINT       NOT NULL DEFAULT 0        COMMENT '是否热门: 0-否 1-是',
    `is_recommend` TINYINT      NOT NULL DEFAULT 0        COMMENT '是否推荐: 0-否 1-是',
    `tags`        VARCHAR(256)  DEFAULT NULL              COMMENT '标签(逗号分隔)',
    `product_tier` VARCHAR(32)  DEFAULT 'entry'           COMMENT '产品层级: entry/profit/brand',
    `sort_order`  INT           NOT NULL DEFAULT 0        COMMENT '排序',
    `status`      TINYINT       NOT NULL DEFAULT 1        COMMENT '状态: 0-下架 1-上架',
    `deleted`     TINYINT       NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT           NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `product_idx_category` (`category_id`),
    INDEX `product_idx_zodiac` (`zodiac_id`),
    INDEX `product_idx_hot_recommend` (`is_hot`, `is_recommend`, `status`),
    INDEX `product_idx_price` (`price`),
    INDEX `product_idx_sales` (`sales`),
    CONSTRAINT `product_fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `product_category`(`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ;

-- ============================================================
-- 14. 图库表 (gallery)
-- ============================================================
CREATE TABLE IF NOT EXISTS `gallery` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '图片ID',
    `title`       VARCHAR(128) NOT NULL                  COMMENT '图片标题',
    `image_url`   VARCHAR(512) NOT NULL                  COMMENT '图片URL',
    `thumb_url`   VARCHAR(512) DEFAULT NULL              COMMENT '缩略图URL',
    `category`    VARCHAR(64)  DEFAULT NULL              COMMENT '图片分类',
    `description` VARCHAR(512) DEFAULT NULL              COMMENT '图片描述',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `status`      TINYINT      NOT NULL DEFAULT 1        COMMENT '状态',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `gallery_idx_category` (`category`),
    INDEX `gallery_idx_status_sort` (`status`, `sort_order`)
) ;

-- ============================================================
-- 15. 合作伙伴表 (partner)
-- ============================================================
CREATE TABLE IF NOT EXISTS `partner` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '合作伙伴ID',
    `name`        VARCHAR(128) NOT NULL                  COMMENT '名称',
    `logo_url`    VARCHAR(512) NOT NULL                  COMMENT 'Logo URL',
    `website`     VARCHAR(256) DEFAULT NULL              COMMENT '网站链接',
    `description` VARCHAR(256) DEFAULT NULL              COMMENT '描述',
    `type`        VARCHAR(32)  DEFAULT NULL              COMMENT '类型: school/organization/media',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `status`      TINYINT      NOT NULL DEFAULT 1        COMMENT '状态',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `partner_idx_status_sort` (`status`, `sort_order`)
) ;

-- ============================================================
-- 16. 团队成员表 (team_member)
-- ============================================================
CREATE TABLE IF NOT EXISTS `team_member` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '成员ID',
    `name`        VARCHAR(32)  NOT NULL                  COMMENT '姓名',
    `avatar_url`  VARCHAR(512) DEFAULT NULL              COMMENT '头像URL',
    `title`       VARCHAR(64)  DEFAULT NULL              COMMENT '职位/角色',
    `department`  VARCHAR(64)  DEFAULT NULL              COMMENT '学院/专业',
    `description` VARCHAR(256) DEFAULT NULL              COMMENT '简介',
    `sort_order`  INT          NOT NULL DEFAULT 0        COMMENT '排序',
    `is_core`     TINYINT      NOT NULL DEFAULT 0        COMMENT '是否核心成员',
    `status`      TINYINT      NOT NULL DEFAULT 1        COMMENT '状态',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `version`     INT          NOT NULL DEFAULT 0        COMMENT '乐观锁版本号',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `team_member_idx_status_sort` (`status`, `sort_order`)
) ;

-- ============================================================
-- 17. 反馈表 (feedback)
-- ============================================================
CREATE TABLE IF NOT EXISTS `feedback` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '反馈ID',
    `user_id`     BIGINT       DEFAULT NULL              COMMENT '用户ID',
    `content`     TEXT         NOT NULL                  COMMENT '反馈内容',
    `contact`     VARCHAR(128) DEFAULT NULL              COMMENT '联系方式',
    `type`        VARCHAR(32)  DEFAULT 'suggestion'      COMMENT '反馈类型: bug/suggestion/other',
    `is_handled`  TINYINT      NOT NULL DEFAULT 0        COMMENT '是否已处理',
    `handle_note` VARCHAR(512) DEFAULT NULL              COMMENT '处理备注',
    `deleted`     TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `feedback_idx_user` (`user_id`),
    INDEX `feedback_idx_handled` (`is_handled`),
    CONSTRAINT `feedback_fk_feedback_user` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ;

-- ============================================================
-- 18. 文件存储表 (file_storage)
-- ============================================================
CREATE TABLE IF NOT EXISTS `file_storage` (
    `id`            BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '文件ID',
    `original_name` VARCHAR(256) NOT NULL                  COMMENT '原始文件名',
    `file_url`      VARCHAR(512) NOT NULL                  COMMENT '文件URL',
    `file_type`     VARCHAR(32)  NOT NULL DEFAULT 'image'  COMMENT '文件类型: image/video/document/other',
    `file_size`     BIGINT       NOT NULL DEFAULT 0        COMMENT '文件大小(字节)',
    `mime_type`     VARCHAR(128) DEFAULT NULL              COMMENT 'MIME类型',
    `width`         INT          DEFAULT NULL              COMMENT '图片宽度',
    `height`        INT          DEFAULT NULL              COMMENT '图片高度',
    `upload_by`     BIGINT       DEFAULT NULL              COMMENT '上传者ID',
    `deleted`       TINYINT      NOT NULL DEFAULT 0        COMMENT '逻辑删除',
    `create_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `file_storage_idx_type` (`file_type`),
    INDEX `file_storage_idx_upload_by` (`upload_by`)
) ;

-- ============================================================
-- 19. 操作日志表 (operation_log)
-- ============================================================
CREATE TABLE IF NOT EXISTS `operation_log` (
    `id`          BIGINT        NOT NULL AUTO_INCREMENT  COMMENT '日志ID',
    `user_id`     BIGINT        DEFAULT NULL              COMMENT '操作者ID',
    `module`      VARCHAR(64)   NOT NULL                  COMMENT '操作模块',
    `action`      VARCHAR(64)   NOT NULL                  COMMENT '操作动作',
    `description` VARCHAR(512)  DEFAULT NULL              COMMENT '操作描述',
    `ip`          VARCHAR(64)   DEFAULT NULL              COMMENT 'IP地址',
    `user_agent`  VARCHAR(512)  DEFAULT NULL              COMMENT 'User-Agent',
    `request_url` VARCHAR(256)  DEFAULT NULL              COMMENT '请求URL',
    `params`      CLOB          DEFAULT NULL              COMMENT '请求参数(JSON)',
    `cost_time`   BIGINT        DEFAULT NULL              COMMENT '耗时(毫秒)',
    `create_time` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `operation_log_idx_user` (`user_id`),
    INDEX `operation_log_idx_module` (`module`),
    INDEX `operation_log_idx_create_time` (`create_time`)
) ;

-- ============================================================
-- 20. 每日统计表 (statistics_daily)
-- ============================================================
CREATE TABLE IF NOT EXISTS `statistics_daily` (
    `id`               BIGINT   NOT NULL AUTO_INCREMENT  COMMENT '统计ID',
    `stat_date`        DATE     NOT NULL                  COMMENT '统计日期',
    `pv`               BIGINT   NOT NULL DEFAULT 0        COMMENT '页面浏览量',
    `uv`               BIGINT   NOT NULL DEFAULT 0        COMMENT '独立访客数',
    `test_count`       INT      NOT NULL DEFAULT 0        COMMENT '测试完成数',
    `product_click`    INT      NOT NULL DEFAULT 0        COMMENT '产品点击量',
    `share_count`      INT      NOT NULL DEFAULT 0        COMMENT '分享次数',
    `new_user_count`   INT      NOT NULL DEFAULT 0        COMMENT '新增用户数',
    `most_popular_zodiac` BIGINT DEFAULT NULL             COMMENT '最受欢迎生肖ID',
    `total_revenue`    DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '当日收入',
    `create_time`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_date` (`stat_date`),
    INDEX `statistics_daily_idx_date` (`stat_date`)
) ;

-- ============================================================
-- 21. 用户行为记录表 (user_behavior)
-- ============================================================
CREATE TABLE IF NOT EXISTS `user_behavior` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT  COMMENT '行为ID',
    `user_id`     BIGINT       DEFAULT NULL              COMMENT '用户ID',
    `behavior_type` VARCHAR(32) NOT NULL                 COMMENT '行为类型: page_view/button_click/test_start/test_finish/product_click/share_click/time_on_page',
    `target_id`   BIGINT       DEFAULT NULL              COMMENT '目标ID',
    `target_type` VARCHAR(32)  DEFAULT NULL              COMMENT '目标类型',
    `extra_data`  CLOB         DEFAULT NULL              COMMENT '额外数据(JSON)',
    `ip`          VARCHAR(64)  DEFAULT NULL              COMMENT 'IP地址',
    `session_id`  VARCHAR(128) DEFAULT NULL              COMMENT '会话ID',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `user_behavior_idx_user` (`user_id`),
    INDEX `user_behavior_idx_type` (`behavior_type`),
    INDEX `user_behavior_idx_create_time` (`create_time`),
    INDEX `user_behavior_idx_user_type_time` (`user_id`, `behavior_type`, `create_time`)
) ;
