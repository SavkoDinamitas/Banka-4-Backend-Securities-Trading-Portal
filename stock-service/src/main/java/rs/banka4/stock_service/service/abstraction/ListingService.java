package rs.banka4.stock_service.service.abstraction;

import java.math.BigDecimal;
import java.util.UUID;

public interface ListingService {
    int getVolumeOfAsset(UUID securityId);

    BigDecimal calculateChange(UUID securityId, BigDecimal currentPrice);
}
