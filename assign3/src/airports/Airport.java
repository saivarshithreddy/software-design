package airports;

public record Airport(String IATACode, String name, String city, String state, double temperature, boolean delay) {
}
