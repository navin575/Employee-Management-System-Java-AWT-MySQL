class Test extends Thread {
    public void run() {
        for (int i = 1; i < 5; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + " - value: " + i);
                Thread.sleep(500);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {
        Test t1 = new Test();
        Test t2 = new Test();
        t1.start();
        t2.start();
    }
}
