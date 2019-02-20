package br.edu.ifba.gabrielvinicius.cerveja.Model;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;
import com.orm.dsl.Unique;

public class Volume extends SugarRecord {
    @NotNull
    private int volume;
    @NotNull
    @Unique
    private String nome;

    public Volume(){
        volume = 0;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Volume(int volume, String nome) {
        this.volume = volume;
        this.nome = nome;
    }


    public int getVolume() {
        return volume;
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
