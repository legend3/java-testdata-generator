package com.datalabels.generator;

import com.datalabels.generator.base.GenericGenerator;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.RandomUtils;

import java.util.Map;

public class IMEI extends GenericGenerator {
    private static GenericGenerator instance = new IMEI();

    public IMEI() {

    }

    public static GenericGenerator getInstance() {//在单例模式(保证一个类仅有一个实例，并提供一个访问它的全局访问点<public>)的类中常见，用来生成唯一的实例，getInstance往往是static的
        return instance;
    }

    @Override
    public String generate() {
        /**
         * ————>不断叠加
         */
        Map<String,String> Code = getOrganization();
        String OrganizationCode = Code.keySet().toArray(new String[0])[RandomUtils.nextInt(0,Code.size())];

        String randNumber = String.valueOf(RandomUtils.nextInt(0,999999));

        String SNR = String.valueOf(RandomUtils.nextInt(0,999999));

        String Number14 = OrganizationCode + randNumber + SNR;
//        String Number14 = "35890180697241";

        /**
         * 校验码
         */
//        int s= 0;
//        int card_num_length = Number14.length();
////        for(int i = 2;i < (card_num_length+2);i++){
////                t = Number14.charAt(card_num_length - i + 1);
////            if(i % 2 == 0) {//偶数
////                t *= 2;
////                if (t < 10) {
////                    s += t;
////                } else {
////                    s += t % 10 + t / 10;
////
////                }
////            }else{
////                s += t;
////            }
////        }
//        for(int i =0 ; i<card_num_length; i++){
//            int t = Number14.charAt(i) - '0';
//            if(i %2 == 0){
//                s += t;
//            }else{
//                int temp = t * 2;
//                if(temp < 10){
//                    s += temp;
//                }else {
//                    s  += temp - 10 + 1;
//                }
//            }
//
//        }
//
////        System.out.println(s);
//        if((s % 10) == 0){
//            Number14 += "0";
//        }
//        else{
//            Number14 += 10- s % 10;
//        }

        String ImeiCheckNumber15    =   getimei15(Number14);
        String ImeiNumer    =   Number14 + ImeiCheckNumber15;


        return ImeiNumer;
    }

    public static Map<String,String> getOrganization(){
        final Map<String,String> map   =   Maps.newHashMap();
        map.put("01","CTIA");//美国
        map.put("35","BABT");//英国
        map.put("86","TF");//中国

        return map;
    }

    /**
     * 根据IMEI的前14位，得到第15位的校验位
     * IMEI校验码算法：
     * (1).将偶数位数字分别乘以2，分别计算个位数和十位数之和
     * (2).将奇数位数字相加，再加上上一步算得的值
     * (3).如果得出的数个位是0则校验位为0，否则为10减去个位数
     * 如：35 89 01 80 69 72 41 偶数位乘以2得到5*2=10 9*2=18 1*2=02 0*2=00 9*2=18 2*2=04 1*2=02,计算奇数位数字之和和偶数位个位十位之和，
     * 得到 3+(1+0)+8+(1+8)+0+(0+2)+8+(0+0)+6+(1+8)+7+(0+4)+4+(0+2)=63
     * 校验位 10-3 = 7
     * @param imei
     * @return
     */
    private static String getimei15(String imei){
        if (imei.length() == 14) {
            char[] imeiChar=imei.toCharArray();
            int resultInt=0;
            for (int i = 0; i < imeiChar.length; i++) {
                int a=Integer.parseInt(String.valueOf(imeiChar[i]));
                i++;
                final int temp=Integer.parseInt(String.valueOf(imeiChar[i]))*2;
                final int b=temp<10?temp:temp-9;
                resultInt+=a+b;
            }
            resultInt%=10;
            resultInt=resultInt==0?0:10-resultInt;
            return resultInt + "";
        }else{
            return "";
        }
    }
}
