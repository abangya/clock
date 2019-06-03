package com.deyi.clock.utils.log;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName LogEnum
 * @Description 本地日志枚举
 * @createTime 2019年06月03日 09:19
 */
public enum LogEnum {
    BUSSINESS("bussiness"),
    PLATFORM("platform"),
    DB("db"),
    EXCEPTION("exception"),
    ;
    private String category;


    LogEnum(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
