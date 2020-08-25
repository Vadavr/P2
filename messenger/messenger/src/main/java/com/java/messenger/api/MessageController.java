package com.java.messenger.api;


import com.java.messenger.dto.HistoryDTO;
import com.java.messenger.dto.LoadHistoryDTO;
import com.java.messenger.dto.SaveMessageDTO;
import com.java.messenger.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/save")
    public void saveMessage(@RequestBody SaveMessageDTO saveMessageDTO) {
        messageService.save(saveMessageDTO);
    }

    @ResponseBody
    @PostMapping("/history")
    public HistoryDTO loadHistory(@RequestBody LoadHistoryDTO loadHistoryDTO) {
        return messageService.loadHistory(loadHistoryDTO);
    }

}
