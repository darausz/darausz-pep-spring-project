package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
  List<Message> findAll();
  List<Message> findAllByPostedBy(Integer postedBy);
  Optional<Message> findById(Integer id);
  void deleteById(Integer id);
}
