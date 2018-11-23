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
public class WelcomeInfo {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;

	@Column(name = "PARAGRAPH",columnDefinition = "TEXT")
	private String paragraph;

	@OneToOne
	@JoinColumn(name = "BUSINESS_ID")
	private User business;
}
