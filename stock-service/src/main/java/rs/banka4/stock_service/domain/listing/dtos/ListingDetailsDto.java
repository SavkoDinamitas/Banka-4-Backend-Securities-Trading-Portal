package rs.banka4.stock_service.domain.listing.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
    description = "Dto for showing details after clicking on one listing, this Dto can be of 3 types.\n"
        + "Look for StockDto, ForexPairDto and FutureDto for exact information about each of them.\n"
        + "Type od dto can be found in securityType field."
)
public class ListingDetailsDto {
    private String name;
    private String ticker;
    @Schema(
        description = "Number of this security buys today",
        defaultValue = "10"
    )
    private Integer volume;
    @Schema(description = "Difference between current price and last price yesterday")
    private BigDecimal change;
    @Schema(description = "Current price")
    private BigDecimal price;
    @Schema(
        description = "Type of listed security, can be FOREX_PAIR, STOCK or FUTURE",
        defaultValue = "STOCK"
    )
    private SecurityType securityType;
}
