package com.cloud.model.common;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> implements Serializable {



	private static final long serialVersionUID = -275582248840137389L;
	private int total;
	private List<T> data;
	private long count;

	public Page(int total, List<T> data) {
		this.total = total;
		this.count =total;
		this.data = data;
	}
}
