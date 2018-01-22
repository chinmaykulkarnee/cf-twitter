package cf_twitter.controllers;

import cf_twitter.models.BestMetrics;
import cf_twitter.services.TwitterService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

@RestController
public class TwitterController {

    private final TwitterService service;

    public TwitterController(TwitterService service) {
        this.service = service;
    }

    @RequestMapping(value = "/bestmetrics/{userId}", method = RequestMethod.GET)
    public BestMetrics get(@PathVariable String userId) {
        try {
            return service.getBestPostingMetrics(userId);
        } catch (TwitterException e) {
            e.printStackTrace();
            return null; //handle errors here (send specific exception from service)
        }
    }

//    public static void main(String[] args) throws TwitterException {
//        Twitter twitter = new TwitterFactory().getInstance();
//        twitter.setOAuthConsumer("yfCdhoNd6vjuQd4yBcDKm91si", "Rz2h0DIoprXLsCN8gN3GFBbxAeT2RHyU2CqxaCqLpHxhSLWhIp");
//        AccessToken accessToken = new AccessToken("211206515-OyvsZjehvAHRmNrfgunQmUfanmLWDfK5FOXh32fO", "K3R9ZqkfkpBQmeBoYWqW5Gd1ikiVMjLd4mzutObloLWlh");
//        twitter.setOAuthAccessToken(accessToken);
//
//        LinkedList<User> followers = new LinkedList<>();
//        PagableResponseList<User> followersResponse = twitter.getFollowersList("missingfaktor", -1);
//        followers.addAll(followersResponse);
//        while (followersResponse.hasNext()) {
//            followersResponse = twitter.getFollowersList("followersResponse", followersResponse.getNextCursor());
//            followers.addAll(followersResponse);
//        }
//
//        BestMetricsCalculator bestMetricsCalculator = new BestMetricsCalculator();
//        BestMetrics bestMetrics = bestMetricsCalculator.get(List.ofAll(followers));
//        System.out.println(bestMetrics);
//
//    }
}
