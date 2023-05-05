public class Game {
    public boolean flipCoin(int n) {
        return (Math.random() < odds(n));
    }


    public double odds(int n) {
        /*
         0: 0.61
        10: 0.58
        20: 0.55
        30: 0.51
        40: 0.48
        50: 0.45
        60: 0.43
        70: 0.42
        80: 0.42
        90: 0.41
         */
        double x = -Math.atan(((double)n/25)-1)/10;
        return x+0.53;
    }
}