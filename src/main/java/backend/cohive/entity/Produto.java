package backend.cohive.entity;

public class Produto {
    private String nome;
    private String categoria;
    private Double valor;
    private String descricao;
    private Integer id;

    public Produto() {
    }

    public Produto(String nome, String categoria, Double valor, String descricao, Integer id) {
        this.nome = nome;
        this.categoria = categoria;
        this.valor = valor;
        this.descricao = descricao;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}