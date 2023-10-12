package br.com.pedrotrudes.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //isso coloca os gets e sets automaticamente se quiser somente o sett é só colocar @set
@Entity(name ="tb_users")
public class UserModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;

    private String name;

    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    //getters e setters quando o atributo é privado
   


}
