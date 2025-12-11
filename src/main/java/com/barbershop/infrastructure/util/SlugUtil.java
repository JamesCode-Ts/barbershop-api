package com.barbershop.infrastructure.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Random;

public final class SlugUtil {

    private static final Random RAND = new Random();

    private SlugUtil() {}

    /**
     * Converte texto normal para um slug amigável:
     * "Barbearia do Tiago" → "barbearia-do-tiago"
     */
    public static String toSlug(String input) {
        if (input == null) return null;

        String norm = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")           // remove acentos
                .replaceAll("[^\\w\\s-]", "")       // remove caracteres especiais
                .trim()
                .replaceAll("\\s+", "-")      // troca espaços por -
                .toLowerCase(Locale.ROOT);

        return norm;
    }

    /**
     * Retorna um sufixo curto para garantir unicidade:
     * Ex: "-a123"
     */
    public static String uniqueSuffix() {
        int r = RAND.nextInt(9000) + 1000; // 1000–9999
        return "-" + r;
    }
}
