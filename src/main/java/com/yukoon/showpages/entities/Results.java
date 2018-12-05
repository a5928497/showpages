package com.yukoon.showpages.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
public class Results {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;

	//0为旧 1为新
	@Column(name = "IS_NEW")
	private Integer isNew;

	@ManyToOne
	@JoinColumn(name = "BUSINESS_ID")
	private User business;

	@Column(name = "KEY1",columnDefinition = "TEXT")
	private String key1;
	@Column(name = "KEY2",columnDefinition = "TEXT")
	private String key2;
	@Column(name = "KEY3",columnDefinition = "TEXT")
	private String key3;
	@Column(name = "KEY4",columnDefinition = "TEXT")
	private String key4;
	@Column(name = "KEY5",columnDefinition = "TEXT")
	private String key5;
	@Column(name = "KEY6",columnDefinition = "TEXT")
	private String key6;
	@Column(name = "KEY7",columnDefinition = "TEXT")
	private String key7;
	@Column(name = "VALUE1",columnDefinition = "TEXT")
	private String value1;
	@Column(name = "VALUE2",columnDefinition = "TEXT")
	private String value2;
	@Column(name = "VALUE3",columnDefinition = "TEXT")
	private String value3;
	@Column(name = "VALUE4",columnDefinition = "TEXT")
	private String value4;
	@Column(name = "VALUE5",columnDefinition = "TEXT")
	private String value5;
	@Column(name = "VALUE6",columnDefinition = "TEXT")
	private String value6;
	@Column(name = "VALUE7",columnDefinition = "TEXT")
	private String value7;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public User getBusiness() {
		return business;
	}

	public void setBusiness(User business) {
		this.business = business;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getKey3() {
		return key3;
	}

	public void setKey3(String key3) {
		this.key3 = key3;
	}

	public String getKey4() {
		return key4;
	}

	public void setKey4(String key4) {
		this.key4 = key4;
	}

	public String getKey5() {
		return key5;
	}

	public void setKey5(String key5) {
		this.key5 = key5;
	}

	public String getKey6() {
		return key6;
	}

	public void setKey6(String key6) {
		this.key6 = key6;
	}

	public String getKey7() {
		return key7;
	}

	public void setKey7(String key7) {
		this.key7 = key7;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

	public String getValue6() {
		return value6;
	}

	public void setValue6(String value6) {
		this.value6 = value6;
	}

	public String getValue7() {
		return value7;
	}

	public void setValue7(String value7) {
		this.value7 = value7;
	}
}
