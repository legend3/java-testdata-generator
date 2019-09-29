package com.datalabels.generator;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class CompanyNameTest {
    @Test
    public void testGenerateCompanyName(){
        String generateCompany = CompanyName.getInstance().generate();
        System.err.println(generateCompany);
        assertNotNull(generateCompany);
    }
}
