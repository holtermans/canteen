package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequestMapping("/wx/dateTime")
@Validated
public class DateTimeController {

    @RequestMapping("getNow")
    public Object getNow(){
        HashMap<Object, Object> result = new HashMap<>();
        result.put("nowTime",LocalDateTime.now());
        return ResponseUtil.ok(result);
    }
}
