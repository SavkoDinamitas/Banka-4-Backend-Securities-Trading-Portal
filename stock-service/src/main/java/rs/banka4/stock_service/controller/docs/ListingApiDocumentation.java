package rs.banka4.stock_service.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import rs.banka4.stock_service.domain.listing.dtos.ListingDetailsDto;
import rs.banka4.stock_service.domain.listing.dtos.ListingDto;
import rs.banka4.stock_service.domain.listing.dtos.OptionDto;
import rs.banka4.stock_service.domain.listing.dtos.PriceChangeDto;

public interface ListingApiDocumentation {
    @Operation(
        summary = "Search Listings",
        description = "Retrieves listings based on the security type filter and pagination. User can see listings"
            + "that are allowed for his role.",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved listings",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListingDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid request parameters"
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden"
            )
        }
    )
    ResponseEntity<Page<ListingDto>> getListings(
        @Parameter(description = "Type of security to filter by") String securityType,
        @Parameter(description = "Page number") int page,
        @Parameter(description = "Number of listings per page") int size
    );

    @Operation(
        summary = "Get data for price change graph",
        description = "Retrieves daily prices to show the price change over period of time",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved data",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PriceChangeDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden"
            )
        }
    )
    ResponseEntity<List<PriceChangeDto>> getPriceChanges();

    @Operation(
        summary = "Get details for one listing",
        description = "Retrieves all data specific to a listing",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved data",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ListingDetailsDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Listing id not found"
            )
        }
    )
    ResponseEntity<ListingDetailsDto> getListingDetails(
        @Parameter(description = "Listing id as path param to get it's details") String id
    );

    @Operation(
        summary = "Get all options for one stock listing",
        description = "Retrieves all options in specific format required in spec to show in table",
        security = @SecurityRequirement(name = "bearerAuth"),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved data",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OptionDto.class)
                )
            ),
            @ApiResponse(
                responseCode = "403",
                description = "Forbidden"
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Listing id not found, or it is not a Stock listing"
            )
        }
    )
    ResponseEntity<List<OptionDto>> getListingOptions(
        @Parameter(
            description = "Path parameter for stock listing that we want to show all options for"
        ) String listingId,
        @Parameter(
            description = "Request parameter for dateTime with timezone"
                + " to filter options that are expiring on that date"
        ) OffsetDateTime settlementDate
    );
}
