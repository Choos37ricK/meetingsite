public class Main {

    private static final int usersCount = 20;

    private static final int SLEEP = 1000;

    public static void main(String[] args) throws InterruptedException {
        RedisStorage redisStorage = new RedisStorage();
        redisStorage.init();
        for (int i = 1; i < usersCount + 1; i++) {
            redisStorage.addUser(String.valueOf(i));
        }

        redisStorage.listUsers();

        System.out.println();
        
        for(;;) {
            for (int i = 0; i < usersCount; i++) {
                String user = redisStorage.getUser(i);
                System.out.println("На главной странице показываем пользователя " + user);
                redisStorage.deleteUser(i);
                redisStorage.addUser(user);

                Thread.sleep(SLEEP);
            }
        }
    }
}
