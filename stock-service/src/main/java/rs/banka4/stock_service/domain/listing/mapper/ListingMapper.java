package rs.banka4.stock_service.domain.listing.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import rs.banka4.stock_service.domain.listing.db.Listing;
import rs.banka4.stock_service.domain.listing.dtos.ListingDto;
import rs.banka4.stock_service.domain.listing.dtos.specific.ForexPairDto;
import rs.banka4.stock_service.domain.listing.dtos.specific.FutureDto;
import rs.banka4.stock_service.domain.listing.dtos.specific.StockDto;
import rs.banka4.stock_service.domain.security.forex.db.ForexPair;
import rs.banka4.stock_service.domain.security.future.db.Future;
import rs.banka4.stock_service.domain.security.stock.db.Stock;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListingMapper {

    ListingMapper INSTANCE = Mappers.getMapper(ListingMapper.class);

    Listing toEntity(ListingDto dto);

    ListingDto toDto(Listing listing);

    StockDto toStockDto(Stock stock);

    ForexPairDto toForexPairDto(ForexPair forexPair);

    FutureDto toFutureDto(Future future);

}
