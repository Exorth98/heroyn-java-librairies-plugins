package net.heroyn.heroynserverapi.updater;

import net.heroyn.heroynserverapi.utils.UtilMath;

public enum UpdateType
{
    MIN_64("MIN_64", 0, 3840000L), 
    MIN_32("MIN_32", 1, 1920000L), 
    MIN_16("MIN_16", 2, 960000L), 
    MIN_08("MIN_08", 3, 480000L), 
    MIN_04("MIN_04", 4, 240000L), 
    MIN_02("MIN_02", 5, 120000L), 
    MIN_01("MIN_01", 6, 60000L), 
    SLOWEST("SLOWEST", 7, 32000L), 
    SLOWER("SLOWER", 8, 16000L), 
    SLOW("SLOW", 9, 4000L), 
    SEC_08("SEC_08", 10, 8000L), 
    SEC_03("SEC_03", 11, 3000L), 
    SEC("SEC", 12, 1000L), 
    FAST("FAST", 13, 500L), 
    FASTER("FASTER", 14, 250L), 
    FASTEST("FASTEST", 15, 125L), 
    TICK("TICK", 16, 49L),
    MICROTICK("MICROTICK", 8, 24L);
    
    private long _time;
    private long _last;
    private long _timeSpent;
    private long _timeCount;
    
    private UpdateType(final String s, final int n, final long time) {
        this._time = time;
        this._last = System.currentTimeMillis();
    }
    
    public boolean Elapsed() {
        if (UtilMath.elapsed(this._last, this._time)) {
            this._last = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    
    public void StartTime() {
        this._timeCount = System.currentTimeMillis();
    }
    
    public void StopTime() {
        this._timeSpent += System.currentTimeMillis() - this._timeCount;
    }
    
    public void PrintAndResetTime() {
        System.out.println(String.valueOf(this.name()) + " in a second: " + this._timeSpent);
        this._timeSpent = 0L;
    }
}
