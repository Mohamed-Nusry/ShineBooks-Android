package com.example.shinebookstore;



import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class UnitTestLogin {

    private String email = "john@gmail.com";
    private String password = "john111";

    boolean expected = true;

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void setUp(){
        //Todo:: before the test
    }

    @Test
    public void LoginTest(){

        boolean actual = loginActivityActivityTestRule.getActivity().logtest(email,password);
        Assert.assertEquals(expected,actual);
    }

    @After
    public void tearDown(){
        //TODO::After the test
    }

}


