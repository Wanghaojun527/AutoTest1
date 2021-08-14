package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;


public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfo() throws Exception {
        SqlSession session = DatabaseUtil.getSqlsession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",2);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        JSONArray resultJson = getJsonResult(getUserInfoCase);
        System.out.println(resultJson.toString());
        JSONObject expect = (JSONObject) resultJson.get(0);
        System.out.println(expect.toString());
        Thread.sleep(5000);
        SqlSession session1 = DatabaseUtil.getSqlsession();
        User user = session1.selectOne("getUserInfo",2);
        System.out.println(user.toString());
        Assert.assertEquals(expect.getInt("id"),user.getId());

    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws Exception{
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCase.getId());
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        post.setHeader("content-type","application/json");
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray array = new JSONArray(result);
        return array;
    }
}
