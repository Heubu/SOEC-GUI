package de.hrw.java.src;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bayer on 25.01.2016.
 */

public class FlightEntity extends ODataObject {
    private static String entityName = "FlightEntity";
    private static String namespace = "ZHRW_FLIGHTS_MGNT_V03_SRV";
    private String carrier;
    private String carrierid;
    private Calendar lastchangetmstp;
    private String connectionid;
    private Calendar departuredatetime;
    private String countrycodefrom;
    private String countryfrom;
    private String cityfrom;
    private String airportfrom;
    private String countrycodeto;
	private String countryto;
    private String cityto;
    private String airportto;
    private String planetype;
    private String maxseatseconomy;
    private String priceeconomy;
    private String currency;
    private String maxseatsbusiness;
    private String pricebusiness;
    private String maxseatsfirst;
    private String pricefirst;

    public FlightEntity(String carrierid, String connectionid, Calendar departuredatetime) {
        super(Arrays.asList("carrierid", "connectionid", "departuredatetime"));
        this.carrierid = carrierid;
        this.connectionid = connectionid;
        this.departuredatetime = departuredatetime;
    }

    public FlightEntity() {
        super(Arrays.asList("carrierid", "connectionid", "departuredatetime"));
    }

    @Override
    public boolean validate() {
        if (this.carrierid == null) {return false;}
        if (this.connectionid == null) {return false;}
        if (this.departuredatetime == null) {return false;}
        return true;
    }

    public String getCarrier() { return this.carrier;}

    public void setCarrier(String carrier) { this.carrier = carrier;}

    public String getCarrierId() { return this.carrierid;}

    public void setCarrierId(String carrierid) { this.carrierid = carrierid;}

    public Calendar getLastChangeTmstp() { return this.lastchangetmstp;}

    public void setLastChangeTmstp(Calendar lastchangetmstp) { this.lastchangetmstp = lastchangetmstp;}

    public String getConnectionId() { return this.connectionid;}

    public void setConnectionId(String connectionid) { this.connectionid = connectionid;}

    public Calendar getDepartureDatetime() { return this.departuredatetime;}

    public void setDepartureDatetime(Calendar departuredatetime) { this.departuredatetime = departuredatetime;}

    public String getCountryCodeFrom() { return this.countrycodefrom;}

    public void setCountryCodeFrom(String countrycodefrom) { this.countrycodefrom = countrycodefrom;}

    public String getCountryFrom() { return this.countryfrom;}

    public void setCountryFrom(String countryfrom) { this.countryfrom = countryfrom;}

    public String getCityFrom() { return this.cityfrom;}

    public void setCityFrom(String cityfrom) { this.cityfrom = cityfrom;}

    public String getAirportFrom() { return this.airportfrom;}

    public void setAirportFrom(String airportfrom) { this.airportfrom = airportfrom;}

    public String getCountryCodeTo() { return this.countrycodeto;}

    public void setCountryCodeTo(String countrycodeto) { this.countrycodeto = countrycodeto;}

    public String getCountryTo() { return this.countryto;}

    public void setCountryTo(String countryto) { this.countryto = countryto;}

    public String getCityTo() { return this.cityto;}

    public void setCityTo(String cityto) { this.cityto = cityto;}

    public String getAirportTo() { return this.airportto;}

    public void setAirportTo(String airportto) { this.airportto = airportto;}

    public String getPlanetype() { return this.planetype;}

    public void setPlanetype(String planetype) { this.planetype = planetype;}

    public String getMaxSeatsEconomy() { return this.maxseatseconomy;}

    public void setMaxSeatsEconomy(String maxseatseconomy) { this.maxseatseconomy = maxseatseconomy;}

    public String getPriceEconomy() { return this.priceeconomy;}

    public void setPriceEconomy(String priceeconomy) { this.priceeconomy = priceeconomy;}

    public String getCurrency() { return this.currency;}

    public void setCurrency(String currency) { this.currency = currency;}

    public String getMaxSeatsBusiness() { return this.maxseatsbusiness;}

    public void setMaxSeatsBusiness(String maxseatsbusiness) { this.maxseatsbusiness = maxseatsbusiness;}

    public String getPriceBusiness() { return this.pricebusiness;}

    public void setPriceBusiness(String pricebusiness) { this.pricebusiness = pricebusiness;}

    public String getMaxSeatsFirst() { return this.maxseatsfirst;}

    public void setMaxSeatsFirst(String maxseatsfirst) { this.maxseatsfirst = maxseatsfirst;}

    public String getPriceFirst() { return this.pricefirst;}

    public void setPriceFirst(String pricefirst) { this.pricefirst = pricefirst;}

    public String getentityName() { return FlightEntity.entityName;}

    public String getnamespace() { return this.namespace;}

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> m = new HashMap<>();
        m.put("Carrier", carrier);
        m.put("CarrierId", carrierid);
        m.put("LastChangeTmstp", lastchangetmstp);
        m.put("ConnectionId", connectionid);
        m.put("DepartureDatetime", departuredatetime);
        m.put("CountryCodeFrom", countrycodefrom);
        m.put("CountryFrom", countryfrom);
        m.put("CityFrom", cityfrom);
        m.put("AirportFrom", airportfrom);
        m.put("CountryCodeTo", countrycodeto);
        m.put("CountryTo", countryto);
        m.put("CityTo", cityto);
        m.put("AirportTo", airportto);
        m.put("Planetype", planetype);
        m.put("MaxSeatsEconomy", maxseatseconomy);
        m.put("PriceEconomy", priceeconomy);
        m.put("Currency", currency);
        m.put("MaxSeatsBusiness", maxseatsbusiness);
        m.put("PriceBusiness", pricebusiness);
        m.put("MaxSeatsFirst", maxseatsfirst);
        m.put("PriceFirst", pricefirst);
        return m;
    }

    @Override
    public String toString() {
        return carrier + ", " + carrierid + ", " + lastchangetmstp + ", " + connectionid + ", " +
                departuredatetime + ", " + countrycodefrom + ", " + countryfrom + ", " + cityfrom + ", " + airportfrom + ", " +
                countrycodeto + ", " + countryto + ", " + cityto + ", " + airportto + ", " + planetype + ", " + maxseatseconomy + ", " +
                priceeconomy + ", " + currency + ", " + maxseatsbusiness + ", " + pricebusiness + ", " + maxseatsfirst + ", " + pricefirst;
    }
}
