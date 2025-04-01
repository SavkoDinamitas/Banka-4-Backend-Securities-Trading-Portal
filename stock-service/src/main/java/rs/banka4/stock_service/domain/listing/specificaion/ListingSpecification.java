package rs.banka4.stock_service.domain.listing.specificaion;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import rs.banka4.stock_service.domain.listing.db.Listing;
import rs.banka4.stock_service.domain.listing.dtos.ListingFilterDto;
import rs.banka4.stock_service.domain.listing.dtos.SecurityType;
import rs.banka4.stock_service.domain.orders.db.Order;
import rs.banka4.stock_service.domain.security.future.db.Future;
import rs.banka4.stock_service.domain.security.stock.db.Stock;

public class ListingSpecification {
    public static Specification<Listing> getSpecification(
        ListingFilterDto filter,
        boolean isClient
    ) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            var secJoin = root.join("security");
            var exchangesJoin = root.join("exchanges");
            if (isClient) {
                if (
                    filter.getSecurityType() != null
                        && List.of(SecurityType.FUTURE, SecurityType.STOCK)
                            .contains(filter.getSecurityType())
                ) {
                    predicates.add(
                        cb.equal(
                            secJoin.type(),
                            filter.getSecurityType()
                                .getTypeClass()
                        )
                    );
                } else {
                    var p = cb.equal(secJoin.type(), cb.literal(Future.class));
                    var p2 = cb.equal(secJoin.type(), cb.literal(Stock.class));
                    predicates.add(cb.or(p, p2));
                }

            } else {
                if (filter.getSecurityType() != null) {
                    predicates.add(
                        cb.equal(
                            secJoin.type(),
                            filter.getSecurityType()
                                .getTypeClass()
                        )
                    );
                }
            }
            if (
                filter.getSearchName() != null
                    && !filter.getSearchName()
                        .isBlank()
            ) {
                predicates.add(
                    cb.like(
                        cb.lower(secJoin.get("name")),
                        "%"
                            + filter.getSearchName()
                                .toLowerCase()
                            + "%"
                    )
                );
            }
            if (
                filter.getSearchTicker() != null
                    && !filter.getSearchTicker()
                        .isBlank()
            ) {
                predicates.add(
                    cb.like(
                        cb.lower(secJoin.get("ticker")),
                        "%"
                            + filter.getSearchTicker()
                                .toLowerCase()
                            + "%"
                    )
                );
            }

            if (
                filter.getExchangePrefix() != null
                    && !filter.getExchangePrefix()
                        .isBlank()
            ) {
                predicates.add(
                    cb.like(
                        cb.lower(exchangesJoin.get("exchangeName")),
                        filter.getExchangePrefix()
                            .toLowerCase()
                            + "%"
                    )
                );
            }
            if (filter.getAskMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("ask"), filter.getAskMin()));
            }
            if (filter.getAskMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("ask"), filter.getAskMax()));
            }
            if (filter.getBidMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("bid"), filter.getBidMin()));
            }
            if (filter.getBidMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("bid"), filter.getBidMax()));
            }
            if (
                List.of(SecurityType.FUTURE, SecurityType.OPTION)
                    .contains(filter.getSecurityType())
            ) {
                if (
                    filter.getSettlementDateFrom() != null || filter.getSettlementDateTo() != null
                ) {
                    if (
                        filter.getSettlementDateFrom() != null
                            && filter.getSettlementDateTo() != null
                    ) {
                        predicates.add(
                            cb.between(
                                secJoin.get("settlementDate"),
                                filter.getSettlementDateFrom(),
                                filter.getSettlementDateTo()
                            )
                        );
                    } else if (filter.getSettlementDateFrom() != null) {
                        predicates.add(
                            cb.greaterThanOrEqualTo(
                                secJoin.get("settlementDate"),
                                filter.getSettlementDateFrom()
                            )
                        );
                    } else {
                        predicates.add(
                            cb.lessThanOrEqualTo(
                                secJoin.get("settlementDate"),
                                filter.getSettlementDateTo()
                            )
                        );
                    }
                }
            }
            OffsetDateTime now = OffsetDateTime.now();
            OffsetDateTime start = now.truncatedTo(ChronoUnit.DAYS);
            OffsetDateTime end = start.plusDays(1);

            Subquery<Long> volumeSubquery = query.subquery(Long.class);
            Root<Order> orderRoot = volumeSubquery.from(Order.class);
            volumeSubquery.select(cb.count(orderRoot));
            volumeSubquery.where(
                cb.equal(
                    orderRoot.get("asset")
                        .get("id"),
                    root.get("security")
                        .get("id")
                ),
                cb.between(orderRoot.get("createdAt"), start, end)
            );

            if (filter.getVolumeMin() != null || filter.getVolumeMax() != null) {

                if (filter.getVolumeMin() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(volumeSubquery, filter.getVolumeMin()));
                }
                if (filter.getVolumeMax() != null) {
                    predicates.add(cb.lessThanOrEqualTo(volumeSubquery, filter.getVolumeMax()));
                }
            }
            if (filter.getSortBy() != null && filter.getSortDirection() != null) {
                switch (filter.getSortBy()) {
                    case PRICE:
                        if (filter.getSortDirection().equals(ListingFilterDto.SortDirection.ASC)) {
                            query.orderBy(cb.asc(root.get("ask")));
                        } else {
                            query.orderBy(cb.desc(root.get("ask")));
                        }
                        break;
                    case VOLUME:
                        if (filter.getSortDirection().equals(ListingFilterDto.SortDirection.ASC)) {
                            query.orderBy(cb.asc(volumeSubquery));
                        } else {
                            query.orderBy(cb.desc(volumeSubquery));
                        }
                        break;
                    default:
                        break;
                }
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

}
