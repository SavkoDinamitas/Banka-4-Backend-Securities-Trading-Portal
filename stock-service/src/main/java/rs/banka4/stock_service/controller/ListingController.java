package rs.banka4.stock_service.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.banka4.stock_service.controller.docs.ListingApiDocumentation;
import rs.banka4.stock_service.domain.listing.dtos.ListingDetailsDto;
import rs.banka4.stock_service.domain.listing.dtos.ListingDto;
import rs.banka4.stock_service.domain.listing.dtos.OptionDto;
import rs.banka4.stock_service.domain.listing.dtos.PriceChangeDto;
import rs.banka4.stock_service.service.abstraction.ListingService;

@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
public class ListingController implements ListingApiDocumentation {

    // Note: The @Primary annotation in the mock service should be deleted.
    private final ListingService listingService;

    @Override
    @GetMapping
    public ResponseEntity<Page<ListingDto>> getListings(
        @RequestParam(required = false) String securityType,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return listingService.getListings(securityType, PageRequest.of(page, size));
    }

    @GetMapping("/priceChange")
    public ResponseEntity<List<PriceChangeDto>> getPriceChanges() {
        return new ResponseEntity<>(listingService.getPriceChanges(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingDetailsDto> getListingDetails(@PathVariable String id) {
        return new ResponseEntity<>(
            listingService.getListingDetails(UUID.fromString(id)),
            HttpStatus.OK
        );
    }

    @GetMapping("/options/{listingId}")
    public ResponseEntity<List<OptionDto>> getListingOptions(
        @PathVariable String listingId,
        @RequestParam OffsetDateTime settlementDate
    ) {
        return new ResponseEntity<>(
            listingService.getOptionsWithSettlementDateForStock(
                UUID.fromString(listingId),
                settlementDate
            ),
            HttpStatus.OK
        );
    }
}
