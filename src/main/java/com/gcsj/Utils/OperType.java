package com.gcsj.Utils;

public enum OperType {
    ADD(0),
    GET(1),
    PUT(2),
    POST(3);

    private final int key;

    private OperType(int key) {
        this.key = key;
    }
    
    public int getKey() {
        return this.key;
    }

}
