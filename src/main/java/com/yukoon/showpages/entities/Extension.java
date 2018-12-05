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
public class Extension {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "EXTEND_LINK")
    private String extend_LINK;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID")
    private User business;
}
