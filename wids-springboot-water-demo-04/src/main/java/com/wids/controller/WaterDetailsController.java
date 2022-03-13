package com.wids.controller;

import com.wids.entities.WaterDetails;
import com.wids.service.WaterDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/details")
public class WaterDetailsController {

    @Autowired
    private WaterDetailsService waterDetailsService;

    /**
     * /waterDetails对应的方法用来处理前端的请求，将送水详细信息显示到浏览器
     * 步骤：
     * 1 调用业务逻辑层WaterDetailsService对象的方法查询所有的送水详细信息
     * 2 将查询的列表返回给前端页面
     * 3 调整到前端页面
     * @param model
     * @return 送水详细信息列表页面
     */
    @RequestMapping("/waterDetails")
    public String listWaterDetails(Model model) {
        List<WaterDetails> detailsList = waterDetailsService.listWaterDetails();
        model.addAttribute("detailsList",detailsList);
        return "waterDetailsList";
    }
}
