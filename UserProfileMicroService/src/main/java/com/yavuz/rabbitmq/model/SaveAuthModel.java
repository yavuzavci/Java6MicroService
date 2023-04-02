package com.yavuz.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * ÖNEMLİ!!!
 * Modeller mutlaka serileştirilmelidir.
 * Ayrıca paket ismi dahil olmak üzere bu sınıfın aynısı iletilen serviste de olmalıdır.
 */
public class SaveAuthModel implements Serializable {
    Long authid;
    String username;
    String email;
}
