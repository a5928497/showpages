package com.yukoon.showpages.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Entity
public class CustomField {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;

	//0关1开
	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "FIELD_ORDER")
	private Integer order;

	@Column(name = "FIELD_TYPE")
	private Integer type;

	@Column(name = "FIELD_TITLE")
	private String title;

	@Column(name = "FIELD_CONDITION")
	private String condition;

	@JoinColumn(name = "BUSINESS_ID")
	@ManyToOne
	private User business;
}
