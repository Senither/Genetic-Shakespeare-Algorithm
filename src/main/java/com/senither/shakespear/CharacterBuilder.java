package com.senither.shakespear;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CharacterBuilder {

    private static final Random RANDOM = new Random();
    private final List<Character> characters = new ArrayList<>();

    public CharacterBuilder(String string) {
        for (char c : string.toCharArray()) {
            if (!characters.contains(c)) {
                characters.add(c);
            }
        }
    }

    public char getRandom() {
        return characters.get(RANDOM.nextInt(characters.size()));
    }
}
