package vn.lowzii.metech;

/** Monotonic time; no burst of queued attacks after a stall. */
public final class Interval {
    private final long period;
    private long previous;
    public Interval(long period) { this.period = period; }
    public void reset(long now) { previous = now; }
    public boolean due(long now) {
        if (now - previous < period) return false;
        previous = now;
        return true;
    }
}
