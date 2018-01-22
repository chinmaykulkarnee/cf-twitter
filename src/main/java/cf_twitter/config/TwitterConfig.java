package cf_twitter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class TwitterConfig {
    private final String apiKey;
    private final String apiSecret;
    private final String accessKey;
    private final String accessSecret;

    public TwitterConfig(@Value("${twitter.client_api_key}") String apiKey,
                         @Value("${twitter.client_api_secret}") String apiSecret,
                         @Value("${twitter.access_key}") String accessKey,
                         @Value("${twitter.access_secret}") String accessSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }
}
