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
public class Extension {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "EXTEND_ORDER")
    private Integer order;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "EXTEND_LINK")
    private String extend_LINK;

    @Column(name = "IMG_NAME")
    private String imgName;

    @ManyToOne
    @JoinColumn(name = "BUSINESS_ID")
    private User business;
}
