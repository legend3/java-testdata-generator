package com.datalabels.generator;

import com.datalabels.generator.base.GenericGenerator;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class VehicleVIN extends GenericGenerator {
    private static GenericGenerator instance = new VehicleVIN();

    public VehicleVIN() {
    }

    public static GenericGenerator getInstance(){
        return instance;
    }

    @Override
    public String generate() {
        String beforeStr = prepareBeforeStr();
//        System.out.println(beforeStr);
        String afterStr = prepareAfterStr();
//        System.out.println(afterStr);
        String vin = spellVin(beforeStr, afterStr);
        while(vin.matches("\\d+")){//递归经典案例
        	vin = generate();
        }
//        System.out.println(vin);
        return vin;
    }

    /**
     * ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
     * VIN码详解--- 1
     * 第1位：生产国家代码---3
     * 第2位：汽车制造商代码---3
     * 第3位：汽车类型代码---3
     * 第4~8位(vds)车辆特征---6
     * 第9位：校验位---7
     * 第10位：车型年款---7
     * 第11位：装配厂---7
     * 第12~17位：顺序号---7
     */

    //生产国家代码
    public static final String areaArray[] = new String[]{"1", "2", "3", "6", "9", "J", "K", "L", "R", "S", "T", "V", "W", "Y", "Z", "G"};

    //车架号中可能出现的字符数组
    public static final String charArray[] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "V", "W", "X", "Y"};

    //车架号校验位计算数组
    public static final Object[][] KVMACTHUP = new Object[][]{{'A', 1}, {'B', 2}, {'C', 3}, {'D', 4}, {'E', 5}, {'F', 6},{'G', 7}, {'H', 8}, {'I', 0}, {'J', 1}, {'K', 2}, {'L', 3},{'M', 4}, {'N', 5}, {'O', 0}, {'P', 7}, {'Q', 8}, {'R', 9}, {'S', 2}, {'T', 3}, {'U', 4}, {'V', 5}, {'W', 6}, {'X', 7},{'Y', 8}, {'Z', 9}};

    //车架号数据加权数组
    public static final int[] WEIGHTVALUE = new int[]{8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2};

