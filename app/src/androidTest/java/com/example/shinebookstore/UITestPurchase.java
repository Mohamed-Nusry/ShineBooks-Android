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

public class UITestPurchase {

    private String p_bookname = "Fantastic beasts";


    @Rule
    public ActivityTestRule<PurchaseActivity> purchaseActivityActivityTestRule = new ActivityTestRule<PurchaseActivity>(PurchaseActivity.class);

    @Before
    public void setUp(){

    }

    @Test
    public void registerUITest(){
        onView(withId(R.id.p_bname)).perform(typeText(p_bookname));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_confirm)).perform(click());
    }

    @After
    public void tearDown(){

    }


}
