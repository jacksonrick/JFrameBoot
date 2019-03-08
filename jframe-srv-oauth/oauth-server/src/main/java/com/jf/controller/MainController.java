package com.jf.controller;

import com.jf.database.model.ICONSTANT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-11-01
 * Time: 10:52
 */
@Controller
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Welcome to OAuth2.0";
    }

    @RequestMapping("/valid")
    public void getValidCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            BufferedImage bi = new BufferedImage(100, 35, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            Color c = new Color(127, 255, 212);
            g.setColor(c);
            int width = 100;
            int height = 35;
            g.fillRect(0, 0, width, height);
            g.setFont(new Font("Default", Font.PLAIN, 24));
            Random random = new Random();
            // g.setColor(Color.BLACK);
            int red = 0, green = 0, blue = 0;
            for (int i = 0; i < 20; i++) {
                int xs = random.nextInt(width);
                int ys = random.nextInt(height);
                int xe = xs + random.nextInt(width / 8);
                int ye = ys + random.nextInt(height / 8);
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                g.setColor(new Color(red, green, blue));
                g.drawLine(xs, ys, xe, ye);
            }

            char[] ch = "0123456789".toCharArray();
            int len = ch.length, index;
            StringBuffer sb = new StringBuffer();
            // 验证码位数：5
            for (int i = 0; i < 5; i++) {
                Graphics2D gg = (Graphics2D) g.create();
                gg.translate((i * 15) + 14, 20);
                gg.rotate(random.nextInt(60) * Math.PI / 180);

                index = random.nextInt(len);
                gg.setColor(new Color(random.nextInt(100), random.nextInt(180), random.nextInt(255)));
                gg.drawString(ch[index] + "", 0, 0);
                sb.append(ch[index]);
            }
            request.getSession().setAttribute(ICONSTANT.SE_VERIFY, sb.toString());
            ImageIO.write(bi, "JPG", response.getOutputStream());
            g.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
