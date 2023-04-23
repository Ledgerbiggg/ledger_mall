package com.tulingxueyuan.tulingtest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ledger
 * @version 1.0
 **/
@SpringBootTest
public class test {
    @Test
    void test1(){
        String s=null;
        String s1 = s + "";
        System.out.println(s1);
    }
}
