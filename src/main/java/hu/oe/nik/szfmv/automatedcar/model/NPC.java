package hu.oe.nik.szfmv.automatedcar.model;

import hu.oe.nik.szfmv.automatedcar.xml.XmlParser;

import java.util.List;

public class NPC extends WorldObject {
    private int index = 0;
    private List<TurningPoint> turningPoints;

    public NPC(Position startPosition, int startRotation, String imageFileName, String roadFileName) {
        super(startPosition.getX(), startPosition.getY(), imageFileName);
        this.turningPoints = XmlParser.parseTurningPoints(roadFileName).turningPoints;
        this.setRotation(startRotation);
        this.position.setZ(2);
        this.referencePosition = new Position(this.width / 2, this.height / 2);
    }

    public void move() {
        var currentTurningPoint = turningPoints.get(index);
        if (position.getX() == currentTurningPoint.getPosition().getX() && position.getY() == currentTurningPoint.getPosition().getY()) {
            increaseIndex();
        } else {
            if (getRotation() == currentTurningPoint.getMovingDirection()) {
                moveInTurningPointDirection(currentTurningPoint);
            } else {
                turnInTurningPointDirection(currentTurningPoint);
            }
        }
    }

    private void moveInTurningPointDirection(TurningPoint currentTurningPoint) {
        setPosX(getPosX() + getTurningPointDirection(getPosX(), currentTurningPoint.getPosition().getX()) * currentTurningPoint.getMovingSpeed());
        setPosY(getPosY() + getTurningPointDirection(getPosY(), currentTurningPoint.getPosition().getY()) * currentTurningPoint.getMovingSpeed());
    }

    private void turnInTurningPointDirection(TurningPoint currentTurningPoint) {
        var turningSize = getRotation() + currentTurningPoint.getTurningSpeed() * currentTurningPoint.getTurningDirection();
        if (turningSize < 0) {
            turningSize = 360 - Math.abs(turningSize);
        } else if (turningSize >= 360) {
            turningSize -= 360;
        }
        setRotation(turningSize);
    }

    private void increaseIndex() {
        if (index == turningPoints.size() - 1) {
            index = 0;
        } else {
            index++;
        }
    }

    private int getTurningPointDirection(int currentCoordinate, int nextCoordinate) {
        return Integer.compare(nextCoordinate, currentCoordinate);
    }
}