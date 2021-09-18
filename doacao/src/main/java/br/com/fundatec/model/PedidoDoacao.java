package br.com.fundatec.model;


import javax.persistence.*;
import java.util.Set;

@Entity
public class PedidoDoacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    private Long idInstituicao;

    @OneToMany(mappedBy = "doacao")
    private Set<ItemPedido> pedidos;

    public PedidoDoacao() {

    }


    public PedidoDoacao(Long id, String nome, String quantidade) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Long getIdInstituicao() {
		return idInstituicao;
	}


	public void setIdInstituicao(Long idInstituicao) {
		this.idInstituicao = idInstituicao;
	}
}
