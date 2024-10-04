public class TimeTracker {
    public static void main(String[] args) {
        Runnable timeTask = () -> {
            int secondsPassed = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                    secondsPassed++;
                    System.out.println("Минуло " + secondsPassed + "sec");
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    };
        Runnable fiveSecondTask = () -> {
        while (true) {
        try {
            Thread.sleep(5000);
            System.out.println("Минуло 5 секунд");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        };

        Thread timeThread = new Thread(timeTask);
        Thread fiveSecondThread = new Thread(fiveSecondTask);

        timeThread.start();
        fiveSecondThread.start();
    }
}
