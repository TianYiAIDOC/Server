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
public class AccountDetail extends BaseBo implements Serializable {

    private Long accountId;
    /**
     * 充值金额
     */
    private Long depositAmount;
    /**
     * 支付金额
     */
    private Long paymentAmount;
    /**
     * 奖励金额
     */
    private Long rewardAmount;
    /**
     * 提现金额
     */
    private Long withdrawAmount;
    /**
     * 操作完成后账户余额
     */
    private Long balance;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 交易流水号
     */
    private String tranNo;
    /**
     * 订单号
     */
    private String orderNo;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Long depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Long getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public Long getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Long withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
