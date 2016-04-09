package deeplife.gcme.com.deeplife.Models;

/**
 * Created by BENGEOS on 4/5/16.
 */
public class Country {
    private String id,Country_id,iso3,name,code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry_id() {
        return Country_id;
    }

    public void setCountry_id(String country_id) {
        Country_id = country_id;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
