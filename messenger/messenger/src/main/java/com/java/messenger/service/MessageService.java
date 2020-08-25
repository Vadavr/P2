package com.java.messenger.service;

import com.java.messenger.dao.ConnectionDAO;
import com.java.messenger.dao.MessageDAO;
import com.java.messenger.dto.HistoryDTO;
import com.java.messenger.dto.LoadHistoryDTO;
import com.java.messenger.dto.MessageEnvelope;
import com.java.messenger.dto.SaveMessageDTO;
import com.java.messenger.repository.ConnectionsRepository;
import com.java.messenger.repository.MessageRepository;
import com.java.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.sql.Timestamp.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ConnectionsRepository connectionsRepository;


    public void save(SaveMessageDTO saveMessageDTO) {
        log.info("Message: {} by user: {} saved!", saveMessageDTO.getPayload(), saveMessageDTO.getUserId());
        messageRepository.save(MessageDAO.builder()
                .message(saveMessageDTO.getPayload())
                .timestamp(valueOf(now()))
                .userId(saveMessageDTO.getUserId())
                .build());
    }


    public HistoryDTO loadHistory(LoadHistoryDTO loadHistoryDTO) {
        var friend = userRepository.findUserDAOByUserName(loadHistoryDTO.getFriendUserName());
        if (friend.isPresent()) {
            var connections = connectionsRepository.findAllByOwnerId(loadHistoryDTO.getOwnerUserId())
                    .stream().map(ConnectionDAO::getFriendId).collect(toList());
            if (connections.contains(friend.get().getId())) {
                log.info("Connection established {} : {}", loadHistoryDTO.getOwnerUserId(), friend.get().getId());
                var friendMessages = messageRepository.findAllByUserId(friend.get().getId());
                var ownerMessages = messageRepository.findAllByUserId(loadHistoryDTO.getOwnerUserId());
                return HistoryDTO.builder()
                        .messageEnvelopes(Stream.concat(ownerMessages.stream(), friendMessages.stream())
                                .sorted(Comparator.comparing(MessageDAO::getTimestamp))
                                .map(dao -> MessageEnvelope.builder()
                                        .payload(dao.getMessage())
                                        .timestamp(dao.getTimestamp())
                                        .userId(dao.getUserId())
                                        .build())
                                .collect(toList())).build();
            }
            log.info("Creating new connection between users {} : {}", loadHistoryDTO.getOwnerUserId(), friend.get().getId());
            var conn1 = ConnectionDAO.builder()
                    .ownerId(loadHistoryDTO.getOwnerUserId())
                    .friendId(friend.get().getId())
                    .build();
            var conn2 = ConnectionDAO.builder()
                    .ownerId(friend.get().getId())
                    .friendId(loadHistoryDTO.getOwnerUserId())
                    .build();
            connectionsRepository.saveAll(List.of(conn1, conn2));
            return HistoryDTO.builder().build();


        }
        throw new EntityNotFoundException();

    }


}

