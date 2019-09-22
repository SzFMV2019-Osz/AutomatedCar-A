package hu.oe.nik.szfmv.automatedcar.systemcomponents;

public class GearShift {
    private char [] values = {'P', 'R', 'N', 'D'};
    private int currValue = 0;

    public void Increment(){
        if(currValue <= values.length - 1)
            ++currValue;
    }

    public void Decrement(){
        if(currValue >= 0)
            --currValue;
    }

    public char GetCurrentState(){
        return values[currValue];
    }
}
