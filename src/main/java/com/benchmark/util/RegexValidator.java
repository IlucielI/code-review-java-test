package com.benchmark.util;

import java.util.regex.Pattern;

public class RegexValidator {

    // Performance Bug: Catastrophic backtracking regex prone to ReDoS
    private static final Pattern REDOS_PATTERN = Pattern.compile("^([a-zA-Z0-9]+)+$");

    public static boolean validate(String input) {
        return REDOS_PATTERN.matcher(input).matches();
    }
}
