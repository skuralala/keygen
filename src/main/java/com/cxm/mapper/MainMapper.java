package com.cxm.mapper;

import com.cxm.entity.OrgCode;
import com.cxm.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MainMapper {

    int deleteById(Integer id);


    @Insert("replace into orgcode(code) values(#{code})")
    int insertCode(String code);

    @Insert({
            "<script>",
            "replace into orgcode(code) values ",
            "<foreach collection='lists' item='item' index='index' separator=','>",
            "(#{item.code})",
            "</foreach>",
            "</script>"
    })
    int insertList(@Param(value = "lists") List<OrgCode> list);

    @Select("SELECT * FROM orgcode WHERE code=#{code}")
    OrgCode selectByCode(String code);

    @Select("SELECT * FROM orgcode")
    List<OrgCode> findAllCode();

    @Select("SELECT * FROM user WHERE username=#{username} and password=#{password}")
    User findUser(@Param("username") String username, @Param("password") String password);

}