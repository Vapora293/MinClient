package com.worwafi;

public enum SvStranaEnum {
    SEVER, JUH, VYCHOD, ZAPAD;

    @Override
    public String toString() {
        switch (this) {
            case SEVER: return "severnu";
            case JUH: return "juznu";
            case VYCHOD: return "vychodnu";
            case ZAPAD: return "zapadnu";
            default: throw new IllegalStateException("Svetova strana unknown");
        }
    }
}
