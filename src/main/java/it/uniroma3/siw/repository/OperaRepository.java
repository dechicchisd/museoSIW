package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{
	public List<Opera> findByNome(String nome);
	public List<Opera> findByArtista(Artista artista);

	@Query("SELECT o FROM Opera o WHERE o.artista.id = :idArtista")
	public List<Opera> findByArtistaId(@Param("idArtista") Long id);

}
