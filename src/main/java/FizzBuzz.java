import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzz {
    private int n;
    private BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();
    private int currentNumber = 1;

    public FizzBuzz(int n) {
        this.n = n;
    }

    public void printQueue() {
        while (true) {
            try {
                String output = outputQueue.take();
                System.out.print(output);
                if (!output.equals("fizzbuzz") && currentNumber - 1 != n) {
                    System.out.print(", ");
                }
                if (currentNumber - 1 == n && output.equals("fizzbuzz")) {
                    System.out.println();
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int n = 20;
        FizzBuzz fizzBuzz = new FizzBuzz (n);

        Thread threadA = new Thread(() -> {
            while (true) {
                synchronized (fizzBuzz) {
                    if (fizzBuzz.currentNumber > fizzBuzz.n) return;
                    if (fizzBuzz.currentNumber % 3 == 0 && fizzBuzz.currentNumber % 5 != 0) {
                        try {
                            fizzBuzz.outputQueue.put("fizz");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fizzBuzz.currentNumber++;
                    }
                }
            }
        });

        Thread threadB = new Thread(() -> {
            while (true) {
                synchronized (fizzBuzz) {
                    if (fizzBuzz.currentNumber > fizzBuzz.n) return;
                    if (fizzBuzz.currentNumber % 5 == 0 && fizzBuzz.currentNumber % 3 != 0) {
                        try {
                            fizzBuzz.outputQueue.put("buzz");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fizzBuzz.currentNumber++;
                    }
                }
            }
        });

        Thread threadC = new Thread(() -> {
            while (true) {
                synchronized (fizzBuzz) {
                    if (fizzBuzz.currentNumber > fizzBuzz.n) return;
                    if (fizzBuzz.currentNumber % 3 == 0 && fizzBuzz.currentNumber % 5 == 0) {
                        try {
                            fizzBuzz.outputQueue.put("fizzbuzz");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fizzBuzz.currentNumber++;
                    }
                }
            }
        });

        Thread threadD = new Thread(() -> {
            while (true) {
                synchronized (fizzBuzz) {
                    if (fizzBuzz.currentNumber > fizzBuzz.n) return;
                    if (fizzBuzz.currentNumber % 3 != 0 && fizzBuzz.currentNumber % 5 != 0) {
                        try {
                            fizzBuzz.outputQueue.put(String.valueOf(fizzBuzz.currentNumber));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fizzBuzz.currentNumber++;
                    }
                }
            }
        });

        Thread printThread = new Thread(fizzBuzz::printQueue);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        printThread.start();
    }
}
