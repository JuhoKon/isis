/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.subdomains.base.applib.valuetypes;

import java.util.Objects;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

/**
 * @since 2.0 {@index}
 */
public class LocalDateInterval extends AbstractInterval<LocalDateInterval>{

    public static LocalDateInterval excluding(final LocalDate startDate, final LocalDate endDate) {
        return new LocalDateInterval(startDate, endDate, IntervalEnding.EXCLUDING_END_DATE);
    }

    public static LocalDateInterval including(final LocalDate startDate, final LocalDate endDate) {
        return new LocalDateInterval(startDate, endDate, IntervalEnding.INCLUDING_END_DATE);
    }

    // //////////////////////////////////////

    public LocalDateInterval() {
    }

    public LocalDateInterval(final Interval interval) {
        super(interval);
    }

    public LocalDateInterval(final LocalDate startDate, final LocalDate endDate) {
        super(startDate, endDate);
    }

    public LocalDateInterval(final LocalDate startDate, final LocalDate endDate, final IntervalEnding ending) {
        super(startDate, endDate, ending);
    }

    // //////////////////////////////////////

    @Override
    protected LocalDateInterval newInterval(Interval overlap) {
        return new LocalDateInterval(overlap);
    }

    // //////////////////////////////////////

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocalDateInterval)) {
            return false;
        }
        LocalDateInterval rhs = (LocalDateInterval) obj;
        return Objects.equals(startDate, rhs.startDate)
                && Objects.equals(endDate, rhs.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        StringBuilder builder =
                new StringBuilder(
                        dateToString(startDate()))
                        .append("/")
                        .append(dateToString(endDateExcluding()));
        return builder.toString();
    }

    public String toString(String format) {
        StringBuilder builder =
                new StringBuilder(
                        dateToString(startDate(), format))
                        .append("/")
                        .append(dateToString(endDate(), format));
        return builder.toString();
    }

    // //////////////////////////////////////

    /**
     * Parse a string representation of a LocalDateInterval
     *
     * Since this method is only used for testing it's not heavily guarded against illegal arguments
     *
     * @param input a string with format {@literal yyyy-mm-dd/yyyy-mm-dd}, end date is excluding
     */
    public static LocalDateInterval parseString(final String input) {
        String[] values = input.split("/");
        try {
            return new LocalDateInterval(parseLocalDate(values[0]), parseLocalDate(values[1]), IntervalEnding.EXCLUDING_END_DATE);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse " + input);
        }
    }

    /**
     * Parse a string to a LocalDate
     *
     * @param input  a string representing a parsable LocalDate, "*" or "----------" returns null
     */
    private static LocalDate parseLocalDate(final String input) {
        if (input.contains("--") || input.contains("*")) {
            return null;
        }
        return LocalDate.parse(input);
    }


    /**
     * Returns an end date given the start date of the next adjoining interval
     *
     * @param date
     */
    public static LocalDate endDateFromStartDate(LocalDate date) {
        return new LocalDateInterval(date, null).endDateFromStartDate();

    }

}
