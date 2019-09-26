package hu.oe.nik.szfmv.automatedcar.model;

public class Position {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Position() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * Gets the X position of the object
     * @return x position of the object
     */
    public int getX(){
        return  this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    /**
     * Gets the Y position of the object
     * @return y position of the object
     */
    public int getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    /**
     * Gets the Z position of the object
     * @return z position of the object
     */
    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
