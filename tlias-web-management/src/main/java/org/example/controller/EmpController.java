package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.example.anno.Log;
import org.example.pojo.Emp;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @param gender
     * @param begin
     * @param end
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10")Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("分页查询：{}{}{}{}{}{}",page,pageSize,name,gender,begin,end);
        PageBean pageBean = empService.page(page,pageSize,name,gender,begin,end);
        return Result.success(pageBean);
    }
    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        log.info("删除：{}",ids);
        empService.delete(ids);
        return Result.success();
    }
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增：{}",emp);
        empService.save(emp);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        log.info("查询：{}",id);
        Emp emp = empService.selectById(id);
        return Result.success(emp);
    }
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("更新：{}",emp);
        empService.update(emp);
        return Result.success();
    }


}
