package com.wids.controller;

import cn.hutool.core.util.StrUtil;
import com.wids.entities.Worker;
import com.wids.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 送水工管理的控制器，用来处理前端的请求
 * 被@Controller注解修饰的类是一个控制器，此时能够处理送水工管理所有的请求
 * @Slf4j是一个日志注解，能够代替System.out.println
 * @RequestMapping("/worker")表示一个请求映射的路径，如果定义在类上面，通常表示父路径
 */
@Controller
@Slf4j
@RequestMapping("/worker")
public class WorkerController {

    /**
     * @Value注解用来读取YML配置文件的数据， ${location}表示将YML配置文件的location值读取到成员变量location中
     */
    @Value("${location}")
    private String location;

    /**
     * 将业务逻辑接口自动装配到Controller控制器，因为Controller要调用业务逻辑接口的方法
     */
    @Autowired
    private WorkerService workerService;

    /**
     * 前端页面“点击修改”按钮，/preUpdateWorker路径对应的方法处理请求，完成数据回显。该步骤也叫做“预修改”
     * 步骤：
     * 1 打印前端传入的送水工ID
     * 2 调用业务逻辑层的getWorkerById方法，根据送水工ID查询对应的送水工信息
     * 3 将送水工信息渲染到前端页面
     * 4 跳转到修改送水工页面
     * @param wid 送水工id
     * @param model 在前后端传入数据的对象
     * @return 修改送水工页面
     */
    @RequestMapping("/preUpdateWorker/{wid}")
    public String preUpdateWorker(@PathVariable("wid") Integer wid , Model model) {
        log.info("pre update worker wid = "+wid);
        Worker worker = workerService.getWorkerById(wid);
        model.addAttribute("worker",worker);
        return "workerUpdate";
    }

    /**
     * 在“修改送水工”页面点击“提交”按钮，做修改送水工操作。
     * 步骤：
     * 1 打印前端传递的参数
     * 2 修改上传的图片
     * 3 调用业务逻辑对象的updateWorker方法修改送水工信息
     * 4 重定向到送水工列表路径，显示新修改的送水工信息
     * 注意：添加送水工的上传和修改送水工的上传步骤相同的
     * @param worker 浏览器采集的送水工信息
     * @param upload 二进制上传文件
     * @return 重定向到送水工列表路径
     */
    @RequestMapping(value = "/updateWorker",method = RequestMethod.POST)
    public String updateWorker(Worker worker , MultipartFile upload) {
        log.info("update worker  = "+worker);
        // 上传文件
        uploadFile(worker,upload);
        // 修改送水工信息，将送水工信息持久化到数据库
        int rows = workerService.updateWorker(worker);
        return "redirect:/worker/workerList";
    }

    /**
     * 在前端页面点击“添加送水工”按钮，跳转到“添加送水工”页面，这个步骤也叫做预添加操作
     * @return “添加送水工”页面
     */
    @RequestMapping("/preSaveWorker")
    public String preSaveWorker(){
        return "workerSave";
    }

    /**
     添加送水工信息，核心在上传图片
     步骤：
     1 将浏览器选择的图像上传到服务器指定的路径
     1.1 获取上传文件的名称 例如： wechat.png
     1.2 获取上传文件后缀的索引,例如：wechat.png文件的后缀为.png。我们要获取.png的索引(6)
     1.3 获取上传文件的后缀，例如：.png
     1.4 获取上传文件的前缀。前缀的格式为：1970-01-01距离现在的纳秒数。例如：1565634653563456
     1.5 拼接上传文件的前缀和后缀，例如：1565634653563456.png
     1.6 创建服务器存放浏览器上传文件的File对象
     File uploadPath = new File(location);
     1.7 判断File对象(upload)的物理路径是否存在，如果不存在就创建该路径
     1.8 创建上传文件的File对象。分为：路径(D:/upLoad/picture/)+文件（1565634653563456.png）
     File uploadFile = new File(uploadPath,1565634653563456.png)
     1.9 调用MultipartFile对象的transferTo方法上传文件。
     1.10 将上传文件名称设置到Worker对象中
     2 调用workerService对象的saveWorker方法，将送水工信息持久化到数据库
     3 重定向到送水工列表，显示新增加的送水工信息

     * @param worker 送水工信息
     * @param upload 接受前端传过来的文件
     * @return
     */
    @RequestMapping(value = "/saveWorker",method = RequestMethod.POST)
    public String saveWorker(Worker worker , MultipartFile upload) {
        uploadFile(worker, upload);
        // 送水工信息持久化到数据库
        workerService.saveWorker(worker);
        // 重定向到客户列表路径，显示新添加的送水工信息
        return "redirect:/worker/workerList";
    }

