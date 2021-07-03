package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista, Long>{
	public List<Artista> findByNomeAndCognome(String nome, String cognome);

}
