package hu.oe.nik.szfmv.automatedcar.model;

import java.awt.*;

public class Triangle {
    /*
    int aX, aY, bX, bY, cX, cY;

    public int getaX() {
        return aX;
    }

    public void setaX(int aX) {
        this.aX = aX;
    }

    public int getaY() {
        return aY;
    }

    public void setaY(int aY) {
        this.aY = aY;
    }

    public int getbX() {
        return bX;
    }

    public int getbY() {
        return bY;
    }

    public int getcX() {
        return cX;
    }


    public int getcY() {
        return cY;
    }



    public int getOuterDegree() {
        return outerDegree;
    }

    public void setOuterDegree(int outerDegree) {
        this.outerDegree = outerDegree;
    }

    static int degree = 100;
    static int height = 30;
    int outerDegree = 0;

    public Triangle(int aX, int aY, int outerDegree){
        this.aX = aX;
        this.aY = aY;
        this.outerDegree = outerDegree;
        Transform();
    }

    public void Transform(){
           int mX = (int) (aX + Math.cos((double)height) * outerDegree);
           int mY = (int) (aY + Math.sin((double)height) * outerDegree);
           int distance = (int) Math.tan((double)(degree/2)* height);
           bX = aX + height * (int)Math.cos(Math.toRadians((double)(outerDegree + (degree /2))));
           bY = aY + height * (int)Math.sin(Math.toRadians((double)(outerDegree + (degree /2))));
           cX = aX + height * (int)Math.cos(Math.toRadians((double)(outerDegree - (degree /2))));
           cY = aY + height * (int)Math.sin(Math.toRadians((double)(outerDegree - (degree /2))));
    } */

    private Position base;
    private Position left;
    private Position right;

    public Triangle() {
        base = new Position();
        base = new Position();
        base = new Position();
    }

    public void setBase(Position value) {
        base = value;
    }
    public void setLeft(Position value) {
        left = value;
    }
    public void setRight(Position value) {
        right = value;
    }

    public Position getBase() {
        return base;
    }

    public Position getLeft() {
        return left;
    }

    public Position getRight() {
        return right;
    }
}
