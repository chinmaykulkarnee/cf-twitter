package cf_twitter.gateway;

import cf_twitter.config.TwitterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.util.LinkedList;
import java.util.List;

@Service
public class TwitterGateway {
    private static final Logger logger = LoggerFactory.getLogger(TwitterGateway.class);

    private final TwitterConfig config;

    public TwitterGateway(TwitterConfig config) {
        this.config = config;
    }

    public List<User> get() throws TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(config.getApiKey(), config.getApiSecret());

        AccessToken accessToken = new AccessToken(config.getAccessKey(), config.getAccessSecret());
        twitter.setOAuthAccessToken(accessToken);
        LinkedList<User> followers = new LinkedList<>();
        PagableResponseList<User> followersResponse = twitter.getFollowersList("followersResponse", -1);
        followers.addAll(followersResponse);
        while (followersResponse.hasNext()) {
            followersResponse = twitter.getFollowersList("followersResponse", followersResponse.getNextCursor());
            followers.addAll(followersResponse);
        }
        return followers;
    }

}
