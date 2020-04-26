package com.jebcodes.stockprices.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class YahooStockPriceController {

    private static final Logger logger = Logger.getLogger(YahooStockPriceController.class.getName());

    @GetMapping(value = "/api/v1/getPrices")
    ResponseEntity<Stock> getStockPrices(@RequestParam(value = "code") String code, @RequestParam(value = "from") String from, @RequestParam(value = "to") String to) throws IOException, ParseException {

        Calendar fromC = convertToCalender(from);
        Calendar toC = convertToCalender(to);

        Stock google = YahooFinance.get(code);
        List<HistoricalQuote> googleHistQuotes = google.getHistory(fromC, toC, Interval.DAILY);

        google.print();
        googleHistQuotes.forEach(ahistQuote -> {
            System.out.println(ahistQuote.getClose());
        });
        return new ResponseEntity<>(google, HttpStatus.OK);
    }

    private Calendar convertToCalender(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(dateString);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender;
    }


}
