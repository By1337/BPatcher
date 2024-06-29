package org.by1337.bpatcher;

import java.util.NoSuchElementException;

public enum Version {
    V1_14("1.14", 477, 1952),
    V1_14_1("1.14.1", 480, 1957),
    V1_14_2("1.14.2", 485, 1963),
    V1_14_3("1.14.3", 490, 1968),
    V1_14_4("1.14.4", 498, 1976),
    V1_15("1.15", 573, 2225),
    V1_15_1("1.15.1", 575, 2227),
    V1_15_2("1.15.2", 578, 2230),
    V1_16("1.16", 735, 2566),
    V1_16_1("1.16.1", 736, 2567),
    V1_16_2("1.16.2", 751, 2578),
    V1_16_3("1.16.3", 753, 2580),
    V1_16_4("1.16.4", 754, 2584),
    V1_16_5("1.16.5", 754, 2586),
    V1_17("1.17", 755, 2724),
    V1_17_1("1.17.1", 756, 2730),
    V1_18("1.18", 757, 2860),
    V1_18_1("1.18.1", 757, 2865),
    V1_18_2("1.18.2", 758, 2975),
    V1_19("1.19", 759, 3105),
    V1_19_1("1.19.1", 760, 3117),
    V1_19_2("1.19.2", 760, 3120),
    V1_19_3("1.19.3", 761, 3218),
    V1_19_4("1.19.4", 762, 3337),
    V1_20("1.20", 763, 3463),
    V1_20_1("1.20.1", 763, 3465),
    V1_20_2("1.20.2", 764, 3578),
    V1_20_3("1.20.3", 765, 3698),
    V1_20_4("1.20.4", 765, 3700),
    V1_20_5("1.20.5", 766, 3837),
    V1_20_6("1.20.6", 766, 3839),
    V1_21("1.21", 767, 3953);
    private final String id;
    private final int protocol;
    private final int worldVersion;
    private static Version current;

    Version(String id, int protocol, int worldVersion) {
        this.id = id;
        this.protocol = protocol;
        this.worldVersion = worldVersion;
    }

    public static Version getVersionById(String id) {
        for (Version value : values()) {
            if (value.id.endsWith(id)) return value;
        }
        throw new NoSuchElementException("Unknown version " + id);
    }

    public static Version getCurrent() {
        return current;
    }

    static void setCurrent(Version current) {
        Version.current = current;
    }

    public String getId() {
        return id;
    }

    public int getProtocol() {
        return protocol;
    }

    public int getWorldVersion() {
        return worldVersion;
    }
}
