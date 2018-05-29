package com.inspiware.price.aggregator.utils;

import com.inspiware.price.aggregator.Constants;
import com.inspiware.price.aggregator.domain.Instrument;
import com.inspiware.price.aggregator.domain.InstrumentPrice;
import com.inspiware.price.aggregator.domain.Vendor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {

    public static final Date DATE_31_DAYS_AGO = new Date(System.currentTimeMillis() - (Constants.ONE_DAY_IN_MILLIS * 31));
    public static final Date DATE_30_DAYS_AGO = new Date(System.currentTimeMillis() - (Constants.ONE_DAY_IN_MILLIS * 30));
    public static final Date DATE_1_WEEK_AGO = new Date(System.currentTimeMillis() - (Constants.ONE_DAY_IN_MILLIS * 7));
    public static final Date DATE_1_DAY_AGO = new Date(System.currentTimeMillis() - Constants.ONE_DAY_IN_MILLIS);

    public static final Instrument testInstrument1 = new Instrument(10001L, "AAPL", "Apple Inc", "$");
    public static final Instrument testInstrument2 = new Instrument(10002L, "GOOGL", "Alphabet Inc", "$");
    public static final Instrument testInstrument3 = new Instrument(10003L, "MSFT", "Microsoft Corporation", "$");
    public static final Instrument testInstrument4 = new Instrument(10004L, "FB", "Facebook, Inc", "$");
    public static final Instrument testInstrument5 = new Instrument(10005L, "TWTR", "Twitter, Inc", "$");

    public static final Vendor vendor1 = new Vendor(10001L, "Vendor1");
    public static final Vendor vendor2 = new Vendor(10002L, "Vendor2");
    public static final Vendor vendor3 = new Vendor(10003L, "Vendor3");

    // Apple prices
    public static final InstrumentPrice testPrice1 = new InstrumentPrice(testInstrument1, vendor1, new BigDecimal("160.26"), new BigDecimal("170.26"), DATE_31_DAYS_AGO);
    public static final InstrumentPrice testPrice2 = new InstrumentPrice(testInstrument1, vendor2, new BigDecimal("161.26"), new BigDecimal("169.26"), DATE_31_DAYS_AGO);
    public static final InstrumentPrice testPrice3 = new InstrumentPrice(testInstrument1, vendor3, new BigDecimal("160.56"), new BigDecimal("169.96"), DATE_31_DAYS_AGO);
    public static final InstrumentPrice testPrice4 = new InstrumentPrice(testInstrument1, vendor1, new BigDecimal("172.26"), new BigDecimal("192.26"), DATE_30_DAYS_AGO);
    public static final InstrumentPrice testPrice5 = new InstrumentPrice(testInstrument1, vendor2, new BigDecimal("176.26"), new BigDecimal("186.26"), DATE_30_DAYS_AGO);
    public static final InstrumentPrice testPrice6 = new InstrumentPrice(testInstrument1, vendor3, new BigDecimal("178.26"), new BigDecimal("184.26"), DATE_30_DAYS_AGO);
    public static final InstrumentPrice testPrice7 = new InstrumentPrice(testInstrument1, vendor1, new BigDecimal("165.26"), new BigDecimal("171.00"), DATE_1_WEEK_AGO);
    public static final InstrumentPrice testPrice8 = new InstrumentPrice(testInstrument1, vendor2, new BigDecimal("164.26"), new BigDecimal("170.89"), DATE_1_WEEK_AGO);
    public static final InstrumentPrice testPrice9 = new InstrumentPrice(testInstrument1, vendor3, new BigDecimal("166.26"), new BigDecimal("170.78"), DATE_1_WEEK_AGO);
    public static final InstrumentPrice testPrice10 = new InstrumentPrice(testInstrument1, vendor1, new BigDecimal("172.26"), new BigDecimal("178.26"), DATE_1_DAY_AGO);
    public static final InstrumentPrice testPrice11 = new InstrumentPrice(testInstrument1, vendor2, new BigDecimal("172.86"), new BigDecimal("178.86"), DATE_1_DAY_AGO);
    public static final InstrumentPrice testPrice12 = new InstrumentPrice(testInstrument1, vendor3, new BigDecimal("172.66"), new BigDecimal("178.66"), DATE_1_DAY_AGO);

    // Google prices
    public static final InstrumentPrice testPrice13 = new InstrumentPrice(testInstrument2, vendor1, new BigDecimal("160.26"), new BigDecimal("170.26"), DATE_31_DAYS_AGO);
    public static final InstrumentPrice testPrice14 = new InstrumentPrice(testInstrument2, vendor2, new BigDecimal("161.26"), new BigDecimal("169.26"), DATE_31_DAYS_AGO);
    public static final InstrumentPrice testPrice15 = new InstrumentPrice(testInstrument2, vendor3, new BigDecimal("160.56"), new BigDecimal("169.96"), DATE_31_DAYS_AGO);
    public static final InstrumentPrice testPrice16 = new InstrumentPrice(testInstrument2, vendor1, new BigDecimal("172.26"), new BigDecimal("192.26"), DATE_30_DAYS_AGO);
    public static final InstrumentPrice testPrice17 = new InstrumentPrice(testInstrument2, vendor2, new BigDecimal("176.26"), new BigDecimal("186.26"), DATE_30_DAYS_AGO);
    public static final InstrumentPrice testPrice18 = new InstrumentPrice(testInstrument2, vendor3, new BigDecimal("178.26"), new BigDecimal("184.26"), DATE_30_DAYS_AGO);
    public static final InstrumentPrice testPrice19 = new InstrumentPrice(testInstrument2, vendor1, new BigDecimal("165.26"), new BigDecimal("171.00"), DATE_1_WEEK_AGO);
    public static final InstrumentPrice testPrice20 = new InstrumentPrice(testInstrument2, vendor2, new BigDecimal("164.26"), new BigDecimal("170.89"), DATE_1_WEEK_AGO);
    public static final InstrumentPrice testPrice21 = new InstrumentPrice(testInstrument2, vendor3, new BigDecimal("166.26"), new BigDecimal("170.78"), DATE_1_WEEK_AGO);
    public static final InstrumentPrice testPrice22 = new InstrumentPrice(testInstrument2, vendor1, new BigDecimal("172.26"), new BigDecimal("178.26"), DATE_1_DAY_AGO);
    public static final InstrumentPrice testPrice23 = new InstrumentPrice(testInstrument2, vendor2, new BigDecimal("172.86"), new BigDecimal("178.86"), DATE_1_DAY_AGO);
    public static final InstrumentPrice testPrice24 = new InstrumentPrice(testInstrument2, vendor3, new BigDecimal("172.66"), new BigDecimal("178.66"), DATE_1_DAY_AGO);

    public static Set<InstrumentPrice> getAllPrices() {
        return new HashSet<>(Arrays.asList(testPrice1, testPrice2, testPrice3, testPrice4, testPrice5, testPrice6,
                testPrice7, testPrice8, testPrice9, testPrice10, testPrice11, testPrice12, testPrice13, testPrice14,
                testPrice15, testPrice16, testPrice17, testPrice18, testPrice19, testPrice20, testPrice21, testPrice22,
                testPrice23, testPrice24));
    }
}
