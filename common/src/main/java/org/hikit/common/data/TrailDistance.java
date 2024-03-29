package org.hikit.common.data;

public class TrailDistance {

    private final CoordinatesWithAltitude coordinates;
    private int distance;
    private Trail trail;

    public TrailDistance(int distance, CoordinatesWithAltitude coordinates, Trail trail) {
        this.distance = distance;
        this.coordinates = coordinates;
        this.trail = trail;
    }

    public int getDistance() {
        return distance;
    }

    public Trail getTrail() {
        return trail;
    }

    public CoordinatesWithAltitude getCoordinates() {
        return coordinates;
    }
}
