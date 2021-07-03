package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Collezione;
import it.uniroma3.siw.repository.CollezioneRepository;

@Service
public class CollezioneService {
	@Autowired
	private CollezioneRepository collezioneRepo;
	
	@Transactional
	public Collezione inserisci(Collezione collezione) {
		return collezioneRepo.save(collezione);
	}
	
	@Transactional
	public List<Collezione> tutti(){
		return (List<Collezione>) collezioneRepo.findAll();
	}
	
	@Transactional
	public Collezione cercaArtistaPerId(Long id) {
		Optional<Collezione> optional = collezioneRepo.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}
	
	@Transactional
	public boolean alreadyExists(Collezione collezione) {
		List<Collezione> collezioni = collezioneRepo.findByNome(collezione.getNome());
		
		if(collezioni.size() > 0)
			return true;
		
		return false;
	}

	
}
