package com.datalabels.generator;

import com.datalabels.generator.util.ChineseCharUtils;
import com.datalabels.generator.base.GenericGenerator;

public class CompanyName extends GenericGenerator {
    private static GenericGenerator instance = new CompanyName();
    private int count = 0;

//    public CompanyName() {
//    }

    public static GenericGenerator getInstance() {
        return instance;
    }

    @Override
    public  String generate() {

        if (count % 2 == 1) {//奇数
            StringBuilder result = new StringBuilder(ChineseCharUtils.genRandomLengthChineseChars(2, 10) + "有限公司");
            count++;
            return result.toString();
        } else if (count % 2 == 0) {//偶数
            StringBuilder result = new StringBuilder(ChineseCharUtils.genRandomLengthChineseChars(2, 3) + "有限公司");
            count++;
            return result.toString();
        } else if (count % 3 == 0) {//3的�?�数
            StringBuilder result = new StringBuilder(ChineseCharUtils.genRandomLengthChineseChars(2, 10) + "有限公司");
            count++;
            return result.toString();
        } else if(count % 10 == 0) {//10的�?�数
            StringBuilder result = new StringBuilder(ChineseCharUtils.genRandomLengthChineseChars(2, 10) + "有限公司");
            count++;
            return result.toString();
        }else{
            System.out.println("没有�?");
        }
        return null;
    }
}
