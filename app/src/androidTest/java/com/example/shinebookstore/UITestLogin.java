package com.example.shinebookstore;



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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(JUnit4.class)
@LargeTest

public class UITestLogin {

    private String email = "john@gmail.com";
    private String email2 = "john112@gmail.com";
    private String password = "john111";

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    public void loginUITest(){
        onView(withId(R.id.uemail)).perform(typeText(email));
        closeSoftKeyboard();
        onView(withId(R.id.upass)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.btnlogin)).perform(click());
    }

    @Test
    public void loginUITestWrong(){
        onView(withId(R.id.uemail)).perform(typeText(email2));
        closeSoftKeyboard();
        onView(withId(R.id.upass)).perform(typeText(password));
        closeSoftKeyboard();
        onView(withId(R.id.btnlogin)).perform(click());
    }

    @After
    public void tearDown(){

    }


}
