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

package yunjiao.springboot.extension.apijson.gson.model;

import apijson.MethodAccess;
import apijson.framework.BaseModel;
import apijson.orm.Visitor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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
@Getter
@Setter
@Accessors(chain = true)
public class User extends BaseModel implements Visitor<Long> {
    /**
     * 性别0
     */
    public static final int SEX_MAIL = 0;

    /**
     * 性别1
     */
    public static final int SEX_FEMALE = 1;

    /**
     * 性别2
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
     * @param id id
     */
    public User(long id) {
        this();
        setId(id);
    }


}
