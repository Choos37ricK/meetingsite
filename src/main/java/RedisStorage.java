import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

public class RedisStorage {

    private RedissonClient redissonClient;

    private RKeys rKeys;

    private RList<String> usersList;

    private static final String KEY = "USERS";

    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redissonClient = Redisson.create(config);
        } catch (RedisConnectionException ex) {
            System.out.println("Can't to connect to Redis!");
            System.out.println(ex.getMessage());
        }

        rKeys = redissonClient.getKeys();
        usersList = redissonClient.getList(KEY);
        rKeys.delete(KEY);
    }

    public void shutdown() {
        redissonClient.shutdown();
    }

    public void listUsers() {
        for(String user : usersList) {
            System.out.println("Пользователь " + user);
        }
    }

    public String getUser(Integer userIndex) {
        return usersList.get(userIndex);
    }

    public void addUser(String user) {
        usersList.add(user);
    }

    public void deleteUser(Integer userIndex) {
        usersList.remove(userIndex);
    }

}
