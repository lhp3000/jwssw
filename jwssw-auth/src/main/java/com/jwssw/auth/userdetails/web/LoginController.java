package com.jwssw.auth.userdetails.web;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.jwssw.auth.userdetails.entity.User;
import com.jwssw.auth.userdetails.service.UserService;
import com.jwssw.auth.userdetails.vo.LoginVO;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author <a href="mailTo:luhaopeng2005@126.com">Luhaopeng</a>
 * @version 1.0
 * @date 2020/2/21 17:19
 * @since JDK 1.8
 */
@RestController
@RequestMapping("login")
public class LoginController extends MyController<UserService> {

    @Autowired
    private Producer producer;


    /**
     * 登录时验证码信息
     *
     * @param response 请求响应对象
     * @param request  请求对象
     * @throws Exception 抛出异常
     */
    @GetMapping("captcha")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        // 生成文字验证码
        String text = producer.createText();
        // 生成图片验证码
        BufferedImage image = producer.createImage(text);
        // 保存到验证码到 session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        try (ServletOutputStream out = response.getOutputStream();) {
            ImageIO.write(image, "jpg", out);
        }
    }

    /**
     * 用户查验
     *
     * @param loginVO 用户信息
     * @param request 请求对象
     * @return 用户信息
     */
    @PostMapping("check")
    public HttpResult<Map<String, String>> user(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
        Object captcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (ObjectUtil.isNull(captcha)) {
            return HttpResult.error("验证码已失效");
        }
        if (!loginVO.getCaptcha().equals(captcha)) {
            return HttpResult.error("验证码不正确");
        }

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq(User.NAME, loginVO.getAccount());
        User user = service.getOne(qw);
        // 账号不存在、密码错误
        if (ObjectUtil.isNull(user)) {
            return HttpResult.error("账号不存在");
        }
        // 账号锁定
        if (MyConst.ZERO_STR.equals(user.getStatus())) {
            return HttpResult.error("账号已被锁定,请联系管理员");
        }

        // 返回用户信息
        Map<String, String> retMap = new WeakHashMap<>(3);
        retMap.put("userName", user.getName());
        retMap.put("nickName", user.getNickName());
        retMap.put("createDate", user.getCreateTime());
        return HttpResult.success(retMap);
    }
}
