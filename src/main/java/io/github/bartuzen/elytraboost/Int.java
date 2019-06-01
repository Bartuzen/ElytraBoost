package io.github.bartuzen.elytraboost;

public class Int {
    int i;

    public Int(int i) {
        this.i = i;
    }

    public int getFly() {
        return i;
    }

    public void setFly(int i) {
        this.i = i;
    }

    public boolean equals(int i) {
        return this.i == i;
    }
}