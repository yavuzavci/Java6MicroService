package com.yavuz.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder // BaseEntity miras alındığı için bunu kullanıyoruz
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
    Long createat;
    Long updateat;
    boolean state;
}
