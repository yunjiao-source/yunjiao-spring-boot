package io.yunjiao.spring.boot.autoconfigure.apijson.gson;

import apijson.RequestMethod;
import apijson.gson.APIJSONController;
import apijson.gson.APIJSONParser;
import io.yunjiao.spring.apijson.annotation.ApijsonRest;
import io.yunjiao.spring.boot.autoconfigure.apijson.ApijsonProperties;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * 统一的rest接口
 *
 * @author yangyunjiao
 */
@Slf4j
@ApijsonRest
@RequiredArgsConstructor
@RestController
@RequestMapping("common")
public class GsonRestController extends APIJSONController<Serializable> {
    private final ApijsonProperties properties;

    @Override
    public APIJSONParser<Serializable> newParser(HttpSession session, RequestMethod method) {
        return super.newParser(session, method)
                .setNeedVerifyLogin(properties.isNeedVerifyLogin())
                .setNeedVerifyRole(properties.isNeedVerifyRole())
                .setNeedVerifyContent(properties.isNeedVerifyContent());
    }

    @PostMapping(value = "{method}")
    @Override
    public String crud(@PathVariable("method") String method, @RequestBody String request, HttpSession session) {
        return super.crud(method, request, session);
    }

    @PostMapping(value = "crud")
    public String crudAll(@RequestBody String request, HttpSession session) {
        return newParser(session, RequestMethod.CRUD).parse(request);
    }

    @PostMapping("{method}/{tag}")
    @Override
    public String crudByTag(@PathVariable("method") String method, @PathVariable("tag") String tag, @RequestParam Map<String, String> params, @RequestBody String request, HttpSession session) {
        return super.crudByTag(method, tag, params, request, session);
    }
}
