package hu.oe.nik.szfmv.automatedcar.model;

public class TurningPoint {
    private Position position;
    private int movingDirection;
    private int movingSpeed;
    private int turningDirection;
    private int turningSpeed;

    public TurningPoint(Position position, int movingDirection, int movingSpeed, int turningDirection, int turningSpeed) {
        this.position = position;
        this.movingDirection = movingDirection;
        this.movingSpeed = movingSpeed;
        this.turningDirection = turningDirection;
        this.turningSpeed = turningSpeed;
    }

    public Position getPosition() {
        return position;
    }

    public int getMovingDirection() {
        return movingDirection;
    }

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public int getTurningDirection() {
        return turningDirection;
    }

    public int getTurningSpeed() {
        return turningSpeed;
    }
}
