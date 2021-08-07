package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrue",description = "更改用户信息")
    public void updateUserInfo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlsession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
    }
}
