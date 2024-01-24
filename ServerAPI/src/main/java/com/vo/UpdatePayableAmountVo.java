package com.vo;

import com.constants.UpdatePayableAmnt;

public class UpdatePayableAmountVo {

	private float updateAmount;
	private UpdatePayableAmnt radioButtonValue;
	private String paperSize;
	
	public UpdatePayableAmountVo() {

	}
	
	public UpdatePayableAmountVo(float updateAmount, UpdatePayableAmnt radioButtonValue, String paperSize) {
		this.updateAmount = updateAmount;
		this.radioButtonValue = radioButtonValue;
		this.paperSize = paperSize;
	}

	public UpdatePayableAmnt getRadioButtonValue() {
		return radioButtonValue;
	}
	public float getUpdateAmount() {
		return updateAmount;
	}
	public void setUpdateAmount(float updateAmount) {
		this.updateAmount = updateAmount;
	}
	public void setRadioButtonValue(UpdatePayableAmnt radioButtonValue) {
		this.radioButtonValue = radioButtonValue;
	}
	public String getPaperSize() {
		return paperSize;
	}
	public void setPaperSize(String paperSize) {
		this.paperSize = paperSize;
	}
	
}
