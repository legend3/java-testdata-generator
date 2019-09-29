package com.datalabels.generator;

import org.testng.annotations.Test;

public class CarNumberTest {
    @Test
    public void testGenerate() {
        for(int i=0;i<10;i++) {
            String generatedCarNumber = CarNumbers.getInstance().generate();
//        CarNumbers c = new CarNumbers();
//        System.err.println(c.getNewEnergy());
            System.err.println(generatedCarNumber);
        }
    }
}
