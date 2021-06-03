import java.util.Random;

class Randomized {
    private static String[] contents = {
        "FOO", "BAR", "BAZ", "SCOOBY", "DOO"
    };

    private Random rand = new Random();

    private int getRandomIndex() {
        return rand.nextInt(contents.length);
    }

    public String getRandomItem() {
        return contents[getRandomIndex()];
    }
}

public final class Randomer {
    public static void main(String[] args) {
        Randomized r = new Randomized();
        for (int i = 0; i < 10; i++) {
            System.out.println(r.getRandomItem());
        }
    }
}