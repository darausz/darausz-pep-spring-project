package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
  private MessageRepository messageRepository;

  @Autowired
  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public Message createMessage(Message message) throws Exception {
    String messageText = message.getMessageText();
    if (messageText.isBlank() || messageText.length() > 255) {
      throw new Exception("400");
    }
    Message messageCreated = messageRepository.saveAndFlush(message);
    return messageCreated;
  }

  public List<Message> findAllMessages() throws Exception {
    List<Message> messages = messageRepository.findAll();
    if (messages == null) {
      throw new Exception("400");
    }
    return messages;
  }
  
  public List<Message> findAllMessagesByAccountId(Integer accountId) throws Exception {
    List<Message> messages = messageRepository.findAllByPostedBy(accountId);
    if (messages == null) {
      throw new Exception("400");
    }
    return messages;
  }

  public Message findMessageById(Integer id) {
    Optional<Message> message = messageRepository.findById(id);
    if (message.isPresent()) {
      return message.get();
    }
    return null;
  }

  public Integer deleteById(Integer id) throws Exception{
    Optional<Message> message = messageRepository.findById(id);
    if (message.isPresent()) {
      messageRepository.deleteById(id);
      return 1;
    }
    throw new Exception("400");
  }

  public Integer updateById(Integer messageId, Message message) throws Exception{
    String messageUpdatedText = message.getMessageText();
    if (messageUpdatedText.isBlank() || messageUpdatedText.length() > 255) {
      throw new Exception("400");
    }
    Message messageToUpdate = findMessageById(messageId);
    if (messageToUpdate == null) {
      throw new Exception("400");
    }
    messageToUpdate.setMessageText(messageUpdatedText);
    messageRepository.save(messageToUpdate);
    return 1;
  }
}
