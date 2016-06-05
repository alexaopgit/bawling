package org.my.controller;

import org.my.service.BAException;
import org.my.service.BAScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BAController {

    @Autowired
    private BAScoreService bowlingScoreService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/bowling/score",
            method = RequestMethod.POST,
            produces = "application/json; charset=utf-8"
    )
    public int[] getScore(
            @RequestBody int[] knockedPins
    ) throws BAException {
        return bowlingScoreService.runBAWithCheck(knockedPins);
    }

    @RequestMapping(value = "/bowling", method = RequestMethod.GET)
    public ModelAndView page() {
        return new ModelAndView("bowling");
    }
}
