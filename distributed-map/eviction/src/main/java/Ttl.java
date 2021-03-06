import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Ttl {

    public static void main(String[] args)
            throws InterruptedException {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Integer, String> map = hazelcastInstance.getMap("default");

        ((IMap<Integer, String>) map).put(1, "Number One", 5, SECONDS);
        System.out.println("Entry expiration: " + ((IMap<Integer, String>) map).getEntryView(1).getExpirationTime());

        // Entry expires
        Thread.sleep(SECONDS.toMillis(5));
        if (map.get(1) != null) {
            System.err.println("Entry is still alive!");
            System.exit(-1);
            return;
        }

        System.out.println("Entry expired!");
        System.exit(0);
    }

}
