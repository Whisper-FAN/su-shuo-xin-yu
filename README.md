# 塑说心语 (Shuo Xin Yu)

## 大吴泥塑文化传承平台

"塑说心语"是一个以性格测试为交互入口、以大吴泥塑十二生肖真实作品为内容载体、以非遗活态传承与乡村振兴为最终落脚点的数字化公益传播与商业运营平台。

### 技术架构

| 层级 | 技术栈 |
|------|--------|
| 前端 | Vue 3 + Vite + Tailwind CSS + Pinia + Vue Router |
| 后端 | Java 21 + Spring Boot 3.3 + MyBatis Plus + MySQL 8 |
| 认证 | JWT (JSON Web Token) |
| 文档 | Knife4j (Swagger) |
| 存储 | 本地文件存储 + 阿里云OSS |
| 工具 | Lombok, Hutool, Jakarta Validation |

### 项目结构

```
├── yimo-server/          # Spring Boot 后端
│   ├── src/main/java/com/yimo/
│   │   ├── annotation/   # 自定义注解
│   │   ├── common/       # 通用类 (Result, PageResult, PageRequest)
│   │   ├── config/       # 配置类 (MyBatis Plus, Knife4j, Redis, OSS, CORS)
│   │   ├── controller/   # REST 控制器 (18个)
│   │   ├── dto/          # 数据传输对象 (19个)
│   │   ├── entity/       # 数据库实体 (21个)
│   │   ├── enums/        # 枚举类
│   │   ├── exception/    # 异常处理
│   │   ├── interceptor/  # 拦截器
│   │   ├── mapper/       # MyBatis Plus Mapper (21个)
│   │   ├── security/     # JWT 安全
│   │   ├── service/      # 服务接口 (18个)
│   │   ├── service/impl/ # 服务实现 (18个)
│   │   ├── utils/        # 工具类
│   │   └── vo/           # 视图对象 (19个)
│   └── src/main/resources/
│       ├── application.yml
│       ├── db/schema.sql  # 数据库建表脚本
│       └── db/data.sql    # 初始化数据
│
├── yimo-web/             # Vue 3 前端
│   └── src/
│       ├── api/          # API 封装
│       ├── assets/       # 静态资源
│       ├── components/   # 公共组件
│       ├── router/       # 路由配置
│       ├── stores/       # Pinia 状态管理
│       ├── utils/        # 工具函数
│       └── views/        # 页面视图
│           └── admin/    # 管理后台页面
│
└── 素材/                 # 项目素材资源
```

### 快速开始

#### 1. 数据库初始化

```bash
mysql -u root -p < yimo-server/src/main/resources/db/schema.sql
mysql -u root -p < yimo-server/src/main/resources/db/data.sql
```

#### 2. 启动后端

```bash
cd yimo-server
mvn spring-boot:run
```

服务启动在 http://localhost:8088

API 文档: http://localhost:8088/doc.html

#### 3. 启动前端

```bash
cd yimo-web
npm install
npm run dev
```

前端启动在 http://localhost:3000

#### 4. 管理后台登录

- 地址: http://localhost:3000/admin/login
- 用户名: `admin`
- 密码: `admin123`

### 核心功能模块

| 模块 | 说明 | API 前缀 |
|------|------|----------|
| 认证 | 登录、登出、Token刷新 | `/api/auth` |
| 用户 | 用户管理 | `/api/user` |
| 轮播图 | 首页轮播图管理 | `/api/banner` |
| 文章 | 文章内容管理 | `/api/article` |
| 分类 | 文章分类 | `/api/category` |
| 生肖 | 十二生肖管理 | `/api/zodiac` |
| 故事 | 生肖故事 | `/api/story` |
| 泥塑作品 | 作品展示 | `/api/clay-sculpture` |
| 性格测试 | 24题测试 + 结果匹配 | `/api/test` |
| 产品 | 文创产品 | `/api/product` |
| 图库 | 图片资源 | `/api/gallery` |
| 合作伙伴 | 合作伙伴 | `/api/partner` |
| 团队成员 | 团队成员 | `/api/team-member` |
| 反馈 | 用户反馈 | `/api/feedback` |
| 文件 | 文件上传 | `/api/file` |
| 统计 | 数据统计 | `/api/statistics` |
| 管理后台 | 综合管理 | `/api/admin` |

### API 响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": {}
}
```

### 数据库表 (21张)

user, banner, article_category, article, zodiac, zodiac_story, clay_sculpture, personality_question, personality_option, personality_result, test_record, product_category, product, gallery, partner, team_member, feedback, file_storage, operation_log, statistics_daily, user_behavior

### 性格测试算法

测试采用24题五维人格模型：
- **E/I** (能量来源：外向/内向) - 5题
- **S/N** (信息获取：感觉/直觉) - 5题
- **T/F** (决策方式：思考/情感) - 5题
- **J/P** (生活态度：判断/感知) - 5题
- **VALUE** (价值观倾向：成就/和谐/探索/关系) - 4题

每题采用5级李克特量表 (-2到+2)，通过维度得分计算出用户人格类型，然后匹配到对应的十二生肖守护神。

### 设计规范

- 主色: `#8c4a2f` (陶土橙)
- 背景: `#fcf9f3` (象牙白)
- 辅助色: `#396759` (青瓷绿)
- 字体: Noto Serif SC (标题) + Inter (正文)

### 团队

广东金融学院 + 广东财经大学 - "塑说心语"团队

### License

MIT
