package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;

public interface OperaRepository extends CrudRepository<Opera, Long>{
	public List<Opera> findByNome(String nome);
	public List<Opera> findByArtista(Artista artista);
	
	@Modifying
	@Query("UPDATE Opera o SET o.nome = ?2 WHERE o.id = ?1")
	public void updateNome(Long idOpera, String nomeOpera);
	
//	@Query("UPDATE")
//	public void updateArtista(Opera opera);
//	@Query("UPDATE")
//	public void updateCollezione(Opera opera);
}
