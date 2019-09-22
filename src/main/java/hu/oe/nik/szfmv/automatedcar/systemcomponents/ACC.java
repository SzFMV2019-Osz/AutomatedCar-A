package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class ACC {
    private int referenceSpeed = 0;

    private boolean isOn = false;
    private static final int minValue = 30;
    private static final int maxValue = 160;
    private static final int step = 10;

    private double[] timeGap = {0.8, 1.0, 1.2, 1.4};
    private int index = 0;

    public void Set(int setSpeed){
        referenceSpeed = setSpeed;
        isOn = true;
    }

    public void Resume(){
        isOn = true;
    }

    public void Plus(){
        if(referenceSpeed + step <= maxValue)
            referenceSpeed += step;
    }

    public void Minus(){
        if( referenceSpeed - step >= minValue)
            referenceSpeed -= step;
    }

    public void TimeGapSetter(){
        if(index <= timeGap.length - 1){
            ++index;
        }else{
            index = 0;
        }

    }

    public double ReturnTimeGap(){
        return timeGap[index];
    }



}
