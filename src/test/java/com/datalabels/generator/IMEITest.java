package com.datalabels.generator;

import org.testng.annotations.Test;

@Test
public class IMEITest {
    public void testGenerate() {
        String number = IMEI.getInstance().generate();
        System.err.println(number);
    }
}
