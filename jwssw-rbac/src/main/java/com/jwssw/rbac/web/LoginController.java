package com.jwssw.rbac.web;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.http.HttpResult;
import com.jwssw.rbac.entity.User;
import com.jwssw.rbac.service.UserService;
import com.jwssw.rbac.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 系统登录 控制类
 *
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
     * @return void
     * @author luhaopeng
     * @date 2019/11/20 21:42
     * @parme [response 响应对象, request 请求对象]
     **/
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
            ImageIO.write(resizeBufferedImage(image, 200, 40, false), "jpg", out);
        }
    }

    /**
     * 用户查验
     *
     * @param loginVO 用户信息
     * @param request 请求对象
     * @return 用户信息
     */
    @PreAuthorize("hasAuthority('rbac:login:check')")
    @PostMapping("check")
    public HttpResult<Map<String, String>> user(LoginVO loginVO, HttpServletRequest request) {
        // 判断是否为移动端，如果为移动端，暂不判断验证码
        String userAgent = request.getHeader(MyConst.USER_AGENT);
        if (!StrUtil.containsIgnoreCase(userAgent, MyConst.APP_USER_AGENT)) {
            // 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
            Object captcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ObjectUtil.isNull(captcha)) {
                return HttpResult.error("验证码已失效");
            }
            if (!loginVO.getCaptcha().equals(captcha)) {
                return HttpResult.error("验证码不正确");
            }
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

    /**
     * 调整bufferedimage大小
     *
     * @param source  BufferedImage 原始image
     * @param targetW int  目标宽
     * @param targetH int  目标高
     * @param flag    boolean 是否同比例调整
     * @return BufferedImage  返回新image
     */
    private static BufferedImage resizeBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (flag && sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else if (flag && sx <= sy) {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        // handmade
        if (type == BufferedImage.TYPE_CUSTOM) {
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }
}
