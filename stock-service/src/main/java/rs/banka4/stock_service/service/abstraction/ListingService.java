package rs.banka4.stock_service.service.abstraction;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.banka4.stock_service.domain.listing.dtos.ListingFilterDto;
import rs.banka4.stock_service.domain.listing.dtos.ListingInfoDto;

public interface ListingService {
    int getVolumeOfAsset(UUID securityId);

    BigDecimal calculateChange(UUID securityId, BigDecimal currentPrice);

    // TODO resiti se ovoga pod hitno
    Page<ListingInfoDto> getListings(ListingFilterDto filter, Pageable pageable, boolean isClient);
}
