package rs.banka4.stock_service.domain.listing.dtos;

import java.math.BigDecimal;

public record ListingInfoDto(
    String name,
    String ticker,
    Integer volume,
    BigDecimal change,
    BigDecimal price
) {
}
