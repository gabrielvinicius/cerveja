package br.edu.ifba.gabrielvinicius.cerveja.Model;

import com.orm.SugarRecord;
import com.orm.dsl.NotNull;

public class Item extends SugarRecord {
    @NotNull
    private Cerveja cerveja;
    @NotNull
    private SuperMercado superMercado;
    @NotNull
    private Volume volume;
    @NotNull
    private Float preco;
    @NotNull
    private Cesta cesta;

    private Float precoPorLitro;


    public Item() {
        this.cerveja = new Cerveja();
        this.superMercado = new SuperMercado();
        this.volume = new Volume();
        this.preco = 0f;
        this.precoPorLitro = preco/volume.getVolume() * 1000;
    }

    public Item(Cerveja cerveja, SuperMercado superMercado, Volume volume, Float preco,Cesta cesta) {
        this.cerveja = cerveja;
        this.superMercado = superMercado;
        this.volume = volume;
        this.preco = preco;
        this.cesta = cesta;
        this.precoPorLitro = preco/volume.getVolume() * 1000;

    }
    public Item(long id,Cerveja cerveja, SuperMercado superMercado, Volume volume, Float preco,Cesta cesta) {
        super.setId(id);
        this.cerveja = cerveja;
        this.superMercado = superMercado;
        this.volume = volume;
        this.preco = preco;
        this.cesta = cesta;
        this.precoPorLitro = preco/volume.getVolume() * 1000;
    }
    public Cerveja getCerveja() {
        return cerveja;
    }

    public void setCerveja(Cerveja cerveja) {
        this.cerveja = cerveja;
    }

    public SuperMercado getSuperMercado() {
        return superMercado;
    }

    public void setSuperMercado(SuperMercado superMercado) {
        this.superMercado = superMercado;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
        this.precoPorLitro = preco/volume.getVolume() * 1000;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
        this.precoPorLitro = preco/volume.getVolume() * 1000;
    }

    public float getPrecoPorMl (){
        return preco/volume.getVolume();
    }
    public float getPrecoPorLitro(){
        return preco/volume.getVolume() * 1000;
    }

    public void setCesta(Cesta cesta) {
        this.cesta = cesta;
    }

    public Cesta getCesta() {
        return cesta;
    }
}
