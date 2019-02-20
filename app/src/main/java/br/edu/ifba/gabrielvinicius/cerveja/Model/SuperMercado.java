package br.edu.ifba.gabrielvinicius.cerveja.Model;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

public class SuperMercado extends SugarRecord {

    public SuperMercado(){
        nome = "";
    }
    @NotNull
    @Unique
    private String nome;

    public SuperMercado(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    @Override
    public String toString() {
        return this.nome;
    }
}
