package com.lucas.todoSimple.models;

import javax.persistence.CollectionTable;
//? Importação das Annotations
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.lucas.todoSimple.models.enums.ProfileEnum;

/*
    ! Annotations em respectiva ordem:
    ? Define essa classe como um modelo de entidade
    ? Definição do nome da tabela a ser criada no BD
    ? As proximas 5 Annotations são para geração em tempo real de alguns metodos usando Lombok sendo eles:
    ? Construtor com e sem argumentos, Getters e Setters e Hash e Equals  
 */
@Entity
@Table(name = User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    public interface CreateUser {}
    public interface UpdateUser {}

    // * Const nome da tabela
    public static final String TABLE_NAME = "user";

    @Id // Define esse atributo como o identificador do objeto
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o atributo como uma chave primaria gerada automaticamente
    @Column(name = "id", unique = true) // Define o nome dessa coluna como id e que ele é unico e não pode ser repetido no BD
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true) // Define o nome da coluna no BD e as regras que o registro deve seguir
    @NotNull(groups = CreateUser.class) // Esse campo não pode ser nulo
    @NotEmpty(groups = CreateUser.class) // Esse campo não pode ser vazio
    @Size(groups = CreateUser.class, min = 3, max = 100)//Regras para tamanho do registro
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY) //Define que esse atributo pode apenas ser escrito e não lido por questões de segurança
    @Column(name = "password", length = 100, nullable = false)//Nome da coluna no BD
    @NotNull(groups ={CreateUser.class, UpdateUser.class})// Não pode ser nulo
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})// Não pode ser Vazio
    @Size(groups = CreateUser.class, min = 8, max = 100) // Regras
    private String password;

    @OneToMany(mappedBy = "user") // Define a relação um para muitos entre a entidade de usuario com a entidade de tasks
    @JsonProperty(access = Access.WRITE_ONLY) // Apenas leitura
    private List<Task> tasks = new ArrayList<Task>();

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty(access = Access.WRITE_ONLY)
    @CollectionTable(name = "user_profile")
    @Column(name = "profile", nullable = false)
    private Set<Integer> profiles = new HashSet<>();

    public Set<ProfileEnum> getProfiles(){
        return this.profiles.stream().map(x -> ProfileEnum.ToEnum(x)).collect(Collectors.toSet());
    }

    public void addProfile(ProfileEnum profileEnum){
        this.profiles.add(profileEnum.getCode());
    }
}
