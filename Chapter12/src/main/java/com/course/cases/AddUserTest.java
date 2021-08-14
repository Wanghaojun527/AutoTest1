package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue",description = "����û��ӿڲ���")
    public void addUser() throws Exception {
        SqlSession session = DatabaseUtil.getSqlsession();
        AddUserCase addUserCase = session.selectOne("addUserCase",3);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //������,��ȡ���
        String result = getResult(addUserCase);
        //��֤���ؽ��
        Thread.sleep(5000);
        SqlSession session1 = DatabaseUtil.getSqlsession();
        User user = session1.selectOne("addUser",3);
        System.out.println(user.toString());
        Assert.assertEquals(addUserCase.getExpected(),result);
    }
    private String getResult(AddUserCase addUserCase) throws Exception{
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("id",addUserCase.getId());
        param.put("userName",addUserCase.getUserName());
        param.put("passWord",addUserCase.getPassWord());
        param.put("age",addUserCase.getAge());
        param.put("sex",addUserCase.getSex());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        //����ͷ��Ϣ
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //����cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;//��ŷ��ؽ��
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        return result;
    }
}
