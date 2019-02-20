package br.edu.ifba.gabrielvinicius.cerveja.Model;


import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

public class Cerveja extends SugarRecord {
    @NotNull
    private String nome;
    @NotNull
    private Marca marca;

    public Cerveja(){
    nome = "";
    marca = new Marca();
    }
    public Cerveja(String nome, Marca marca) {
        this.nome = nome;
        this.marca = marca;
    }



    public Marca getMarca() {
        return marca;
    }

    public String getNome() {
        return nome;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String toString() {
        return nome;
    }
}
