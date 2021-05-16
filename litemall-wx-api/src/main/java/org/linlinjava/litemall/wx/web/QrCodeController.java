package org.linlinjava.litemall.wx.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.linlinjava.litemall.core.qcode.QRCodeUtil;
import org.linlinjava.litemall.core.storage.StorageService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 小程序码服务
 */
@RestController
@RequestMapping("/wx/QrCode")
@Validated
public class QrCodeController {
    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private StorageService storageService;
    @RequestMapping("get")
    public Object get() throws WxErrorException, IOException {

//        File qrcode = wxMaService.getQrcodeService().createQrcode("/pages/ordering/ordering");
        File qrcode = wxMaService.getQrcodeService().createWxaCode("/pages/ordering/ordering");
        FileInputStream inputStream = new FileInputStream(qrcode);
        LitemallStorage checkCode = storageService.store(inputStream, inputStream.available(), "image/jpeg", qrcode.getName());
        return ResponseUtil.ok(checkCode);

    }
    @GetMapping(value = "/getQRCode")
    public void getQRCode(HttpServletResponse response,
                          @RequestParam("content") String content,
                          @RequestParam(value = "logoUrl", required = false) String logoUrl) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            QRCodeUtil.getQRCode(content, logoUrl, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
}
