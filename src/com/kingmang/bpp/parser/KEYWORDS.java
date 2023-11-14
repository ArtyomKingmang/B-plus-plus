package com.kingmang.bpp.parser;

public enum KEYWORDS {
    ДЛЯПОКУДА("для"), ПОКУДА("покуда "),
    ВОИН("воин"),
    ВОЗДАТЬ("воздать"),
    МОЛВИТЬ("молвить"),
    НОВЬ("новь"),
    ПРАВДА("правда"),
    КРИВДА("кривда"),
    УСЛОВКА("коли"), ОТНЮДЬ("отнюдь");

    private final String text;

    KEYWORDS(String text) {
        this.text = text;
    }
    public String toString() {
        return this.text;
    }
}
