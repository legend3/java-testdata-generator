package cn.binarywang.tools.generator;

import static org.testng.AssertJUnit.assertNotNull;

import org.testng.annotations.Test;

import cn.binarywang.tools.generator.base.GenericGenerator;

@Test
public class ChineseMobileNumberGeneratorTest {

    public void testGenerate() {
    	GenericGenerator instance = new ChineseMobileNumberGenerator();
        String generatedMobileNum = instance.generate();
        assertNotNull(generatedMobileNum);
        System.err.println(generatedMobileNum);
    }

    public void testGgenerateFake() {
    	ChineseMobileNumberGenerator instance = new ChineseMobileNumberGenerator();
        String generatedMobileNum = instance.generateFake();
        assertNotNull(generatedMobileNum);
        System.err.println(generatedMobileNum);
    }
}
