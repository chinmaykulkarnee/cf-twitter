package cf_twitter.services;

import cf_twitter.models.BestMetrics;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.collection.Traversable;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class BestMetricsCalculator {

    public BestMetrics get(List<User> followers) {
        List<Long> lastPostTimes = getFollowerLastPostTime(followers);
        return new BestMetrics(getBestHour(lastPostTimes), getBestDay(lastPostTimes));
    }

    private List<Long> getFollowerLastPostTime(List<User> followers) {
        return List.ofAll(followers)
                .filter(u -> u.getStatus() != null)
                .map(User::getStatus)
                .map(Status::getCreatedAt)
                .map(Date::getTime);
    }

    private int getBestHour(List<Long> timeStamps) {
        Calendar calendar = Calendar.getInstance();

        List<Integer> hours = timeStamps.map(timeStamp -> {
            calendar.setTimeInMillis(timeStamp);
            return calendar.get(Calendar.HOUR_OF_DAY);
        });

        return getBestByFrequency(hours);
    }

    private int getBestDay(List<Long> timeStamps) {
        Calendar calendar = Calendar.getInstance();
        List<Integer> days = timeStamps.map(timeStamp -> {
            calendar.setTimeInMillis(timeStamp);
            return calendar.get(Calendar.DAY_OF_WEEK);
        });

        return getBestByFrequency(days);
    }

    private int getBestByFrequency(List<Integer> list) {
        Map<Integer, Integer> mappedHours = list.groupBy(e -> e).mapValues(Traversable::size);
        ArrayList<java.util.Map.Entry<Integer, Integer>> entries = new ArrayList<>(mappedHours.toJavaMap().entrySet());

        int max = 0;
        int bestValue = 0;

        for (java.util.Map.Entry<Integer, Integer> e : entries) {
            if (e.getValue() > max) {
                max = e.getValue();
                bestValue = e.getKey();
            }
        }

        return bestValue;
    }
}
