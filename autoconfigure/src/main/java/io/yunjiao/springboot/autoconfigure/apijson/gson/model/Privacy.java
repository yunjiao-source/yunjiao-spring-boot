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

package io.yunjiao.springboot.autoconfigure.apijson.gson.model;

import apijson.MethodAccess;
import apijson.framework.BaseModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import static apijson.orm.AbstractVerifier.*;


/**
 * 用户隐私信息
 *
 * @author Lemon
 */
@Getter
@Setter
@Accessors(chain = true)
@MethodAccess(
        GET = {},
        GETS = {OWNER, ADMIN},
        POST = {UNKNOWN, ADMIN},
        DELETE = {ADMIN}
)
public class Privacy extends BaseModel {

    public static final int PASSWORD_TYPE_LOGIN = 0;

    public static final int PASSWORD_TYPE_PAY = 1;

    private String phone; //手机

    @SerializedName("_password")
    private String password; //登录密码，隐藏字段

    @SerializedName("_payPassword")
    private String payPassword; //支付密码，隐藏字段

    private Double balance;    //余额

    public Privacy() {
        super();
    }

    public Privacy(Long id) {
        this();
        setId(id);
    }

    public Privacy(String password) {
        this.password = password;
    }

    public Privacy(String phone, String password) {
        this();
        setPhone(phone);
        setPassword(password);
    }


    public static void main(String[] args) {
        Privacy privacy = new Privacy("uer", "pwd");
        String json = new Gson().toJson(privacy);
        System.out.print(json);
    }

}
