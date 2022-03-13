package com.wids.controller;

import com.wids.entities.Customer;
import com.wids.entities.History;
import com.wids.entities.Worker;
import com.wids.service.CustomerService;
import com.wids.service.HistoryService;
import com.wids.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 送水历史管理对应的控制器，用来处理送水历史相关的所有请求
 */
@Controller
@RequestMapping("/history")
@Slf4j
public class HistoryController {

    /**
     * 送水历史控制器需要依赖业务逻辑，此时将业务逻辑对象HistoryService自动装配到Controler(根据类型装配的)
     */
    @Autowired
    private HistoryService historyService;

    /**
     * 由于送水历史模块，需要依赖客户管理和送水工管理模块，所以在Controller层将客户管理和送水工管理注入进来
     */
    @Autowired
    private CustomerService customerService;

    @Autowired
    private WorkerService workerService;

    /**
     * /batchDeleteHistory路径对应的方法专门用来处理前端发起的批量删除异步请求
     *  步骤：
     *  1 打印前端传入的参数，出了问题可以调试
     *  2   调用业务逻辑接口的批量删除方法，删除选中的数据
     *  3 打印受影响行数
     *  4 受影响行数大于0，向前端返回success，否则返回fail
     * @param ids 前端传入的id列表
     * @return success 删除成功，否则删除失败
     */
    @RequestMapping(value = "/batchDeleteHistory",method = RequestMethod.POST)
    @ResponseBody
    public String batchDeleteHis(String  ids) {
        log.info("batch delete history ids = "+ids);
        try {
            int rows = historyService.batchDeleteHistory(ids);
            log.info("batch delete history rows = "+rows);
            return rows > 0 ? "success" : "fail";
        } catch (Exception e) {
            log.error("批量删除失败，数据回滚",e);
            return "fail";
        }
    }

    /**
     * 在前端页面点击“送水历史管理”，调用/historyList路径对应的方法处理前端请求，将所有送水历史信息响应给前端页面
     * 步骤：
     * 1 调用业务逻辑对象的listHistory方法查询所有的送水历史信息
     * 2 向送水历史信息渲染到前端页面
     * 3 返回送水历史页面
     * @param model 前后端传递数据的对象
     * @return 返回送水历史页面
     */
    @RequestMapping("/historyList")
    public String historyList(Model model) {
        List<History> hisList = historyService.listHistory();
        model.addAttribute("hisList",hisList);
        log.info("hisList = "+hisList);
        return "historyList";
    }

    /**
     * 在前端页面点击“添加按钮”，将所有的客户信息、送水工信息渲染到前端页面。并且跳转到“添加送水历史页面”
     * 步骤：
     * 1 调用业务逻辑层对象CustomerService的custList方法查询所有的客户信息
     * 2 调用业务逻辑层对象WorkerService的listWorker方法查询所有的送水工信息
     * 3 分别将他们渲染到前端页面
     * 4 跳转到“添加送水历史页面”
     * @param model 在前后端传输数据的对象
     * @return 添加送水历史页面
     */
    @RequestMapping("/preSaveHistory")
    public String preSaveHistory(Model model){
        List<Customer> custList = customerService.custList();
        List<Worker> workerList = workerService.workerList();
        model.addAttribute("custList",custList);
        model.addAttribute("workerList",workerList);
        return "historySave";
    }

    /**
     * 在“添加送水历史页面”采集完要添加的送水历史信息，点击“提交”按钮。然后后端使用/saveHistory路径对应的方法处理前端请求，将表单采集的数据持久化到数据库
     * 步骤：
     * 1 打印前端采集的参数
     * 2 将送水工ID和客户ID注入到History对象中
     * 3 调用业务逻辑对象historyServer的saveHistory方法将History对象持久化到数据库
     * 4 调整到“送水历史列表”路径，并且显示新添加的送水历史信息
     * @param workerId 送水工ID
     * @param custId 客户ID
     * @param history 送水历史信息
     * @return 送水历史列表页面
     */
    @RequestMapping("/saveHistory")
    public String saveHistory(Integer workerId , Integer custId , History history) {
        log.info("workerId = "+workerId);
        log.info("custId = "+custId);
        log.info("History = "+history);

        Customer customer = new Customer();
        customer.setCid(custId);
        Worker worker = new Worker();
        worker.setWid(workerId);
        // 将客户信息和送水工信息注入到History对象
        history.setWorker(worker);
        history.setCustomer(customer);

        int rows = historyService.saveHistory(history);
        log.info("save history rows = " +rows);
        return "redirect:/history/historyList";
    }

    /**
     * 预修改操作（数据回显），将送水历史信息回显到“修改送水历史页面”
     * 步骤：
     * 1 打印前端传递的参数
     * 2 调用业务逻辑对象historyService的getHistoryById方法，根据送水历史ID查询对应的送水历史信息
     * 3 查询所有的送水工信息和客户信息
     * 4 将送水历史信息，所有的送水工信息、客户信息渲染到前端
     * 5 跳转"修改送水历史页面"
     * @param hid 送水历史id
     * @param model 在前后端传递数据的对象
     * @return 返回"修改送水历史页面"
     */
    @RequestMapping("/preUpdateHistory/{hid}")
    public String preUpdateHistory(@PathVariable("hid") Integer hid, Model model) {
        log.info("pre update history hid = "+hid);
        History history = historyService.getHistoryById(hid);
        List<Customer> custList = customerService.custList();
        List<Worker> workerList = workerService.workerList();
        // 将后端查询的数据渲染到前端
        model.addAttribute("workerList",workerList);
        model.addAttribute("custList",custList);
        model.addAttribute("history",history);
        return "historyUpdate";
    }

    /**
     * 点击“提交”按钮，进行真正的修改。将表单参数持久化到数据库，并返回到历史列表路径，显示新修改的送水历史信息
     * 步骤：
     * 1 打印前端传递的参数
     * 2 将客户ID和送水工ID注入到History对象
     * 3 调用业务逻辑层对象historyService的updateHistory方法将送水历史信息，持久化到数据库
     * 4 重定向到送水历史路径，并且显示新修改的信息
     * @param workerId 送水工ID
     * @param custId 客户ID
     * @param history 送水历史信息
     * @return 并返回到历史列表路径，显示新修改的送水历史信息
     */
    @RequestMapping(value = "/updateHistory",method = RequestMethod.POST)
    public String updateHistory(Integer workerId , Integer custId , History history) {
        log.info("UpdateHistory worker id = "+workerId);
        log.info("update history custId = "+custId);
        log.info("update history ="+ history);
        Worker worker = new Worker();
        worker.setWid(workerId);

        Customer customer = new Customer();
        customer.setCid(custId);

        history.setCustomer(customer);
        history.setWorker(worker);
        // 将前端采集的送水历史信息持久化到数据库，返回受影响的行数，大于0修改成功，否则修改失败
        int rows = historyService.updateHistory(history);
        log.info("update history rows = "+rows);
        return "redirect:/history/historyList";

    }
}
