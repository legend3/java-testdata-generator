package com.datalabels.generator;

import org.testng.annotations.Test;

public class VehicleVinTest {
    @Test
    public void TestVin() {
        String vin = VehicleVIN.getInstance().generate();
        System.err.println(vin);

//        VehicleVIN v = new VehicleVIN();
//        List<String> list = v.getVinList(10);
//        System.err.println(list);
    }
}
