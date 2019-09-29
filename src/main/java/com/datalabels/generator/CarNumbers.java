package com.datalabels.generator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.datalabels.generator.base.GenericGenerator;

/**
 1.常规车牌号：仅允许以汉字�?头，后面可录入六个字符，由大写英文字母和阿拉伯数字组成�?�如：粤B12345�?
 2.武警车牌：允许前两位为大写英文字母，后面可录入五个或六个字符，由大写英文字母和阿拉伯数字组成，其中第三位可录汉字也可录大写英文字母及阿拉伯数字，第三位也可空，如：WJ�?00081,WJ�?1234J,WJ1234X�?
    2.1 新式号牌则改�?"WJ"武警拼音首字母与省份汉字�?称，外加单位车辆编制序列的数字组成�??
    2.2 组成�?
        a.红色�?"武警"汉语拼音首字�?"WJ"
        b.各省份汉字简�?
        c.单位车辆编制序列�?5位数字或4位数字和警种标志
        "X"消防部队
     �?�?"B"边防部队
     �?�?"T"交�?�部�?
     �?�?"S"森林部队
     �?�?"H"黄金部队
     �?�?"J"警卫部队
     �?�?"D"水电部队
 3.新能源车：常规车牌号基础上，增加第三位为字母“D”代表纯电动汽车或字母�?? F”代表非纯电动汽车（包括插电式混合动力和燃料电池汽车等）
 4.�?后一个为汉字的车牌：允许以汉字开头，后面可录入六个字符，前五位字符，由大写英文字母和阿拉伯数字组成，而最后一个字符为汉字，汉字包�?"�?","�?","�?","�?","�?","�?"。如：粤Z1234港�??
 5.新军车牌：以两位为大写英文字母开头，后面�?5位阿拉伯数字组成。如：BA12345�?
 */
public class CarNumbers extends GenericGenerator {
    private static final String[] province = new String[] { "京", "津", "沪", "渝",
    	    "冀", "豫", "云", "辽", "黑", "湘", "皖", "鲁", "苏", "浙", "赣",
    	    "鄂", "甘", "晋", "陕", "吉", "闽", "贵", "粤", "青", "川", "琼",
    	    "宁", "新", "藏", "桂", "蒙",
    	    "港", "澳", "台" };
    private static final int[] number = new int[] {0,1,2,3,4,5,6,7,8,9 };
    private static final String[] alphabet = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final String[] alphabet_number = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z","0","1","2","3","4","5","6","7","8","9"};

    private static final String[] policeClassification = new String[] {"X","B","T","S","H","J","D","J","D"};

//    private static final String[] LastCharacter = new String[] {"�?","�?","�?","�?","�?","�?"};
    private static final String[]  NewEnergyLabel = new String[]{"D","F"};


    List<String> globalCarNumber = new ArrayList<String>();

    /**
     * 单例模式
     */
    private static GenericGenerator instance = new CarNumbers();

    public CarNumbers() {
    }

    public static GenericGenerator getInstance(){
        return instance;
    }

    @Override
    public String generate() {
        /**
         * 私家车
         */
            String ConventionPre = province[RandomUtils.nextInt(0,province.length)];
            String Conventionalphabet = alphabet[RandomUtils.nextInt(0,alphabet.length)].toUpperCase();
            String ConventionSuffix = "";
            for(int i =0;i<5;i++) {
                ConventionSuffix += number[RandomUtils.nextInt(0, number.length)];
            }
            String Convention = ConventionPre + Conventionalphabet + ConventionSuffix;
        globalCarNumber.add(Convention);
        /**
         * 武警�?
         */
            String ArmedPoliceSuffix4 = "";


            String ArmedPolicePre = "WJ";
            String thirdCharacter = province[RandomUtils.nextInt(0,province.length)];
            for(int i =0;i<4;i++) {
                ArmedPoliceSuffix4 += number[RandomUtils.nextInt(0, number.length)];
            }
            StringBuilder ArmedPoliceSuffix = new StringBuilder(ArmedPoliceSuffix4)
                .append(policeClassification[RandomUtils.nextInt(0,policeClassification.length)]);

            String ArmedPolice = ArmedPolicePre + thirdCharacter + ArmedPoliceSuffix.toString();
        globalCarNumber.add(ArmedPolice);
        /**
         * 电动�?
         */
            String NewEnergyPre = province[RandomUtils.nextInt(0,province.length)];
            String NewEnergyLabels = NewEnergyLabel[RandomUtils.nextInt(0,NewEnergyLabel.length)];
            String NewEnergyAlphabet = alphabet[RandomUtils.nextInt(0,alphabet.length)].toUpperCase();
            String NewEnergySuffix = "";
            for(int i =0;i<5;i++) {
                NewEnergySuffix += alphabet_number[RandomUtils.nextInt(0, alphabet_number.length)].toUpperCase();
            }
            String NewEnergy = NewEnergyPre + NewEnergyAlphabet + NewEnergyLabels +NewEnergySuffix;
        globalCarNumber.add(NewEnergy);

        String randCarNumber = globalCarNumber.get(RandomUtils.nextInt(0,globalCarNumber.size()));
        return randCarNumber;
    }



    /**
     * 军车
     */
//    public String getLiberationArmy(){
//
//        return null;
//    }

    /**
     * �?后一个为汉字的车�?
     */
//    public String getLastCharacters(){
//        String LastPre = province[RandomUtils.nextInt(0,province.length)];
//        String LastSuffix = "";
//        for(int i =0;i<5;i++) {
//            LastSuffix += alphabet_number[RandomUtils.nextInt(0, alphabet_number.length)].toUpperCase();
//        }
//        StringBuilder lastBuild = new StringBuilder(LastSuffix).append(LastCharacter[RandomUtils.nextInt(0,LastCharacter.length)]);
//        String Last = LastPre + LastSuffix;
//        return null;
//    }
}
