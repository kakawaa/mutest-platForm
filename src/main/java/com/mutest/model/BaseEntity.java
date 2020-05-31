package com.mutest.model;

import lombok.Data;

import java.io.Serializable;
@Data
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 2054813493011812469L;

	private ID id;
//	private Date createTime = new Date();
//	private Date updateTime = new Date();
}
