package cn.fenrana.blog.entity.enums;

public enum LogType {

    /**
     * 用户登录
     */
    LOGGED_IN("用户登录"),
    /**
     * 退出
     */
    LOGGED_OUT("用户退出"),
    /**
     * 登录失败
     */
    LOGIN_FAILED("登录失败"),

    /**
     * 修改密码
     */
    PASSWORD_UPDATED("修改密码"),

    /**
     * 添加文章
     */
    ARTICLE_PUBLISHED("发布文章"),
    /**
     * 修改文章
     */
    ARTICLE_UPDATE("修改文章"),
    /**
     * 删除文章
     */
    ARTICLE_DELETE("删除文章"),
    /**
     * 添加标签
     */
    TAG_INSERT("添加标签"),
    /**
     * 修改标签
     */
    TAG_UPDATE("修改标签"),
    /**
     * 删除标签
     */
    TAG_DELETE("删除标签"),
    /**
     * 上传附件
     */
    ATTACHMENT_UPLOAD("上传附件"),
    /**
     * 删除附件
     */
    ATTACHMENT_DELETE("删除附件"),
    /**
     * 修改附件的名字
     */
    ATTACHMENT_UPDATE_NAME("修改附件蜜名字"),
    /**
     * 添加文章分类
     */
    CATEGORY_INSERT("添加文章分类"),
    /**
     * 删除文章分类
     */
    CATEGORY_DELETE("删除文章分类"),
    /**
     * 修改文章分类
     */
    CATEGORY_UPDATE("修改文章分类");


    private final String value;

    LogType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
