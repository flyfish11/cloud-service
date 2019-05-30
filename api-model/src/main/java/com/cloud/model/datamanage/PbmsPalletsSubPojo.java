package com.cloud.model.datamanage;


import lombok.Data;

import java.io.Serializable;
@Data
public class PbmsPalletsSubPojo implements Serializable {
	private static final long serialVersionUID = -9142507611344906748L;
	private String cigaretteId;
    private String cgInfoId;
	private String weight;
	private String circle;
	private String ovality;
	private String length;
	private String pdPa;
	private String cgFv;
	private String cgPv;
	private String cgTv;
	private String hardness;
	private String sendState;

}
