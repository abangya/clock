/*
*
* Userlevel.java
* @date 2019-06-11
*/
package com.deyi.clock.domain;

import java.io.Serializable;
import java.util.Date;

public class UserLevel implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer levelId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 操作人id
     */
    private Integer createUser;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Integer updateUser;

    /**
     * userlevel
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return userId 
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId 
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return levelId 
     */
    public Integer getLevelId() {
        return levelId;
    }

    /**
     * 
     * @param levelId 
     */
    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    /**
     * 
     * @return createTime 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 操作人id
     * @return createUser 操作人id
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 操作人id
     * @param createUser 操作人id
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 
     * @return updateTime 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 
     * @return updateUser 
     */
    public Integer getUpdateUser() {
        return updateUser;
    }

    /**
     * 
     * @param updateUser 
     */
    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }
}