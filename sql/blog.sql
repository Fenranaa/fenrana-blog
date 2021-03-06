create table article
(
    id              bigint auto_increment comment '编号'
        primary key,
    title           varchar(400)      null comment '标题',
    cover           varchar(400)      null comment '封面图片',
    author          varchar(100)      not null comment '作者',
    content         mediumtext        null comment '内容',
    category_id     varchar(20)       null comment '分类',
    state           varchar(100)      not null comment '状态',
    publish_time    bigint(13)          null comment '发布时间',
    edit_time       bigint(13)          null comment '上次修改时间',
    create_time     bigint(13)          null comment '创建时间',
    type            int     default 0 null comment '类型， 0原创 1转载',
    visits          int               null comment '浏览量',
    summary         varchar(500)      null comment '文章概要',
    disallow_comment tinyint           null comment '是否开启评论 0 false 1 true',
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
    time          bigint(13)         not null comment '留言时间',
    content       text             null comment '留言内容',
    email         varchar(100)     null comment '邮箱',
    url           varchar(200)     null comment '网址',
    sort          bigint default 0 null comment '分类：0:默认，文章详情页，1:友链页，2:关于页',
    ip            varchar(20)      null comment 'IP地址',
    device        varchar(100)     null comment '设备',
    address       varchar(100)     null comment '地址'
)
    comment '评论表' charset = utf8;

  
  CREATE TABLE log (
  id bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  username varchar(20) DEFAULT NULL COMMENT '操作用户',
  operation varchar(160) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '操作描述',
  method varchar(100) DEFAULT NULL COMMENT '操作方法',
  params varchar(255) DEFAULT NULL COMMENT '操作参数',
  ip varchar(20) DEFAULT NULL COMMENT 'IP地址',
  create_time bigint(13) DEFAULT NULL COMMENT '操作时间',
  device varchar(20) DEFAULT NULL COMMENT '来自那个设备， 比如pc， android，平板',
  browser_name varchar(20) DEFAULT NULL COMMENT '浏览器名称',
  browser_cersion varchar(10) DEFAULT NULL COMMENT '浏览器版本',
  url varchar(200) DEFAULT NULL COMMENT '请求的url',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统日志表';

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
  id int(11) NOT NULL primary key auto_increment,
  create_time bigint(13) NULL DEFAULT NULL COMMENT '创建时间',
  media_type varchar(150)  NULL COMMENT '文件格式',
  name varchar(100)  NULL COMMENT '文件名称',
  path varchar(100) NULL COMMENT '文件存放路径',
  size double(200, 0) NULL COMMENT '文件大小',
  thumb_path varchar(255)  NULL COMMENT '缩略图路径',
  suffix varchar(20)  NULL COMMENT '文件后缀'
) ENGINE = InnoDB charset = utf8;


