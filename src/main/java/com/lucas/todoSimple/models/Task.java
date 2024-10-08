package com.lucas.todoSimple.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    ! Annotations em respectiva ordem:
    ? Define essa classe como um modelo de entidade
    ? Definição do nome da tabela a ser criada no BD
    ? As proximas 5 Annotations são para geração em tempo real de alguns metodos usando Lombok sendo eles:
    ? Construtor com e sem argumentos, Getters e Setters e Hash e Equals  
 */
@Entity
@Table(name = Task.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task {
    //* Nome da tabela
    public static final String TABLE_NAME = "task";

    @Id // Define esse atributo como o identificador do objeto
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Define o atributo como uma chave primaria gerada automaticamente
    @Column(name = "id", unique = true) // Define o nome dessa coluna como id e que ele é unico e não pode ser repetido no BD
    private Long id;

    @ManyToOne // Define o relacionamento Muitos para um entre Tasks e Usuarios
    @JoinColumn(name = "user_id", nullable = false, updatable = false) // Define como coluna de chave estrangeira o id do usuario que realizou o registro da task
    private User user;

    @Column(name = "description", length = 255, nullable = false) // Cria a coluna de Description do BD
    @NotNull // Não pode ser nulo
    @NotEmpty // Não pode ser vazio
    @Size(min = 1, max = 255) // Regras
    private String description;
    
}
