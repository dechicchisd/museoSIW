package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.model.Opera;
import it.uniroma3.siw.repository.OperaRepository;

@Service
public class OperaService {
	
	@Autowired
	private OperaRepository operaRepository;
	
	@Transactional
	public Opera inserisci(Opera opera) {
		return operaRepository.save(opera);
	}
	
	@Transactional
	public List<Opera> tutti(){
		return (List<Opera>) operaRepository.findAll();
	}
	
	@Transactional
	public Opera cercaOperaPerId(Long id) {
		Optional<Opera> optional = operaRepository.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}
	
	@Transactional
	public boolean alreadyExists(Opera opera) {
		List<Opera> opere = operaRepository.findByNome(opera.getNome());
		
		if(opere.size() > 0)
			return true;
		
		return false;
	}
	
	@Transactional
	public List<Opera> getOperePerArtista(Artista a){
		return this.operaRepository.findByArtista(a);
	}

	@Transactional
	public void deleteOpera(Long id) {
		this.operaRepository.delete(this.cercaOperaPerId(id));	}
	
	@Transactional
	public void updateOpera(Opera opera, Long id) {

		this.operaRepository.updateNome(id, opera.getNome());
//		this.operaRepository.updateArtista(opera);
//		this.operaRepository.updateCollezione(opera);
	}
}
