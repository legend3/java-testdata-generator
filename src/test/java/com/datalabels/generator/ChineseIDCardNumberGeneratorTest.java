package com.datalabels.generator;

import static org.testng.Assert.assertNotNull;

import java.util.Date;

import org.testng.annotations.Test;

import cn.binarywang.tools.generator.base.GenericGenerator;

public class ChineseIDCardNumberGeneratorTest {

    @Test
    public void generateRandomDate() {
        Date randomDate = ChineseIDCardNumberGenerator.randomDate();
        System.err.println(randomDate);
        assertNotNull(randomDate);
    }
//123456 -2 = 1234  360302 19970814 4510
    @Test
    public void testGenerate() {
    	GenericGenerator instance = new ChineseIDCardNumberGenerator();
        String idCard = instance.generate();
        System.err.println(idCard);
        assertNotNull(idCard);
        if (idCard.charAt(idCard.length()-2)%2 == 0){
            System.out.println();
            System.err.println("女");
        } else {
            System.err.println("男");
        }
    }

    @Test
    public void testGenerateIssueOrg() {
        String issueOrg = ChineseIDCardNumberGenerator.generateIssueOrg();
        System.err.println(issueOrg);
        assertNotNull(issueOrg);
    }

    @Test
    public void testGenerateValidPeriod() {
        String result = ChineseIDCardNumberGenerator.generateValidPeriod();
        System.err.println(result);
        assertNotNull(result);
    }

}
