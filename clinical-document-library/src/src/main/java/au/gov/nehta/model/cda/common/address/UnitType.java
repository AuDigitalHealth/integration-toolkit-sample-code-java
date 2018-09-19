package au.gov.nehta.model.cda.common.address;

/**
 * Address  - Unit type codes 
 */
public enum UnitType {


    /**
     * Antenna
     * 
     */
    ANT,

    /**
     * Apartment
     * 
     */
    APT,

    /**
     * Automated Teller Machine
     * 
     */
    ATM,

    /**
     * Barbeque
     * 
     */
    BBQ,

    /**
     * Boatshed
     * 
     */
    BTSD,

    /**
     * Building
     * 
     */
    BLDG,

    /**
     * Bungalow
     * 
     */
    BNGW,

    /**
     * Cage
     * 
     */
    CAGE,

    /**
     * Carpark
     * 
     */
    CARP,

    /**
     * Carspace
     * 
     */
    CARS,

    /**
     * Club
     * 
     */
    CLUB,

    /**
     * Coolroom
     * 
     */
    COOL,

    /**
     * Cottage
     * 
     */
    CTGE,

    /**
     * Duplex
     * 
     */
    DUP,

    /**
     * Factory
     * 
     */
    FY,

    /**
     * Flat
     * 
     */
    F,

    /**
     * Garage
     * 
     */
    GRGE,

    /**
     * Hall
     * 
     */
    HALL,

    /**
     * House
     * 
     */
    HSE,

    /**
     * Kiosk
     * 
     */
    KSK,

    /**
     * Lease
     * 
     */
    LSE,

    /**
     * Lobby
     * 
     */
    LBBY,

    /**
     * Loft
     * 
     */
    LOFT,

    /**
     * Lot
     * 
     */
    LOT,

    /**
     * Maisonette
     * 
     */
    MSNT,

    /**
     * Marine berth
     * 
     */
    MB,

    /**
     * Office
     * 
     */
    OFF,

    /**
     * Penthouse
     * 
     */
    PTHS,

    /**
     * Reserve
     * 
     */
    RESV,

    /**
     * Room
     * 
     */
    RM,

    /**
     * Shed
     * 
     */
    SHED,

    /**
     * Shop
     * 
     */
    SHOP,

    /**
     * Sign
     * 
     */
    SIGN,

    /**
     * Site
     * 
     */
    SITE,

    /**
     * Stall
     * 
     */
    SL,

    /**
     * Store
     * 
     */
    STOR,

    /**
     * Strata Unit
     * 
     */
    STR,

    /**
     * Studio
     * 
     */
    STU,

    /**
     * Substation
     * 
     */
    SUBS,

    /**
     * Suite
     * 
     */
    SE,

    /**
     * Tenancy
     * 
     */
    TNCY,

    /**
     * Tower
     * 
     */
    TWR,

    /**
     * Townhouse
     * 
     */
    TNHS,

    /**
     * Unit
     * 
     */
    U,

    /**
     * Villa
     * 
     */
    VLLA,

    /**
     * Ward
     * 
     */
    WARD,

    /**
     * Warehouse
     * 
     */
    WE,

    /**
     * Workshop
     * 
     */
    WKSH;

    public String value() {
        return name();
    }

    public static UnitType fromValue(String v) {
        return valueOf(v);
    }

}