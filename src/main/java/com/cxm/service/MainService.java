package com.cxm.service;

import com.cxm.entity.OrgCode;
import com.cxm.entity.User;
import com.cxm.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {

    @Autowired
    private MainMapper mainMapper;

    public int insertCode(String code){
        return mainMapper.insertCode(code);
    }

    public int insertList(List<OrgCode> list){
        return mainMapper.insertList(list);
    }

    public OrgCode findByCode(String code) {
        return mainMapper.selectByCode(code);
    }

    public List<OrgCode> findAllCode() {
        return mainMapper.findAllCode();
    }

    public User findByNameAndPwd(String username, String password){
        return mainMapper.findUser(username,password);
    }

}
