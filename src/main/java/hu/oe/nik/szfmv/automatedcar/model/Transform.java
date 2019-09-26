package hu.oe.nik.szfmv.automatedcar.model;

public class Transform {
    private double m11;
    private double m12;
    private double m21;
    private double m22;
    private double rotation;

    /**
     *
     * @param m11 Distance from top left corner
     * @param m12 Distance from top right corner
     * @param m21 Distance from bottom left corner
     * @param m22 Distance from bottom right corner
     */
    public Transform(double m11, double m12, double m21, double m22){
        this.m11 = m11;
        this.m12 = m12;
        this.m21 = m21;
        this.m22 = m22;
        this.rotation = this.calculateRotation();
    }

    public Transform(){
        this.m11 = 0;
        this.m12 = 0;
        this.m21 = 0;
        this.m22 = 0;
        this.rotation = this.calculateRotation();
    }

    public double getRotation(){
        return this.rotation;
    }

    public void setRotation(double rotation){
        this.rotation = rotation;
    }

    /**
     * Calculates and gets the rotation value from the distance of the corners
     * @return calculated rotation
     */
    private double calculateRotation(){
        // TODO calculate rotation
        return 0;
    }
}
