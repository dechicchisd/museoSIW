package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{
	public List<Opera> findByNome(String nome);
	public List<Opera> findByArtista(Artista artista);
}
