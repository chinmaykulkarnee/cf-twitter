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

    private final Twitter twitter;

    public TwitterGateway(TwitterConfig config) {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(config.getApiKey(), config.getApiSecret());
        AccessToken accessToken = new AccessToken(config.getAccessKey(), config.getAccessSecret());
        twitter.setOAuthAccessToken(accessToken);
    }

    public List<User> getFollowers(String screenName) throws TwitterException {
        LinkedList<User> followers = new LinkedList<>();
        PagableResponseList<User> followersResponse = twitter.getFollowersList(screenName, -1);
        followers.addAll(followersResponse);
        while (followersResponse.hasNext()) {
            long nextCursor = followersResponse.getNextCursor();
            followersResponse = twitter.getFollowersList("followersResponse", nextCursor, 20);
            followers.addAll(followersResponse);
        }
        return followers;
    }

    public List<User> getFollowersByLookup(String screenName) throws TwitterException {
        LinkedList<User> followers = new LinkedList<>();
        long followerCursor = -1;
        IDs followerIds;
        do
        {
            followerIds = twitter.getFollowersIDs(screenName, followerCursor);
            for(int i = 0; i < followerIds.getIDs().length; i += 100) {
                long[] list = javaslang.collection.List.ofAll(followerIds.getIDs())
                        .slice(i, i + 100).toJavaList().stream().mapToLong(l -> l).toArray();

                ResponseList<User> followerUsers = twitter.lookupUsers(list);
                followers.addAll(followerUsers);
            }

        }while((followerCursor = followerIds.getNextCursor()) != 0);
        return followers;
    }
}
