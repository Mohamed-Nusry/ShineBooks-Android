package com.example.shinebookstore;





import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(JUnit4.class)
@LargeTest

public class UITestRegister {

    private String email = "Thomas@gmail.com";
    private String username = "Thomas";
    private String password = "Thomas111";

    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    public void registerUITest(){
        onView(withId(R.id.reg_email)).perform(typeText(email));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.reg_name)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.reg_pass)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_add)).perform(click());
    }

    @After
    public void tearDown(){

    }



}
