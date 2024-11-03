package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.anno.Log;
import org.example.pojo.Result;
import org.example.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.example.pojo.Dept;

/**
 * 部门管理Controller
 */
@Slf4j
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;
    //private static Logger log = LoggerFactory.getLogger(DeptController.class);
    //@RequestMapping(value = "/depts",method = RequestMethod.GET)

    /**
     * 查询部门数据
     * @return Result
     */
    @GetMapping("/depts")
    public Result list(){
        log.info("查询全部部门数据");
        //调用service查询部门数据
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    /**
     * 删除部门数据
     * @param id
     * @return Result
     */
    @Log
    @DeleteMapping("/depts/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("根据id删除部门：{}",id);
        deptService.delete(id);
        return Result.success();
    }

    /**
     * 新增部门
     */
    @Log
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
        log.info("新增部门数据");
        deptService.add(dept);
        return Result.success();
    }


}
