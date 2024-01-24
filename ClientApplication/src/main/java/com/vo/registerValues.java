package com.vo;

public class registerValues {
    private int pieceCount;

    private Previous previous;
    private Current current;

    public registerValues() {
    }

    public registerValues(int pieceCount, Previous previous, Current current) {
        this.pieceCount = pieceCount;
        this.previous = previous;
        this.current = current;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }

    public Previous getPrevious() {
        return previous;
    }

    public void setPrevious(Previous previous) {
        this.previous = previous;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "registerValues [pieceCount=" + pieceCount + ", previous=" + previous + ", current=" + current + "]";
    }

}
