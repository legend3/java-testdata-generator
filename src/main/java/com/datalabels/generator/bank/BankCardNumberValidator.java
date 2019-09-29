package com.datalabels.generator.bank;

import com.datalabels.generator.util.LuhnUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 银行卡号校验类
 *
 */
public class BankCardNumberValidator {

    /**
     * 校验银行卡号是否合法
     *
     * @param cardNo 银行卡号
     * @return 是否合法
     */
    public static boolean validate(String cardNo) {
        if (StringUtils.isEmpty(cardNo)) {
            return false;
        }

        if (!NumberUtils.isDigits(cardNo)) {
            return false;
        }

        if (cardNo.length() > 19 || cardNo.length() < 16) {
            return false;
        }

        int luhnSum = LuhnUtils.getLuhnSum(cardNo.substring(0, cardNo.length() - 1).trim().toCharArray());
        char checkCode = (luhnSum % 10 == 0) ? '0' : (char) ((10 - luhnSum % 10) + '0');
        return cardNo.substring(cardNo.length() - 1).charAt(0) == checkCode;
    }


}
