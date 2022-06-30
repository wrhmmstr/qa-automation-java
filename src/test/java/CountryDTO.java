public class CountryDTO {

    private String id;
    private String countryName;
    private String locations;

    public CountryDTO setId(String id) {
        this.id = id;
        return this;
    }

    public CountryDTO setCountryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public CountryDTO setLocations(String locations) {
        this.locations = locations;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getLocations() {
        return locations;
    }
}