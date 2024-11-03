package org.example.service;

import org.example.pojo.Emp;
import org.example.pojo.PageBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
public interface EmpService {
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    public PageBean page(Integer page, Integer pageSize, String name, Short gender,LocalDate begin, LocalDate end);

    /**
     * 批量删除
     * @param ids
     */
    public void delete(List<Integer> ids);

    /**
     * 新增员工
     * @param emp
     */
    public void save(Emp emp);

    /**
     * 根据id查询员工
     * @param id
     */
    public Emp selectById(Integer id);

    /**
     * 更新员工数据
     * @param emp
     */
    public void update(Emp emp);

    /**
     * 员工登录
     * @param emp
     * @return
     */
    public Emp login(Emp emp);


}
