package com.ghsh.code.bean;

/**
 * 银行
 * */
public class TBank {

	private String bankID;// 编号

	private String bankName;// 名称

	public TBank(){
		
	}
	
	public TBank(String bankID,String bankName){
		this.bankID=bankID;
		this.bankName=bankName;
	}
	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return bankName;
	}

}
