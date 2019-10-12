package net.heroyn.heroynapi.structures;

import javax.xml.stream.Location;

public class Position {

    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    public int getZ() {
        return z;
    }

    public Position setZ(int z) {
        this.z = z;
        return this;
    }

    public boolean isCenter(){
        return x == 0 && y == 0 && z == 0;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}
