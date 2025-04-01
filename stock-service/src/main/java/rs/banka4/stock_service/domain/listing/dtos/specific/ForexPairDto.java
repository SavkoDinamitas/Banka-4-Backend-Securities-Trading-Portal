package rs.banka4.stock_service.domain.listing.dtos.specific;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.banka4.stock_service.domain.listing.dtos.ListingDetailsDto;
import rs.banka4.stock_service.domain.security.forex.db.CurrencyCode;
import rs.banka4.stock_service.domain.security.forex.db.ForexLiquidity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto for additional fields for ForexPair security")
public class ForexPairDto extends ListingDetailsDto {
    private CurrencyCode baseCurrency;
    private CurrencyCode quoteCurrency;
    private ForexLiquidity liquidity;
    private BigDecimal exchangeRate;
}
