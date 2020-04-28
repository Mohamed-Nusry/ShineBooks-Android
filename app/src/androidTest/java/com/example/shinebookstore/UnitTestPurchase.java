package com.example.shinebookstore;



import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class UnitTestPurchase {

    private String bookName = "Fantastic beasts";
    BuyProduct buyProduct = new BuyProduct(bookName);
    boolean expected = true;

    @Before
    public void setUp(){
        //Todo:: before the test
    }

    @Test
    public void RegisterTest(){

        try {

            boolean actual = buyProduct.execute().get();
            Assert.assertEquals(expected,actual);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown(){
        //TODO::After the test
    }

}
