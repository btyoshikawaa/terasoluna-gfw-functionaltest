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

import java.time.Clock;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.terasoluna.gfw.common.time.ClockFactory;
import org.terasoluna.gfw.functionaltest.domain.service.date.DateService;

@Controller
@RequestMapping(value = "time")
public class DateAndTimeController {

    @Inject
    @Named("clockFactory")
    protected ClockFactory clockFactory;

    @Inject
    @Named("jdbcClockFactory")
    protected ClockFactory jdbcClockFactory;

    @Inject
    @Named("dbErrorJdbcClockFactory")
    protected ClockFactory dbErrorJdbcClockFactory;

    @Inject
    @Named("jdbcAdjustClockFactory")
    protected ClockFactory jdbcAdjustClockFactory;

    @Inject
    @Named("dbErrorJdbcAdjustClockFactory")
    protected ClockFactory dbErrorJdbcAdjustClockFactory;

    @Inject
    @Named("configurableClockFactory")
    protected ClockFactory configurableClockFactory;

    @Inject
    @Named("configurableAdjustClockFactory")
    protected ClockFactory configurableAdjustClockFactory;

    @Inject
    protected DateService dateService;

    @Value("${sleep.millis:100}")
    protected Long sleepMillis;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {

        return "time/index";
    }

    @RequestMapping(value = "1_1", method = RequestMethod.GET)
    public String serverTimeReturn_01_01(Model model) {

        return handleClock(model, clockFactory.fixed());
    }

    @RequestMapping(value = "1_2", method = RequestMethod.GET)
    public String serverTimeReturn_01_02(Model model) {

        return handleClock(model, clockFactory.tick());
    }

    @RequestMapping(value = "2_1", method = RequestMethod.GET)
    public String jdbcTimeReturn_02_01(Model model) {

        return handleClock(model, jdbcClockFactory.fixed());
    }

    @RequestMapping(value = "2_2", method = RequestMethod.GET)
    public String jdbcTimeReturn_02_02(Model model) {

        return handleClock(model, jdbcClockFactory.tick());
    }

    @RequestMapping(value = "2_3", method = RequestMethod.GET)
    public String jdbcTimeReturn_02_03(Model model) {

        dbErrorJdbcClockFactory.fixed();

        return "time/dateTimeDisplay";
    }

    @RequestMapping(value = "3_1", method = RequestMethod.GET)
    public String jdbcAdjustTimeReturn_03_01(Model model) {

        return handleClock(model, jdbcAdjustClockFactory.fixed());
    }

    @RequestMapping(value = "3_2", method = RequestMethod.GET)
    public String jdbcAdjustTimeReturn_03_02(Model model) {

        return handleClock(model, jdbcAdjustClockFactory.tick());
    }

    @RequestMapping(value = "3_3", method = RequestMethod.GET)
    public String jdbcAdjustTimeReturn_03_03(Model model) {

        dbErrorJdbcAdjustClockFactory.fixed();

        return "time/dateTimeDisplay";
    }

    @RequestMapping(value = "4_1", method = RequestMethod.GET)
    public String configurableTimeReturn_04_01(Model model) {

        return handleClock(model, configurableClockFactory.fixed());
    }

    @RequestMapping(value = "4_2", method = RequestMethod.GET)
    public String configurableTimeReturn_04_02(Model model) {

        return handleClock(model, configurableClockFactory.tick());
    }

    @RequestMapping(value = "5_1", method = RequestMethod.GET)
    public String configurableAdjustTimeReturn_05_01(Model model) {

        return handleClock(model, configurableAdjustClockFactory.fixed());
    }

    @RequestMapping(value = "5_2", method = RequestMethod.GET)
    public String configurableAdjustTimeReturn_05_02(Model model) {

        return handleClock(model, configurableAdjustClockFactory.tick());
    }

    @RequestMapping(value = "manage", method = RequestMethod.GET)
    public String manage() {
        return "time/dateTimeManage";
    }

    @RequestMapping(value = "diff", method = RequestMethod.POST)
    public String updateDiffTime(@RequestParam("diffTime") String diffTime) {
        dateService.updateOperationDate("1", diffTime);
        return "time/index";
    }

    @RequestMapping(value = "insertOperationDate", method = RequestMethod.GET)
    public String insertOperationDate() {
        dateService.insertOperationDate("2", "-86400000");
        return "time/index";
    }

    @RequestMapping(value = "deleteSystemDate", method = RequestMethod.GET)
    public String deleteSystemDate() {
        dateService.deleteSystemDate(2);
        return "time/index";
    }

    @RequestMapping(value = "deleteOperationDate", method = RequestMethod.GET)
    public String deleteOperationDate() {
        dateService.deleteOperationDate(2);
        return "time/index";
    }

    private String handleClock(Model model, Clock clock) {

        model.addAttribute("firstExpectedTime", LocalDateTime.now());
        sleep();
        model.addAttribute("serverTime", LocalDateTime.now(clock));
        sleep();
        model.addAttribute("lastExpectedTime", LocalDateTime.now());
        sleep();
        model.addAttribute("afterServerTime", LocalDateTime.now(clock));

        model.addAttribute("serverTimeZone", clock.getZone().getId());

        return "time/dateTimeDisplay";
    }

    private void sleep() {
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
