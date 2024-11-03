package org.example.service.impl;

import org.example.mapper.DeptMapper;
import org.example.mapper.EmpMapper;
import org.example.pojo.Dept;
import org.example.pojo.DeptLog;
import org.example.service.DeptLogService;
import org.example.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private DeptLogService deptLogService;
    @Override
    public List<Dept> list(){
        return deptMapper.list();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id){
        try {
            deptMapper.deleteById(id);
            empMapper.deleteByDeptId(id);
        } finally {
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("解散了"+id+"部门");
            deptLogService.insert(deptLog);
        }



    }

    @Override
    public void add(Dept dept){
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

}
