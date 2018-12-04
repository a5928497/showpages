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
public class Colors {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "WELCOME_PAGE_FONT_COLOR")
    private String welcomePageFontColor;

    @Column(name = "DETAILS_PAGE_FONT_COLOR")
    private String detailsPageFontColor;

    @Column(name = "DETAILS_PAGE_BG_COLOR")
    private String detailsPageBackGroundColor;

    @OneToOne
    @JoinColumn(name = "BUSINESS_ID")
    private User business;
}
