package com.course.testng.com.course.suite;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class SuiteConfig {

    @BeforeSuite
    public void beforeClass(){
        System.out.println("��ִ��ǰִ��");
    }

    @AfterSuite
    public void afterClass(){
        System.out.println("�࿪ʼ��ִ��");
    }
}
