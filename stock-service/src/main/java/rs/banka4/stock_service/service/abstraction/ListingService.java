package rs.banka4.stock_service.service.abstraction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import rs.banka4.stock_service.domain.listing.dtos.ListingDetailsDto;
import rs.banka4.stock_service.domain.listing.dtos.ListingDto;
import rs.banka4.stock_service.domain.listing.dtos.OptionDto;
import rs.banka4.stock_service.domain.listing.dtos.PriceChangeDto;

public interface ListingService {
    int getVolumeOfAsset(UUID securityId);

    BigDecimal calculateChange(UUID securityId, BigDecimal currentPrice);

    // TODO resiti se ovoga pod hitno
    ResponseEntity<Page<ListingDto>> getListings(String securityType, Pageable pageable);

    List<PriceChangeDto> getPriceChanges();

    ListingDetailsDto getListingDetails(UUID listingId);

    List<OptionDto> getOptionsWithSettlementDateForStock(
        UUID listingId,
        OffsetDateTime settlementDate
    );
}
