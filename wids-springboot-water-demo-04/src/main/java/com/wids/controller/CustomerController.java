package com.wids.controller;

import com.wids.entities.Customer;
import com.wids.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import java.util.List;

/**
 * 客户管理控制器，专门用来处理前端客户管理请求的
 * @Slf4 注解表示类里面所有的方法都支持日志输出
 */
@Controller
@RequestMapping("/cust")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 用户点击“客户管理”，使用“/listCustomer”对应的方法处理请求，将客户列表显示到前端页面
     * @param model 在前端和后端之间传输数据的对象
     * @return  返回客户列表页面
     */
    @RequestMapping("/listCust")
    public String listCustomer(Model model) {
        // 调用业务逻辑的lcustList方法查询客户信息，然后将客户信息传递给前端，最后返回客户列表页面
        List<Customer> custList = customerService.custList();
        model.addAttribute("custList",custList);
        // 跳转到前端客户管理列表页面custList
        return "custList";
    }

    /**
     * 预添加：在浏览器点击“添加客户”按钮，跳转到添加客户页面
     * @return  添加客户页面
     */
    @RequestMapping("/preSaveCust")
    public String preSaveCustomer(){
        return "custSave";
    }

    /**
     * 在浏览器的“ 添加客户页面”输入完客户信息，点击提交按钮，此时/saveCust路径来处理前端提交的请求，将客户信息添加到数据库
     * 并且跳转到客户列表，显示新添加的客户信息
     * @param customer 前端采集的客户表单数据
     * @return 客户列表，显示新添加的客户信息
     */
    @RequestMapping(value = "/saveCust",method = RequestMethod.POST)
    public String saveCustomer(Customer customer) {
        // 打印前端的数据  工作中调试代码使用slf4j日志代替System.out.print
        log.info(" save Customer "+customer);
        // 调用业务逻辑成的方法，把客户信息添加到数据库中
        int rows = customerService.saveCust(customer);
        System.out.println("save Customer rows = "+rows);
        // 添加完毕之后重定向到客户列表路径,会在此执行上面的listCustomer方法，显示新添加的数据
        return "redirect:/cust/listCust";
    }

    /**
     * /searchCust路径对应的方法专门用来处理搜索请求。当客户在浏览器上输入搜索关键字，点击搜索按钮，该路径就会处理前端发起的请求
     * 步骤：
     * 1 接受并且打印客户输入的搜索关键字(客户名称)
     * 2 调用业务逻辑层的searchCustomer方法搜索满足条件的客户信息
     * 3 将满足条件的客户列表传入给前端
     * 4 跳转到客户列表页面
     * @param custName 搜索条件
     * @param model 在前后端之间传输数据的参数
     * @return 客户列表，显示满足搜索条件的客户信息
     */
    @RequestMapping(value = "/searchCust")
    public String searchCustomer(String custName , Model model) {
        log.info("search customer custName = "+custName);
        List<Customer> custList = customerService.searchCustomerList(custName);
        model.addAttribute("custList",custList);
        // 搜索关键字重新传入给前端
        model.addAttribute("wordKey",custName);
        // 跳转到客户列表页面/templates/water/custList.html,由于我们在yml文件里面已经配置了前缀和后缀所以此时只需要写文件名称(custList)即可
        return "custList";
    }

    /**
     * /delCustById/{cid}路径对应的方法用来处理删除客户信息的功能
     * 步骤：
     * 1 打印前端传入到后端的客户ID
     * 2 调用业务逻辑成的deleteCustomerById方法删除客户信息
     * 3 打印删除受影响行数
     * 4 重定向到客户列表页面，显示删除之后的客户信息
     * @param cid 客户id
     * @return 重定向到客户列表页面，显示删除之后的客户信息
     */
    @RequestMapping(value = "/delCustById/{cid}")
    public String deleteCustomer(@PathVariable("cid") Integer cid) {
        log.info("delete customer cid = "+cid);
        int rows = customerService.deleteCustomerById(cid);
        log.info("delte customer rows = "+rows);
        return "redirect:/cust/listCust";
    }

    /**
     * 客户点击“修改”按钮，就会调用/preUpdateCust路径对应的方法处理修改请求，根据客户id查询客户信息，将客户信息回显到“修改客户页面”
     * 步骤：
     * 1 打印客户id
     * 2 调用业务逻辑接口的getCustomerById方法查询客户信息
     * 3 将客户信息传递到前端的“修改客户”页面
     * 4 跳转到“修改客户页面”
     * @param cid 客户ID
     * @param model 前后端传递参数的对象
     * @return 返回修改客户页面，并且将客户信息传入到该页面中
     */
    @RequestMapping(value = "/preUpdateCust/{cid}")
    public String preUpdateCustomer(@PathVariable("cid")Integer cid , Model model) {
        log.info("pre update customer cid = "+cid);
        Customer cust = customerService.getCustomerById(cid);
        model.addAttribute("cust",cust);
        return "updateCust";
    }

    /**
     * /updateCust路径对应的方法来处理修改客户请求
     * 步骤：
     * 1 打印前端传递的客户信息
     * 2 调用业务逻辑接口的updateCustomer方法修改客户信息
     * 3 打印updateCustomer方法返回的受影响行数
     * 4 重定向到客户列表页面，显示新修改的客户信息
     * @param customer 前端采集的客户信息
     * @return 客户列表页面
     */
    @RequestMapping(value = "/updateCust",method = RequestMethod.POST)
    public String updateCustomer(Customer customer) {
        log.info("update customer form parameter = "+customer);
        int rows = customerService.updateCustomer(customer);
        log.info("update customer rows = "+rows);
        return "redirect:/cust/listCust";
    }
}
