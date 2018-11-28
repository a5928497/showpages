package com.yukoon.showpages.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
public class Results {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;

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
}
