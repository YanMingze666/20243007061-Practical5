package com.example.practical5;

/**
 * Validates and normalises municipality names.
 * A plain Java class - it does not extend Activity and has no layout.
 */
public class TextProcessor {

    /**
     * Returns the correctly capitalised name for a supported municipality,
     * or null if the input is null, empty, whitespace-only, or unsupported.
     */
    public String processMunicipality(String municipality) {
        // Reject null, empty, or whitespace-only input
        if (municipality == null || municipality.trim().isEmpty()) {
            return null;
        }

        String name = municipality.trim().toLowerCase();

        switch (name) {
            case "beijing":
                return "Beijing";
            case "shanghai":
                return "Shanghai";
            case "tianjin":
                return "Tianjin";
            case "chongqing":
                return "Chongqing";
            default:
                return null; // unsupported name, e.g. "Guangzhou"
        }
    }
}
