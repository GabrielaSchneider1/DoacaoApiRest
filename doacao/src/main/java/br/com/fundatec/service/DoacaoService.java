package br.com.fundatec.service;


import br.com.fundatec.model.PedidoDoacao;
import org.springframework.stereotype.Service;

import com.google.common.collect.FluentIterable;

import br.com.fundatec.repository.DoacaoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;

    public DoacaoService(DoacaoRepository doacaoRepository) {
        this.doacaoRepository = doacaoRepository;
    }

    public PedidoDoacao listarDoacoes(Long id) {
         Optional<PedidoDoacao> pedidoDoacao = doacaoRepository.findById(id);
         return pedidoDoacao.isPresent()? pedidoDoacao.get():null;
    }

    public PedidoDoacao consultar (Long id) {
        return doacaoRepository.findById(id).orElse(null);
    }

    public PedidoDoacao incluir(PedidoDoacao doacao) {
        validar(doacao);
        return doacaoRepository.save(doacao);
        
    }

    private void validar (PedidoDoacao doacao) {
//        List<String> nomesValidos = Arrays.asList("Fraldas", "Alimento","Roupa","Medicamento",
//                "Material de Higiene Pessoal", "Produto de Limpeza");
//        if (!nomesValidos.contains(doacao.getNome())) {
//            throw new RuntimeException("O nome " + doacao.getNome() + "é inválido.");
//        }
    }

    public PedidoDoacao atualizar(Long idDoacao, PedidoDoacao doacaoParaAtualizacao) {
        PedidoDoacao doacao = consultar(idDoacao);
        if (doacao != null) {
            doacao.setStatus(doacaoParaAtualizacao.isStatus());
            doacao.setIdInstituicao(doacaoParaAtualizacao.getIdInstituicao());
        }
        doacaoRepository.save(doacao);
        return doacao;
    }

    public void excluir(Long id) {
        doacaoRepository.deleteById(id);
    }

	public List<PedidoDoacao> listarDoacoes() {
		return FluentIterable.from(doacaoRepository.findAll()).toList();
	}
}
