package com.nchowf.tutorlinking.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private String streetNumber;
    private String ward;
    private String district;
    private String city;
}
