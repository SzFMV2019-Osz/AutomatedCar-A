package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class Control {
    private static final int rangeMax = 100;
    private static final int rangeMin = 0;
    public  int step = 0;
    public int currentValue = 0;

    public Control(int step) {
        this.step = step;
    }

    public void Increment(){
        if(currentValue + step <= rangeMax)
            currentValue += step;
    }

    public void Decrement(){
        if(currentValue - step >= rangeMin)
            currentValue -= step;
    }

}
