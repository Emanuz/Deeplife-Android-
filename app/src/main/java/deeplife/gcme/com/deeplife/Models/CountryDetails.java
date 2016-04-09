package deeplife.gcme.com.deeplife.Models;

/**
 * Created by rog on 10/31/2015.
 */
public class CountryDetails {

    public static String[] country = new String[]{"Botswana", "Comoros", "Ethiopia",

            "Kenya", "Lesotho", "Madagascar", "Malawi", "Mauritius", "Mazambique", "Namibia",

            "Reunion", "Rwanda", "Seychelles", "South Africa", "South Sudan", "Tanzania", "Zambia",

            "Zimbabwe"};

    public static String[]  code = new String[]{"+267", "+269", "+251", "+254", "+266", "+261", "+265", "+222", "+258", "+264", "+262",

            "+250", "+248", "+27", "+211", "+255", "+260", "+263"};

    public String[] getCountry() {
        return country;
    }

    public String[] getCode() {
        return code;
    }
}
