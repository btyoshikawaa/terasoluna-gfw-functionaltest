/*
 * Copyright(c) 2013 NTT DATA Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.terasoluna.gfw.functionaltest.app.time;

import static org.exparity.hamcrest.date.LocalDateTimeMatchers.before;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.sameInstant;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.sameOrAfter;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.sameOrBefore;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.terasoluna.gfw.functionaltest.app.FunctionTestSupport;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:META-INF/spring/seleniumContext.xml" })
public class DateAndTimeAPITest extends FunctionTestSupport {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm:ss.SSS");

    private static final LocalDateTime jdbcTime = LocalDateTime
            .parse("2013-01-01 01:01:01.000", dateTimeFormatter);
    private static final long jdbcAdjustDays = 86400000L / (1000L * 60L * 60L * 24L);

    private static final LocalDateTime configurableTime = LocalDateTime
            .parse("2012-09-11 02:25:15.000", dateTimeFormatter);
    private static final long configurableAdjustDays = 30L;

    @Test
    public void test01_01_serverTimeReturn() {
        driver.findElement(By.id("serverTimeReturn_01_01")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(screen.firstExpectedTime));
        assertThat(screen.serverTime, sameOrBefore(screen.lastExpectedTime));
        assertThat(screen.serverTime, sameInstant(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test01_02_serverTimeReturn() {
        driver.findElement(By.id("serverTimeReturn_01_02")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(screen.firstExpectedTime));
        assertThat(screen.serverTime, sameOrBefore(screen.lastExpectedTime));
        assertThat(screen.serverTime, before(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test02_01_jdbcTimeReturn() {
        driver.findElement(By.id("jdbcTimeReturn_02_01")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameInstant(jdbcTime));
        assertThat(screen.serverTime, sameInstant(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test02_02_jdbcTimeReturn() {
        driver.findElement(By.id("jdbcTimeReturn_02_02")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(jdbcTime));
        assertThat(screen.serverTime, before(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test02_03_jdbcTimeReturn() {
        driver.findElement(By.id("management")).click();

        // System Date Delete
        driver.findElement(By.id("deleteSystemDate")).click();

        driver.findElement(By.id("jdbcTimeReturn_02_03")).click();

        // error page screen
        assertThat(driver.findElement(By.cssSelector("h2")).getText(), is(
                "Data Access Error..."));
    }

    @Test
    public void test03_01_jdbcAdjustTimeReturn() {
        driver.findElement(By.id("jdbcAdjustTimeReturn_03_01")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(screen.firstExpectedTime.minusDays(jdbcAdjustDays)));
        assertThat(screen.serverTime, sameOrBefore(screen.lastExpectedTime.minusDays(jdbcAdjustDays)));
        assertThat(screen.serverTime, sameInstant(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test03_02_jdbcAdjustTimeReturn() {
        driver.findElement(By.id("jdbcAdjustTimeReturn_03_02")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(screen.firstExpectedTime.minusDays(jdbcAdjustDays)));
        assertThat(screen.serverTime, sameOrBefore(screen.lastExpectedTime.minusDays(jdbcAdjustDays)));
        assertThat(screen.serverTime, before(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test03_03_jdbcAdjustTimeReturn() {
        // delete Operation Date
        driver.findElement(By.id("management")).click();
        driver.findElement(By.id("deleteOperationDate")).click();

        driver.findElement(By.id("jdbcAdjustTimeReturn_03_03")).click();

        // error page screen
        assertThat(driver.findElement(By.cssSelector("h2")).getText(), is(
                "Data Access Error..."));

        // screen capture
        screenCapture.save(driver);

        driver.get(applicationContextUrl);
        driver.findElement(By.id("Date")).click();

        // insert adjustedValue
        driver.findElement(By.id("management")).click();
        driver.findElement(By.id("insertOperationDate")).click();
    }

    @Test
    public void test04_01_configurableTimeReturn() {
        driver.findElement(By.id("configurableTimeReturn_04_01")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameInstant(configurableTime));
        assertThat(screen.serverTime, sameInstant(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test04_02_configurableTimeReturn() {
        driver.findElement(By.id("configurableTimeReturn_04_02")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(configurableTime));
        assertThat(screen.serverTime, before(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test05_01_configurableAdjustTimeReturn() {
        driver.findElement(By.id("configurableAdjustTimeReturn_05_01")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(screen.firstExpectedTime.plusDays(configurableAdjustDays)));
        assertThat(screen.serverTime, sameOrBefore(screen.lastExpectedTime.plusDays(configurableAdjustDays)));
        assertThat(screen.serverTime, sameInstant(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    @Test
    public void test05_02_configurableAdjustTimeReturn() {
        driver.findElement(By.id("configurableAdjustTimeReturn_05_02")).click();

        Screen screen = new Screen(driver);

        // check Date
        assertThat(screen.serverTime, sameOrAfter(screen.firstExpectedTime.plusDays(configurableAdjustDays)));
        assertThat(screen.serverTime, sameOrBefore(screen.lastExpectedTime.plusDays(configurableAdjustDays)));
        assertThat(screen.serverTime, before(screen.afterServerTime));

        // check return type
        assertThat(screen.serverTimeZone, is(ZoneId.systemDefault()));
    }

    static class Screen {

        final LocalDateTime firstExpectedTime;
        final LocalDateTime serverTime;
        final LocalDateTime lastExpectedTime;
        final LocalDateTime afterServerTime;
        final ZoneId serverTimeZone;

        Screen(WebDriver driver) {

            // get element
            String firstExpectedTimeString = driver.findElement(By.id(
                    "firstExpectedTime")).getText();
            String serverTimeString = driver.findElement(By.id("serverTime"))
                    .getText();
            String lastExpectedTimeString = driver.findElement(By.id(
                    "lastExpectedTime")).getText();
            String afterServerTimeString = driver.findElement(By.id("afterServerTime"))
                    .getText();
            String serverTimeZoneString = driver.findElement(By.id("serverTimeZone")).getText();

            // convert
            firstExpectedTime = LocalDateTime.parse(firstExpectedTimeString, dateTimeFormatter);
            serverTime = LocalDateTime.parse(serverTimeString, dateTimeFormatter);
            lastExpectedTime = LocalDateTime.parse(lastExpectedTimeString, dateTimeFormatter);
            afterServerTime = LocalDateTime.parse(afterServerTimeString, dateTimeFormatter);
            serverTimeZone = ZoneId.of(serverTimeZoneString);
        }
    }
}
