package rs.banka4.stock_service.service.impl;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import rs.banka4.stock_service.domain.listing.db.ListingDailyPriceInfo;
import rs.banka4.stock_service.repositories.ListingDailyPriceInfoRepository;
import rs.banka4.stock_service.repositories.OrderRepository;
import rs.banka4.stock_service.service.abstraction.ListingService;

@Service
public class ListingServiceImpl implements ListingService {
    private OrderRepository orderRepository;
    private ListingDailyPriceInfoRepository listingDailyPriceInfoRepository;

    @Override
    public int getVolumeOfAsset(UUID securityId) {
        var now = OffsetDateTime.now();
        var start = now.truncatedTo(ChronoUnit.DAYS);
        var end = start.plusDays(1);
        return orderRepository.findCountOfMadeOrdersToday(securityId, start, end);
    }

    /**
     * returns null if there is no yesterday listing value returns change by calculating from last
     * price from yesterday
     */
    @Override
    public BigDecimal calculateChange(UUID securityId, BigDecimal currentPrice) {
        Optional<ListingDailyPriceInfo> yesterdayListing =
            listingDailyPriceInfoRepository.getYesterdayListingDailyPriceInfo(
                securityId,
                Limit.of(1)
            );
        return yesterdayListing.map(
            listingDailyPriceInfo -> currentPrice.subtract(listingDailyPriceInfo.getLastPrice())
        )
            .orElse(null);
    }

}
