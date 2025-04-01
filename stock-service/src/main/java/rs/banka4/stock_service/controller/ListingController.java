package rs.banka4.stock_service.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.banka4.stock_service.controller.docs.ListingApiDocumentation;
import rs.banka4.stock_service.domain.listing.dtos.ListingFilterDto;
import rs.banka4.stock_service.domain.listing.dtos.ListingInfoDto;
import rs.banka4.stock_service.service.abstraction.ListingService;

@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
public class ListingController implements ListingApiDocumentation {

    // Note: The @Primary annotation in the mock service should be deleted.
    private final ListingService listingService;

    @Override
    @GetMapping
    public ResponseEntity<Page<ListingInfoDto>> getListings(
        @ModelAttribute ListingFilterDto filter,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.of(
            Optional.ofNullable(
                listingService.getListings(filter, PageRequest.of(page, size), false)
            )
        );
    }
}
