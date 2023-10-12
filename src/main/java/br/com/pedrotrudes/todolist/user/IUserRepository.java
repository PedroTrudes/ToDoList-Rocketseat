package br.com.pedrotrudes.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

//passando qual é o modelo e o tipo de Id que estamos usando
public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
}
