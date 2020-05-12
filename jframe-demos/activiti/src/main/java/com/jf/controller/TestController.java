package com.jf.controller;

import com.jf.service.ActivitiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2019-07-30
 * Time: 15:15
 */
@Controller
public class TestController {

    @Resource
    private ActivitiService activitiService;

    @RequestMapping("/diagram")
    public void diagram(HttpServletResponse response) {
        try {
            InputStream is = activitiService.getDiagram("32501");
            if (is == null)
                return;
            response.setContentType("image/png");

            BufferedImage image = ImageIO.read(is);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);

            is.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
