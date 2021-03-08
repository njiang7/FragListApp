package com.cs250.joanne.myfragments.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class StatContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<StatItem> ITEMS = new ArrayList<StatItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, StatItem> ITEM_MAP = new HashMap<String, StatItem>();

    private static final int COUNT = 5;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createStatItem(i));
        }
    }

    private static void addItem(StatItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static StatItem createStatItem(int position) {
        if(position == 1) {
            return new StatItem(String.valueOf(position), "done by deadline", makeDetails(position));
        } else if (position == 2) {
            return new StatItem(String.valueOf(position), "done after due", makeDetails(position));
        } else if (position == 3) {
            return new StatItem(String.valueOf(position), "past due", makeDetails(position));
        } else if (position == 4) {
            return new StatItem(String.valueOf(position), "to be done", makeDetails(position));
        }

        return new StatItem(String.valueOf(position), "Total Tasks", makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class StatItem {
        public final String id;
        public final String content;
        public final String details;

        public StatItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}