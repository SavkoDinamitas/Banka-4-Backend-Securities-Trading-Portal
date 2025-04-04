package rs.banka4.stock_service.domain.options.db;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.*;
import rs.banka4.stock_service.domain.actuaries.db.MonetaryAmount;
import rs.banka4.stock_service.domain.security.stock.db.Stock;

@Entity(name = "options")
@Builder
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Option extends Asset {

    @ManyToOne(optional = false)
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OptionType optionType;

    @Column(nullable = false)
    private MonetaryAmount strikePrice;

    @Column(nullable = false)
    private double impliedVolatility;

    @Column(nullable = false)
    private int openInterest;

    @Column(nullable = false)
    private OffsetDateTime settlementDate;
}