    /**
     * 从添加送水工，提取的方法，该方法用来完成文件的上传
     * @param worker 送水工信息
     * @param upload 接受前端传过来的文件
     */
    private void uploadFile(Worker worker, MultipartFile upload) {
        // 获取浏览器上传文件的名称(例如：weChat.png)
        String fileName = upload.getOriginalFilename();
        // 条件成立：表示浏览器上传了图像
        if (StrUtil.isNotEmpty(fileName)) {
            // 获取上传文件后缀的索引
            int index = fileName.lastIndexOf(".");
            // 根据索引获取文件的后缀
            String suffix = fileName.substring(index);
            // 获取上传文件的前缀
            long prefix = System.nanoTime();
            // 拼接上传文件的前缀和后缀
            String fullFileName = prefix + suffix;
            log.info("new upload file name ="+fullFileName);
            // 创建服务器存放浏览器上传文件的File对象
            File uploadPath = new File(location);
            // 判断该路径在磁盘下是否存在，如果不存在将其创建
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            // 创建上传文件的全路径
            // uploadPath表示父路径：D:/upLoad/picture/
            // fullFileName上传文件的名称： 1565634653563456.png
            File fullUpLoadPath = new File(uploadPath,fullFileName);
            try {
                // 将文件上传到服务器
                upload.transferTo(fullUpLoadPath);
            } catch (Exception e) {
                System.err.println("上传失败");
                e.printStackTrace();
            }
            // 将上传的文件设置到worker对象中
            worker.setWorkerImage(fullFileName);
        }
    }

    /**
     * 在前端页面点击“送水工管理”按钮，会调用/workerList路径下面的方法处理请求，将送水工列表信息显示到前端页面上
     * 步骤：
     * 1 调用业务逻辑接口的workerList方法查询所有的送水工信息
     * 2 打印送水工信息
     * 3 将送水工信息渲染到前端页面
     * 4 跳转到送水工列表页面
     * @param model
     * @return 送水工列表页面
     */
    @RequestMapping("/workerList")
    public String workerList(Model model) {
        List<Worker> workerList = workerService.workerList();
        log.info("workerList = "+workerList);
        model.addAttribute("workerList",workerList);
        return "workerList";
    }

    /**
     * 处理前端调整工资的请求
     * 步骤：
     * 1 打印前端传入的参数
     * 2 调用业务逻辑成的adjustSalary方法调整工资，并返回受影响行数
     * 3 判断受影响行数：大于0向前端返回ok，否则向前端返回fail
     * 注意：@ResponseBody注解修饰的方法表示该方法的返回值以json字符串的形式传递给前端
     * @param wid 工人ID
     * @param workerSalary 工人调整的新工资
     * @return 调整成功返回ok，否则：返回fail
     */
    @RequestMapping(value="/adjustSalary",method = RequestMethod.POST)
    @ResponseBody
    public String adjustSalary(Integer wid , Integer workerSalary) {
        log.info("worker id = "+wid+" worker salary = "+workerSalary);
        int rows = workerService.adjustSalary(wid, workerSalary);
        return rows > 0 ? "ok" : "fail";
    }
}
