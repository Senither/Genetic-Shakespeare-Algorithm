package com.senither.shakespear;

import java.util.Random;

public class CharacterComparator {

    private static final Random RANDOM = new Random();
    private static final char[] ACCEPTABLE_CHARACTERS = " qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,.-'!\"'".toCharArray();

    public static char getRandom() {
        return ACCEPTABLE_CHARACTERS[RANDOM.nextInt(ACCEPTABLE_CHARACTERS.length)];
    }
}
