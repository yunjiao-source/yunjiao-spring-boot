/*Copyright ©2016 TommyLemon(https://github.com/TommyLemon/APIJSON)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package io.yunjiao.springboot.autoconfigure.apijson.fastjson2.model;

import apijson.MethodAccess;
import apijson.framework.BaseModel;
import apijson.orm.Visitor;

import java.util.List;

import static apijson.orm.AbstractVerifier.ADMIN;
import static apijson.orm.AbstractVerifier.UNKNOWN;

/**
 * 用户开放信息
 *
 * @author Lemon
 */
@MethodAccess(
        POST = {UNKNOWN, ADMIN},
        DELETE = {ADMIN}
)
public class User extends BaseModel implements Visitor<Long> {
    /**
     * 性别男
     */
    public static final int SEX_MAIL = 0;

    /**
     * 性别女
     */
    public static final int SEX_FEMALE = 1;

    /**
     * 性别未知
     */
    public static final int SEX_UNKNOWN = 2;


    /**
     * 性别
     */
    private Integer sex;

    /**
     * 头像url
     */
    private String head;

    /**
     * 姓名
     */
    private String name;

    /**
     * 标签
     */
    private String tag;

    /**
     * 照片列表
     */
    private List<String> pictureList;

    /**
     * 朋友列表
     */
    private List<Long> contactIdList;

    /**
     * 默认构造方法，JSON等解析时必须要有
     */
    public User() {
        super();
    }

    /**
     * 构造器
     * @param id id值
     */
    public User(long id) {
        this();
        setId(id);
    }

    /**
     * 获取性别
     * @return 性别
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别
     * @param sex 性别
     * @return 实例
     */
    public User setSex(Integer sex) {
        this.sex = sex;
        return this;
    }

    /**
     * 获取头像
     * @return 头像
     */
    public String getHead() {
        return head;
    }

    /**
     * 设置头像
     * @param head 头像
     * @return 实例
     */
    public User setHead(String head) {
        this.head = head;
        return this;
    }

    /**
     * 获取名称
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     * @param name 名称
     * @return 实例
     */
    public User setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 获取图片
     * @return 图片
     */
    public List<String> getPictureList() {
        return pictureList;
    }

    /**
     * 设置图片
     * @param pictureList 图片
     * @return 实例
     */
    public User setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
        return this;
    }

    /**
     * 获取标签
     * @return 标签
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
     * @param tag 标签
     * @return 实例
     */
    public User setTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * 获取通讯录
     * @return 通讯录
     */
    public List<Long> getContactIdList() {
        return contactIdList;
    }

    /**
     * 设置通讯录
     * @param contactIdList 通讯录
     * @return 实例
     */
    public User setContactIdList(List<Long> contactIdList) {
        this.contactIdList = contactIdList;
        return this;
    }

}
