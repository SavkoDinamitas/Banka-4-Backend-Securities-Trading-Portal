package rs.banka4.stock_service.domain.listing.dtos.specific;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.banka4.stock_service.domain.listing.dtos.ListingDetailsDto;
import rs.banka4.stock_service.domain.security.future.db.UnitName;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Dto for additional fields for Future security")
public class FutureDto extends ListingDetailsDto {
    private long contractSize;
    private UnitName contractUnit;
    private OffsetDateTime settlementDate;
}
