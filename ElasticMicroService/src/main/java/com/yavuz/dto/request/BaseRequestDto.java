package com.yavuz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestDto {
    String token;
    /**
     * Her bir istekte göstermek istediğimiz kayıt adedi
     */
    Integer pageSize;
    /**
     * Şuan bulunduğunuz, talep ettiğimiz sayfa numarası
     */
    Integer currentPage;
    /**
     * Hangi alana göre sıralama yapılacaksa o alanın adı
     */
    String sortParameter;
    /**
     * sıralamanın yönü, a..Z, ASC,DESC
     */
    String direction;
}
