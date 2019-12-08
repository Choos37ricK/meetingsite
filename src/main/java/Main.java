import java.util.Random;

public class Main {

    private static final int usersCount = 20;

    private static final int SLEEP = 200;

    public static void main(String[] args) throws InterruptedException {
        RedisStorage redisStorage = new RedisStorage();
        redisStorage.init();
        for (int i = 1; i < usersCount + 1; i++) {
            redisStorage.addUser(String.valueOf(i));
        }

        System.out.println();

        for(;;) {
            for (int i = 0; i < usersCount; i++) {
                String user = redisStorage.getUser(i);
                System.out.println(" - На главной странице показываем пользователя " + user);
                redisStorage.deleteUser(user);
                redisStorage.addUser(user);
                i--;

                int donate = new Random().nextInt(11) + 1;
                if (donate == 1) {
                    String userNumber = String.valueOf(new Random().nextInt(21) + 1);
                    if (!userNumber.equals(user)) {
                        System.out.println(" > Пользователь " + userNumber + " оплатил платную услугу");
                        System.out.println(" - На главной странице показываем пользователя " + userNumber);
                        redisStorage.deleteUser(userNumber);
                        redisStorage.addUser(userNumber);
                    }
                }

                Thread.sleep(SLEEP);
            }
        }

    }
}
