package com.thd.wx.joke.controller;

import com.thd.wx.joke.model.Joke;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/joke/*")
public class JokeController {

    @ResponseBody
    @RequestMapping("/listJoke")
    public ModelMap listJokes() {
        ModelMap map = new ModelMap();
        List<Joke> jokes = new ArrayList<Joke>();
        for (int i = 1; i <= 10; i++) {
            Joke joke = new Joke();
            joke.setName("陶发登" + i);
            joke.setValue("陶发登" + i);
            jokes.add(joke);
        }

        map.put("jokes", jokes);
        return map;
    }
}
