package rs.banka4.stock_service.domain.listing.dtos;


import lombok.Getter;
import rs.banka4.stock_service.domain.options.db.Asset;
import rs.banka4.stock_service.domain.options.db.Option;
import rs.banka4.stock_service.domain.security.forex.db.ForexPair;
import rs.banka4.stock_service.domain.security.future.db.Future;
import rs.banka4.stock_service.domain.security.stock.db.Stock;

@Getter
public enum SecurityType {
    FUTURE(Future.class),
    STOCK(Stock.class),
    FOREX_PAIR(ForexPair.class),
    OPTION(Option.class);

    private final Class<? extends Asset> typeClass;

    SecurityType(Class<? extends Asset> typeClass) {
        this.typeClass = typeClass;
    }

}
