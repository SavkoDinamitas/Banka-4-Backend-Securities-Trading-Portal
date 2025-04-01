package rs.banka4.stock_service.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rs.banka4.stock_service.domain.listing.db.Listing;

@Repository
public interface ListingRepository extends
    JpaRepository<Listing, UUID>,
    JpaSpecificationExecutor<Listing> {
}
