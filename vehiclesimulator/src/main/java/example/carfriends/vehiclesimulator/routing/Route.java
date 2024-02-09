package example.carfriends.vehiclesimulator.routing;

import example.carfriends.vehiclesimulator.Location;

import java.util.List;

public record Route(Location start, Location end, List<Section> sections) {
}

record Section(Location start, Location end) {
}
