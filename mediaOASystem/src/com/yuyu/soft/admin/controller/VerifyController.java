package com.yuyu.soft.admin.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuyu.soft.service.IDepartmentService;
import com.yuyu.soft.service.IDispatchUnitService;
import com.yuyu.soft.service.IDutyService;
import com.yuyu.soft.service.IEquipmentService;
import com.yuyu.soft.service.IFixedAssetsService;
import com.yuyu.soft.service.IForeignExpertService;
import com.yuyu.soft.service.ISectionService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.JsonUtil;

/**
 * 校验验证码、用户、手机号等相关操作
 *                       
 * @Filename: VerifyController.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Controller
public class VerifyController {
    @Resource
    private IUserService          userService;
    @Resource
    private IDepartmentService    departmentService;
    @Resource
    private IDutyService          dutyService;
    @Resource
    private IDispatchUnitService  dispatchUnitService;
    @Resource
    private IEquipmentService     equipmentService;
    @Resource
    private IFixedAssetsService   fixedAssetsService;
    @Resource
    private IForeignExpertService foreignExpertService;
    @Resource
    private ISectionService		  sectionService;

    /**
     * 验证码
     * @param request
     * @param response
     * @param name
     * @throws IOException
     */
    @RequestMapping({ "/verify" })
    public void verify(HttpServletRequest request, HttpServletResponse response, String name)
                                                                                             throws IOException {
        //设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        HttpSession session = request.getSession(true);

        int width = 73;
        int height = 27;
        BufferedImage image = new BufferedImage(width, height, 1);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(100, 200));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Arial", 0, 24));

        g.setColor(getRandColor(200, 250));
        for (int i = 0; i < 188; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        String sRand = "";
        for (int i = 0; i < 4; i++) {
            String rand = CommUtil.randomInt(1).toUpperCase();
            sRand = sRand + rand;

            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random
                .nextInt(110)));
            g.drawString(rand, 13 * i + 6, 24);
        }
        if (CommUtil.null2String(name).equals("")) {
            session.setAttribute("verify_number", sRand);
        } else {
            session.setAttribute(name, sRand);
        }
        g.dispose();
        ServletOutputStream responseOutputStream = response.getOutputStream();

        ImageIO.write(image, "JPEG", responseOutputStream);

        responseOutputStream.flush();
        responseOutputStream.close();
    }

    //给定范围获得随机颜色
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    @RequestMapping({ "/verify_user_mobile.htm" })
    public void verify_user_mobile(HttpServletRequest request, HttpServletResponse response,
                                   Long user_id, String mobile) {
        boolean ret = this.userService.verify_user_mobile(user_id, mobile);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }

    @RequestMapping({ "/verify_department_name.htm" })
    public void verify_department_name(HttpServletRequest request, HttpServletResponse response,
                                       Long department_id, String department_name) {
        boolean ret = this.departmentService.verify_department_name(department_id, department_name);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }

    @RequestMapping({ "/verify_section_name.htm" })
    public void verify_section_name(HttpServletRequest request, HttpServletResponse response,
                                       Long section_id, String section_name) {
        boolean ret = this.sectionService.verify_section_name(section_id, section_name);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }
    
    
    @RequestMapping({ "/verify_duty_name.htm" })
    public void verify_duty_name(HttpServletRequest request, HttpServletResponse response,
                                 Long duty_id, Long department_id, String duty_name) {
        boolean ret = this.dutyService.verify_duty_name(duty_id, department_id, duty_name);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }

    @RequestMapping({ "/verify_dispatch_unit_name.htm" })
    public void verify_dispatch_unit_name(HttpServletRequest request, HttpServletResponse response,
                                          Long dispatch_unit_id, String dispatch_unit_name) {
        boolean ret = this.dispatchUnitService.verify_dispatch_unit_name(dispatch_unit_id,
            dispatch_unit_name);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }

    @RequestMapping({ "/verify_equipment_name.htm" })
    public void verify_equipment_name(HttpServletRequest request, HttpServletResponse response,
                                      Long equipment_id, String equipment_name) {
        boolean ret = this.equipmentService.verify_equipment_name(equipment_id, equipment_name);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }

    @RequestMapping({ "/verify_equipment_lifetime_number.htm" })
    public void verify_equipment_lifetime_number(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 Long fixed_assets_id,
                                                 String equipment_lifetime_number) {
        boolean ret = this.fixedAssetsService.verify_equipment_lifetime_number(fixed_assets_id,
            equipment_lifetime_number);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }

    @RequestMapping({ "/verify_foreign_expert_mobile.htm" })
    public void verify_foreign_expert_mobile(HttpServletRequest request,
                                             HttpServletResponse response, Long foreign_expert_id,
                                             String mobile) {
        boolean ret = this.foreignExpertService.verify_foreign_expert_mobile(foreign_expert_id,
            mobile);
        JsonUtil.writeMsg(response, String.valueOf(ret));
    }

}
