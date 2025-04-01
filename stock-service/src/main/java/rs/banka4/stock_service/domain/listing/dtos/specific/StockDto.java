package rs.banka4.stock_service.domain.listing.dtos.specific;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.banka4.stock_service.domain.listing.dtos.ListingDetailsDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto for additional fields for Stock security")
public class StockDto extends ListingDetailsDto {
    private long outstandingShares;
    private BigDecimal dividendYield;
    private OffsetDateTime createdAt;
}
