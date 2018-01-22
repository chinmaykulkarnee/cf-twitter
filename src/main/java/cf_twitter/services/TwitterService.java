package cf_twitter.services;

import cf_twitter.gateway.TwitterGateway;
import cf_twitter.models.BestMetrics;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;
import twitter4j.User;

import java.util.List;

@Service
public class TwitterService {

    private final TwitterGateway gateway;
    private final BestMetricsCalculator calculator;

    public TwitterService(TwitterGateway gateway, BestMetricsCalculator calculator) {
        this.gateway = gateway;
        this.calculator = calculator;
    }

    public BestMetrics getBestPostingMetrics(String screenName) throws TwitterException {
        List<User> followers = gateway.getFollowersByLookup(screenName);
        return calculator.get(javaslang.collection.List.ofAll(followers));
    }
}
