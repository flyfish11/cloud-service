package com.cloud.model.datamanage;



import lombok.Data;

import java.io.Serializable;

@Data
public class PbmsPalletsPojo implements Serializable {
	private static final long serialVersionUID = -8119785820706288884L;
	private String cgInfoId;
	private String checkTime;
	private String treademark;
	private String machineName;
	private String manuTeam;
	private String meaType;
	private String instName;
	private String workshop;
	private String checkCount;
	private String weightMid;
	private String weightUp;
	private String weightDown;
	private String circleMid;
	private String circleUp;
	private String circleDown;
	private String ovalityMid;
	private String ovalityUp;
	private String ovalityDown;
	private String lengthMid;
	private String lengthUp;
	private String lengthDown;
	private String pdMid;
	private String pdUp;
	private String pdDown;
	private String pvMid;
	private String pvUp;
	private String pvDown;
	private String fvMid;
	private String fvUp;
	private String fvDown;
	private String tvMid;
	private String tvUp;
	private String tvDown;
	private String hardnessMid;
	private String hardnessUp;
	private String hardnessDown;
	private String sendState;

}
