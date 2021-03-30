package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.Config;
import org.linlinjava.litemall.db.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/wx/config")
@Validated
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @RequestMapping("getConfig")
    public Object getConfig(@RequestParam String name) {

        Config config = configService.queryByName(name);
        HashMap<Object, Object> result = new HashMap<>();
        result.put("config", config);
        return ResponseUtil.ok(result);

    }
}