/* //车辆特征
    public static Map<String, String> getCarType() {
        final Map<String, String> map = Maps.newHashMap();
        return map;
    }

    //车辆特征
    public static Map<String, String> getVehicleFeature() {
        final Map<String, String> map = Maps.newHashMap();
        return map;
    }

    //校验位
    public static Map<String, String> getCheckDigit() {
        final Map<String, String> map = Maps.newHashMap();
        return map;
    }

    //车型年款
    public static Map<String, String> getCarModel() {
        final Map<String, String> map = Maps.newHashMap();
        return map;
    }

    //装配厂
    public static Map<String, String> getAssemblyPlant() {
        final Map<String, String> map = Maps.newHashMap();
        return map;
    }

    //顺序号
    public static Map<String, String> getSequenceNumber() {
        final Map<String, String> map = Maps.newHashMap();
        return map;
    }
*/

    /**
     * 计算车架号的校验位
     * @return
     */
    public String getIsuredCode(String vin) {
        char[] Vin = vin.toCharArray();
        int sum = 0,tempValue = 0;
        char temp;
        for (int i = 0; i < 17; i++) {
            if (Vin[i] >= 'a' && Vin[i] <= 'z') {
                temp = (char) (Vin[i] - 32);
            } else if ((Vin[i] >= 'A') && (Vin[i] <= 'Z')) {
                temp = Vin[i];
            } else if ((Vin[i] >= '0') && (Vin[i] <= '9')) {
                tempValue = Integer.parseInt(String.valueOf(Vin[i]));
                temp = Vin[i];
            } else {
                return "ERROR";
            }
            if ((temp >= 'A') && (temp <= 'Z')) {
                for (int j = 0; j < 26; j++) {
                    if (temp == (KVMACTHUP[j][0]+"").charAt(0)) {
                        tempValue = (int) KVMACTHUP[j][1];
                    }
                }
            }
            sum += tempValue * WEIGHTVALUE[i];
        }
        int reslt = sum % 11;
        if (reslt != 10) {
            return String.valueOf(reslt);
        } else {
            return "X";
        }
    }

    /**
     * 判断vin是否正确
     * @param vin
     * @return
     */
    public boolean isVin(String vin) {
        String isuredCode = getIsuredCode(vin);
        if (vin.substring(8, 9).equals(isuredCode)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 拼接车架号
     *
     * @param beforeStr
     * @param afterStr
     * @return
     */
    public String spellVin(String beforeStr, String afterStr) {
        StringBuffer vinBuffer = new StringBuffer();
        String preVin = vinBuffer.append(beforeStr).append("X").append(afterStr).toString();
        String isuredCode = getIsuredCode(preVin);//校验码
//        System.out.println(isuredCode);
        String vin = new StringBuffer(beforeStr).append(isuredCode).append(afterStr).toString();
        if (isVin(vin)) {
            return vin;
        } else {
            return spellVin(beforeStr, afterStr);
        }
    }

    /**
     * 生成随机前缀
     *
     * @return
     */
    public String prepareBeforeStr() {
        List<String> alphabetList = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z","0","1","2","3","4","5","6","7","8","9"));
        List<String> OUT = new ArrayList<>(Arrays.asList("J", "K", "L", "R", "S", "T", "V", "W", "Y", "Z", "1","2","3","4","5","6","9"));
        List<String> list = new ArrayList<>();
        Random random = new Random();

        list.add(OUT.get(RandomUtils.nextInt(0, OUT.size())).toUpperCase());
        while(list.size() < 3) {
            list.add(alphabetList.get(random.nextInt(alphabetList.size())).toUpperCase());
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(StringUtils.join(list,""));//StringUtils.join(threeSet,"")集合转字符串("",用什么符号隔开)
	    for (int i = 0; i < 5; i++) {
	    	  stringBuffer.append(getRandomChar(areaArray));
	    }
        return stringBuffer.toString();
    }

    /**
     * 生成随机后缀
     *
     * @return
     */
    public String prepareAfterStr() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            stringBuffer.append(getRandomChar(charArray));
        }
        //第十位不为0
        while(String.valueOf(stringBuffer.charAt(0)).equals("0")){
        	stringBuffer.replace(0, 1, getRandomChar(charArray));
        }
        stringBuffer.append(prepareNo());
//        System.out.println("后缀：" + stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * 生成随机的生产序号(十进制 or 十六进制？)
     * @return
     */
    public String prepareNo(){
        Random random = new Random();
        StringBuffer numStrBuff = new StringBuffer();
        for(int i=0;i<5;i++){
            numStrBuff.append(random.nextInt(9));//Integer.toHexString
        }
//        System.out.println("生产序号：" + numStrBuff.toString());
        return numStrBuff.toString();
    }
    /**
     * 返回随机字符
     * @return
     */
    public String getRandomChar(Object array[]) {
        return ((int) (Math.random() * 10)) + "";
    }

    /**
     * 批量产生
     * @param num
     * @return
     */
    public List<String> getVinList(int num){
        List<String> vinList = new ArrayList<>();
        for(int i =0;i<num;i++){
            vinList.add(generate());
        }

        return vinList;
    }

    public static boolean isHave(String[] strs,StringBuffer s){

    	/*此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串*/
    	  int i = strs.length;
    	        while (i-- > 0){
    	            if(!(strs[i].equals(s.charAt(0)))){
    	                return false;
    	            }
    	        }
    	        return true;
    	}

//    public static void main(String[] args) {
//    	String s = "";
//    	for(int i=0;i<=100000;i++){
//    	GenericGenerator v = new VehicleVIN();
//    	s = v.generate();
//    	try{
//    		BigDecimal big = new BigDecimal(s);
//    		System.out.println(s + "匹配");
//    	}catch(Exception e){
//    		}
//    	}
//    	System.out.println("结束！");
//    }
}
