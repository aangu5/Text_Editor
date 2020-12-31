class Counter {

    int count = 0;

    synchronized public void inc() {
        count++;
    }
}