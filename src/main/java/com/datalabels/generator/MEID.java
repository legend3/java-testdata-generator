package com.datalabels.generator;

import com.datalabels.generator.base.GenericGenerator;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigInteger;

/**
 * def checkMeidLst(self, content):
 *     '''
 *     校验MEID
 *     :param content:
 *     :return:
 *     '''
 *     try:
 *         # dic = {'A': 10, 'B': 11, 'C': 12, 'D': 13, 'E': 14, 'F': 15}
 *         l1 = ['A', 'B', 'C', 'D', 'E', 'F']
 *         l2 = [10, 11, 12, 13, 14, 15]
 *         dic1 = dict(zip(l1, l2))
 *         dic2 = dict(zip(l2, l1))
 *
 *         sum1 = 0
 *         sum2 = 0
 *         for i in range(14):
 *             con = content[i]
 *             if con in dic1:
 *                 con = dic1[con]
 *             else:
 *                 con = int(con)
 *
 *             if i % 2 == 0:
 *                 sum1 += con
 *             else:
 *                 sum2 += 2 * con % 16
 *                 sum2 += 2 * con / 16
 *
 *         sum = sum1 + sum2
 *         if sum % 16 == 0:
 *             check = 0
 *         else:
 *             check = 16 - sum % 16
 *             if check > 9:
 *                 check = dic2[check]
 *
 *         if str(check) == content[14]:
 *             return True
 *         else:
 *             return False
 *     except Exception as e:
 *         self.error(str(e), getCurrentFunctionName())
 *         return False
 */

public class MEID extends GenericGenerator {
    private static GenericGenerator instance = new MEID();
    private final static Integer[] Numbers = new Integer[]{0,1,2,3,4,5,6,7,8,9};
    private final static String[] alphabet =new String[]{"A","B","C","D","E","F"};

    public MEID() {

    }

    public static GenericGenerator getInstance() {
        return instance;
    }

    @Override
    public String generate() {
        /**
         * ————>不断叠加
         * 16进制转10进制算法
         * A = 10, B = 11, C =12 ...... F= 15
         * FFF  =  15*(16^2) + 15*(16^1) + 15*(16^0)  = 4095
         * 同理也可转换其他进制的
         * 比如15进制的567等于十进制的 5*(15^2) + 6*(15^1) + 7*(15^0) = 1222
         *
         * 10进制转16进制算法
         * 除16取余数得最低1位，然后把商继续除得第2位，直到商等于0
         * 65036 除 16，余数 12(C)，商4064
         * 4064 除 16，余数 0(0)，商254
         * 254 除 16，余数 14(E)，商15
         * 15除16，余数 15(F)，商0，结束
         * 得16进制为 FE0C
         *
         *
         * RR：范围A0-FF(160~255)，由官方分配     A0 = A*16^1 + 0*16^0 = 10*16^1 + 0*16^0 = 160 + 0  = 160    FF = 15*16^1 + 15*16^0 = 255
         * XXXXXX：范围 000000-FFFFFF，由官方分配
         * ZZZZZZ：范围 000000-FFFFFF，厂商分配给每台终端的流水号
         * C/CD：0-F，校验码
         */

        /**
         * RR
         */
        String RR = String.valueOf(RandomUtils.nextInt(Integer.parseInt("A0",16),new BigInteger(new BigInteger("FF", 16).toString(10)).intValue()));
        /**
         * XXXXXX
         */
        String XXXXXX = String.valueOf(RandomUtils.nextInt(Integer.parseInt("000000",16),new BigInteger(new BigInteger("FFFFFF", 16).toString(10)).intValue()));
        /**
         * ZZZZZZ
         */
        String ZZZZZ = String.valueOf(RandomUtils.nextInt(Integer.parseInt("000000",16),new BigInteger(new BigInteger("FFFFFF", 16).toString(10)).intValue()));//16777215
        /**
         * 前14位
         */
        String r = new BigInteger(RR , 10).toString(16);//十进制转十六进制
        String x = new BigInteger(XXXXXX, 10).toString(16);//十进制转十六进制
        String z = new BigInteger(ZZZZZ, 10).toString(16);//十进制转十六进制
        String Number14 = r+x+z;
        String MeidNumer    =   Number14;

//        String checkNumber15    =   getmeid15(Number14);
//        String MeidNumer    =   Number14 + checkNumber15;

        /**
         * 验证码
         */
//    Map<String,Integer> meidMap1 = getAlphabet_Number();
//    Map<Integer,String> meidMap2 = getNumber_Alphabet();

//    int sum1 = 0;
//    int sum2 = 0;
//    for(int i = 0;i<14;i++){
//       int t = Integer.valueOf(Number14.charAt(i));//*
//        if(meidMap1.containsValue(t)){
//            t = meidMap1.get(t);
//        }else{
//            t = t;
//        }
//        if(i % 2 == 0){
//            sum1 += t;
//        }else{
//            sum2 += 2 * t % 16;
//            sum2 += 2 * t / 16;
//        }
//    }
    return MeidNumer;
    }

//    public	static Map<String,Integer> getAlphabet_Number(){
//        final Map<String,Integer> map = Maps.newHashMap();
//        map.put("A",10);
//        map.put("B",11);
//        map.put("C",12);
//        map.put("D",13);
//        map.put("E",14);
//        map.put("F",15);
//
//        return map;
//    }
//
//    public	static Map<Integer,String> getNumber_Alphabet(){
//        final Map<Integer,String> map = Maps.newHashMap();
//        map.put(10,"A");
//        map.put(11,"B");
//        map.put(12,"C");
//        map.put(13,"D");
//        map.put(14,"E");
//        map.put(15,"F");
//
//        return map;
//    }

    /**
     * 根据MEID的前14位，得到第15位的校验位
     * MEID校验码算法：
     * (1).将偶数位数字分别乘以2，分别计算个位数和十位数之和，注意是16进制数
     * (2).将奇数位数字相加，再加上上一步算得的值
     * (3).如果得出的数个位是0则校验位为0，否则为10(这里的10是16进制)减去个位数
     * 如：AF 01 23 45 0A BC DE 偶数位乘以2得到F*2=1E 1*2=02 3*2=06 5*2=0A A*2=14 C*2=18 E*2=1C,
     * 	计算奇数位数字之和和偶数位个位十位之和，得到 A+(1+E)+0+2+2+6+4+A+0+(1+4)+B+(1+8)+D+(1+C)=64
     *  校验位 10-4 = C
     * @param meid
     * @return
     */
//    public static String getmeid15(String meid) {
//        if (meid.length() == 14) {
//            String myStr[] = { "a", "b", "c", "d", "e", "f" };
//            int sum = 0;
//            for (int i = 0; i < meid.length(); i++) {
//                String param = meid.substring(i, i + 1);
//                for (int j = 0; j < myStr.length; j++) {
//                    if (param.equalsIgnoreCase(myStr[j])) {
//                        param = "1" + String.valueOf(j);
//                    }
//                }
//                if (i % 2 == 0) {
//                    sum = sum + Integer.parseInt(param);
//                } else {
//                    sum = sum + 2 * Integer.parseInt(param) % 16;
//                    sum = sum + 2 * Integer.parseInt(param) / 16;
//                }
//            }
//            if (sum % 16 == 0) {
//                return "0";
//            } else {
//                int result = 16 - sum % 16;
//                if (result > 9) {
//                    result += 65 - 10;
//                }
//                return result + "";
//            }
//        } else {
//            return "";
//        }
//    }
}
