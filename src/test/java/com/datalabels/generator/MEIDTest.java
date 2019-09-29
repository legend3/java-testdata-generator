package com.datalabels.generator;

import org.testng.annotations.Test;

public class MEIDTest {
    @Test
    public void testmeid(){
//        for(int i =0;i<=1;i++) {
            String number = MEID.getInstance().generate();
            System.err.println(number);
//        }
    }

}
