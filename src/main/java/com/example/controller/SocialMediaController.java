package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
  private AccountService accountService;
  private MessageService messageService;

  @Autowired
  public SocialMediaController(AccountService accountService, MessageService messageService) {
    this.accountService = accountService;
    this.messageService = messageService;
  }

  @GetMapping("messages")
  public ResponseEntity<List<Message>> getAllMessages() {
    try {
      List<Message> messages = messageService.findAllMessages();
      return ResponseEntity.status(200).body(messages);
    }
    catch (Exception e) {
      if (e.getMessage().equals("400")) {
        return ResponseEntity.status(200).build();
      }
    }
    return ResponseEntity.status(200).build();
  }

  @PostMapping("messages")
  public ResponseEntity<Message> createMessage(@RequestBody Message message) {
    try {
      if (accountService.accountExists(message.getPostedBy())) {
        try {
          Message messageCreated = messageService.createMessage(message);
        return ResponseEntity.status(200).body(messageCreated);
        }
        catch (Exception e) {
          if (e.getMessage().equals("400")) {
            return ResponseEntity.status(400).build();
          }
        }
      }
    }
    catch (Exception e) {
      if (e.getMessage().equals("400")) {
        return ResponseEntity.status(400).build();
      }
    }
    return ResponseEntity.status(400).build();
  }

  @GetMapping("messages/{messageId}")
  public ResponseEntity<Message> getMessageById(@PathVariable int messageId) {
    Message message = messageService.findMessageById(messageId);
    return ResponseEntity.status(200).body(message);
  }

  @DeleteMapping("messages/{messageId}")
  public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId) {
    try {
      int body = messageService.deleteById(messageId);
      return ResponseEntity.status(200).body(body);
    }
    catch (Exception e) {
      if (e.getMessage().equals("400")) {
        return ResponseEntity.status(200).build();
      }
    }
    return ResponseEntity.status(200).build();
  }

  @PatchMapping("messages/{messageId}")
  public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message message) {
    try {
      int body = messageService.updateById(messageId, message);
      return ResponseEntity.status(200).body(body);
    }
    catch (Exception e) {
      if (e.getMessage().equals("400")) {
        return ResponseEntity.status(400).build();
      }
    }
    return ResponseEntity.status(400).build();
  }

  @GetMapping("accounts/{accountId}/messages")
  public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable int accountId) {
    try {
      if (accountService.accountExists(accountId)) {
        try {
          List<Message> messages = messageService.findAllMessagesByAccountId(accountId);
          return ResponseEntity.status(200).body(messages);
        }
        catch (Exception e) {
          if (e.getMessage().equals("400")) {
            return ResponseEntity.status(200).build();
          }
        }
      }
    }
    catch (Exception e) {
      if (e.getMessage().equals("400")) {
        return ResponseEntity.status(200).build();
      }
    }
    return ResponseEntity.status(200).build();
  }

  @PostMapping("register")
  public ResponseEntity<Account> register(@RequestBody Account account) {
    try {
      Account accountInserted = accountService.register(account);
      return ResponseEntity.status(200).body(accountInserted);
    }
    catch (Exception e) {
      if (e.getMessage().equals("409")) {
        return ResponseEntity.status(409).build();
      }
      if (e.getMessage().equals("400")) {
        return ResponseEntity.status(400).build();
      }
    }
    return ResponseEntity.status(400).build();
  }

  @PostMapping("login")
  public ResponseEntity<Account> login(@RequestBody Account account) {
    try {
      Account accountLoggedIn = accountService.login(account);
      return ResponseEntity.status(200).body(accountLoggedIn);
    }
    catch (Exception e) {
      if (e.getMessage().equals("401")) {
        return ResponseEntity.status(401).build();
      }
    }
    return ResponseEntity.status(400).build();
  }
}
