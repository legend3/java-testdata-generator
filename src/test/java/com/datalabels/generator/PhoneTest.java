package com.datalabels.generator;

import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertNotNull;

public class PhoneTest {
    @Test
    public void testGenerate() {
        for(int i= 0;i<10;i++) {
            String generatedPhone = Phone.getInstance().generate();
            assertNotNull(generatedPhone);
            System.err.println(generatedPhone);
        }
    }
}
