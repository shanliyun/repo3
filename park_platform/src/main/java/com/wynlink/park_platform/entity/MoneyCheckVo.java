package com.wynlink.park_platform.entity;

import java.io.Serializable;

public class MoneyCheckVo implements Serializable {

	public MoneyCheckVo() {
		super();
	}

	public MoneyCheckVo(String orderNo, String payTime, String money) {
		super();
		this.orderNo = orderNo;
		this.payTime = payTime;
		this.money = money;
	}

	private String orderNo;

	private String payTime;
	
	private String money;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((money == null) ? 0 : money.hashCode());
		result = prime * result + ((orderNo == null) ? 0 : orderNo.hashCode());
		result = prime * result + ((payTime == null) ? 0 : payTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MoneyCheckVo other = (MoneyCheckVo) obj;
		if (money == null) {
			if (other.money != null)
				return false;
		} else if (!money.equals(other.money))
			return false;
		if (orderNo == null) {
			if (other.orderNo != null)
				return false;
		} else if (!orderNo.equals(other.orderNo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MoneyCheckVo [orderNo=" + orderNo + ", payTime=" + payTime + ", money=" + money + "]";
	}
	
	
}
