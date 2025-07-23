package com.smartphonebatch.controller;

import com.smartphonebatch.model.Smartphone;
import com.smartphonebatch.service.KafkaReplayConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class KafkaReplayController {

    @Autowired
    private KafkaReplayConsumer kafkaReplayConsumer;

    @GetMapping("/replay")
    public String triggerReplay(Model model) {
        List<Smartphone> messages = kafkaReplayConsumer.replayMessages();
        model.addAttribute("smartphones", messages);
        model.addAttribute("count", messages.size());
        return "replay";
    }
}