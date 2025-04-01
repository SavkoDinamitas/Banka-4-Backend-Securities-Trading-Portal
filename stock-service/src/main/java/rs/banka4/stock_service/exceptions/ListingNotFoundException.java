package rs.banka4.stock_service.exceptions;

import java.util.Map;
import org.springframework.http.HttpStatus;

public class ListingNotFoundException extends BaseApiException {
    public ListingNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND, Map.of("id", id));
    }
}
