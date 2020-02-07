create table article
(
    id              bigint auto_increment comment '编号'
        primary key,
    title           varchar(400)      null comment '标题',
    cover           varchar(400)      null comment '封面图片',
    author          varchar(100)      not null comment '作者',
    content         mediumtext        null comment '内容',
    category        varchar(20)       null comment '分类',
    state           varchar(100)      not null comment '状态',
    publish_time    datetime          null comment '发布时间',
    edit_time       datetime          null comment '上次修改时间',
    create_time     datetime          null comment '创建时间',
    type            int     default 0 null comment '类型， 0原创 1转载',
    visits          int               null comment '浏览量',
    summary         varchar(500)      null comment '文章概要',
    disallowComment tinyint           null comment '是否开启评论 0 false 1 true',
    is_top          tinyint default 0 null comment '是否开启置顶 0 false 1true'
)
    comment '文章表' charset = utf8;

create table article_category
(
    id          bigint auto_increment comment '编号'
        primary key,
    article_id  bigint not null comment '文章ID',
    category_id bigint not null comment '分类ID'
)
    comment '文章&&分类关联表' charset = utf8;

create table article_tag
(
    id         bigint auto_increment comment '编号'
        primary key,
    article_id bigint not null comment '文章ID',
    tag_id     bigint not null comment '标签ID'
)
    comment '文章&&标签关联表' charset = utf8;

create table category
(
    id   bigint auto_increment comment '编号'
        primary key,
    name varchar(100) null comment '分类名称',
    description varchar(200) null comment '分类名称'
)
    comment '分类表' charset = utf8;

create table comment
(
    id            bigint auto_increment comment 'ID'
        primary key,
    p_id          bigint default 0 null comment '父级ID，给哪个留言进行回复',
    c_id          bigint default 0 null comment '子级ID，给哪个留言下的回复进行评论',
    article_title varchar(200)     null comment '文章标题',
    article_id    bigint           null comment '文章ID',
    name          varchar(20)      null comment '昵称',
    c_name        varchar(20)      null comment '给谁留言',
    time          datetime         not null comment '留言时间',
    content       text             null comment '留言内容',
    email         varchar(100)     null comment '邮箱',
    url           varchar(200)     null comment '网址',
    sort          bigint default 0 null comment '分类：0:默认，文章详情页，1:友链页，2:关于页',
    ip            varchar(20)      null comment 'IP地址',
    device        varchar(100)     null comment '设备',
    address       varchar(100)     null comment '地址'
)
    comment '评论表' charset = utf8;

create table log
(
    id          bigint auto_increment comment '编号'
        primary key,
    username    varchar(20)  null comment '操作用户',
    operation   varchar(20)  null comment '操作描述',
    time        bigint       null comment '耗时(毫秒)',
    method      varchar(100) null comment '操作方法',
    params      varchar(255) null comment '操作参数',
    ip          varchar(20)  null comment 'IP地址',
    create_time datetime     null comment '操作时间',
    location    varchar(20)  null comment '操作地点'
)
    comment '系统日志表' charset = utf8;

create table login_log
(
    id          bigint auto_increment comment '编号'
        primary key,
    username    varchar(20)  null comment '用户名',
    ip          varchar(20)  null comment 'IP地址',
    location    varchar(255) null comment '登录地点',
    create_time datetime     null comment '登录时间',
    device      varchar(255) null comment '登录设备'
)
    charset = utf8;

create table tag
(
    id   bigint auto_increment comment '编号'
        primary key,
    name varchar(100) null comment '标签名称',
    description varchar(200) null comment '描述'
)
    comment '标签表' charset = utf8;

create table user
(
    id        bigint auto_increment comment '编号'
        primary key,
    username  varchar(100) not null comment '用户名',
    password  varchar(100) not null comment '密码',
    salt      varchar(200) not null comment '盐值',
    avatar    varchar(200) null comment '头像',
    introduce varchar(100) null comment '介绍',
    remark    varchar(100) null comment '备注'
)
    comment '用户表' charset = utf8;
	
	
	
CREATE TABLE attachment  (
  id int(11) NOT NULL COMMENT id  primary key,
  create_time datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  media_type varchar(150)  NULL COMMENT '文件格式',
  name varchar(100)  NULL COMMENT '文件名称',
  path varchar(255) NULL COMMENT '文件存放路径',
  size double(200, 0) NULL COMMENT '文件大小',
  thumb_path varchar(255)  NULL COMMENT '缩略图路径',
  suffix varchar(100)  NULL COMMENT '文件后缀',
) ENGINE = InnoDB charset = utf8;


