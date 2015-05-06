package com.oneshotmc.tntplus.tntfiller;

public class AveragePlus {
	int value;
	int amount;
	public AveragePlus(int value,int amount){
		this.value=value;
		this.amount=amount;
	}
	public int getRemainer(){
		return value%amount;
	}
	public int getBottom(){
		return (int) Math.floor(value/amount);
	}
	public int getTop(){
		return (int) Math.ceil(value/amount);
	}
}
