package org.example.service;
import org.example.pojo.Dept;

import java.util.List;

/**
 * 部门管理
 */
public interface DeptService {
    public List<Dept> list();
    public void delete(Integer id);

    public void add(Dept dept);

}
