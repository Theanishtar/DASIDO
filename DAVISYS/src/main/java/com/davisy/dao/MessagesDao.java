package com.davisy.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.davisy.entity.Messages;

public interface MessagesDao extends JpaRepository<Messages, Integer> {

	@Query(value = "SELECT messages.id, messages.content, messages.send_Time, users.username,users.avatar\r\n"
			+ "FROM messages\r\n" + "INNER JOIN users ON messages.sender_id = users.id\r\n"
			+ "INNER JOIN chats ON messages.chat_id = chats.id\r\n"
			+ "WHERE chats.name_chats =:chatName", nativeQuery = true)
	public List<Object[]> findListMessage(String chatName);

	@Query(value = "SELECT COUNT(messages.id) FROM messages WHERE sender_id =:id", nativeQuery = true)
	public int countMessageUnread(int id);

	@Query(value = "UPDATE messages SET send_Status =:st WHERE sender_id =:id", nativeQuery = true)
	public void updateStatus(boolean st,int id);

	@Query(value = "SELECT * FROM messages WHERE send_Status = 0 AND sender_id =:id", nativeQuery = true)
	public List<Messages> findStatus(int id);
}
