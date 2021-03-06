package com.increff.pos.controller;

import com.increff.pos.model.InfoData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/ui")
public class SiteUIController {

    @Value("${app.baseUrl}")
    private String baseUrl;

    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/product")
    public ModelAndView product() {
        return mav("product.html");
    }

    @RequestMapping(value = "/orderHistory")
    public ModelAndView orderHistory() {
        return mav("orderHistory.html");
    }

    @RequestMapping(value = "/brand")
    public ModelAndView brand() {
        return mav("brand.html");
    }

    @RequestMapping(value = "/report")
    public ModelAndView report() {
        return mav("report.html");
    }



    private ModelAndView mav(String page) {
        ModelAndView mav = new ModelAndView(page);
        mav.addObject("info", new InfoData());
        mav.addObject("baseUrl", baseUrl);

        return mav;
    }

}
