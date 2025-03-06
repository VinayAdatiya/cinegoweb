package entity;

public class City {
    private int cityId;
    private String cityName;
    private State state;

    public City(int city_id, String city_name) {
        this.cityId = city_id;
        this.cityName = city_name;
    }



    public City(int cityId) {
        this.cityId = cityId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
