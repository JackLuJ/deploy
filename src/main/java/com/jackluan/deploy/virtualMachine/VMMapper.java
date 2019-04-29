package com.jackluan.deploy.virtualMachine;

import com.jackluan.deploy.virtualMachine.entity.VMEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VMMapper {

    public void insert(VMEntity vmEntity);

    public void update(VMEntity vmEntity);

    public void delete(int id);

    public List<VMEntity> query(String id);

}
