package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class Account extends BaseBo implements Serializable {

    private Long userId;
    /**
     * 证件号码
     */
    private String govtIdNo;
    /**
     * 证件类型
     */
    private Short govtIdType;
    /**
     * 银行卡号
     */
    private String bankAccountNo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 总充值金额
     */
    private Long depositSum;
    /**
     * 账户余额（充值类）
     */
    private Long depositBalance;
    /**
     * 总奖励金额
     */
    private Long rewardSum;
    /**
     * 账户余额（奖励类）
     */
    private Long rewardBalance;
    /**
     * 提现总额
     */
    private Long withdrawSum;
    /**
     * 可提现余额
     */
    private Long withdrawBalance;
    /**
     * 支付总额
     */
    private Long payoutSum;
    /**
     * 可支付余额
     */
    private Long payoutBalance;
    /**
     * 操作完成后账户余额
     */
    private Long balance;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getGovtIdNo() {
        return govtIdNo;
    }

    public void setGovtIdNo(String govtIdNo) {
        this.govtIdNo = govtIdNo;
    }

    public Short getGovtIdType() {
        return govtIdType;
    }

    public void setGovtIdType(Short govtIdType) {
        this.govtIdType = govtIdType;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public Long getDepositSum() {
        return depositSum;
    }

    public void setDepositSum(Long depositSum) {
        this.depositSum = depositSum;
    }

    public Long getDepositBalance() {
        return depositBalance;
    }

    public void setDepositBalance(Long depositBalance) {
        this.depositBalance = depositBalance;
    }

    public Long getRewardSum() {
        return rewardSum;
    }

    public void setRewardSum(Long rewardSum) {
        this.rewardSum = rewardSum;
    }

    public Long getRewardBalance() {
        return rewardBalance;
    }

    public void setRewardBalance(Long rewardBalance) {
        this.rewardBalance = rewardBalance;
    }

    public Long getWithdrawSum() {
        return withdrawSum;
    }

    public void setWithdrawSum(Long withdrawSum) {
        this.withdrawSum = withdrawSum;
    }

    public Long getWithdrawBalance() {
        return withdrawBalance;
    }

    public void setWithdrawBalance(Long withdrawBalance) {
        this.withdrawBalance = withdrawBalance;
    }

    public Long getPayoutSum() {
        return payoutSum;
    }

    public void setPayoutSum(Long payoutSum) {
        this.payoutSum = payoutSum;
    }

    public Long getPayoutBalance() {
        return payoutBalance;
    }

    public void setPayoutBalance(Long payoutBalance) {
        this.payoutBalance = payoutBalance;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
