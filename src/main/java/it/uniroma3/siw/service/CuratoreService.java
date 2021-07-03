package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Curatore;
import it.uniroma3.siw.repository.CuratoreRepository;

@Service
public class CuratoreService {

	@Autowired
	private CuratoreRepository curatoreRepo;
	
	@Transactional
	public Curatore inserisci(Curatore Curatore) {
		return curatoreRepo.save(Curatore);
	}
	
	@Transactional
	public List<Curatore> tutti(){
		return (List<Curatore>) curatoreRepo.findAll();
	}
	
	@Transactional
	public Curatore cercaOperaPerId(Long id) {
		Optional<Curatore> optional = curatoreRepo.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}
	
	@Transactional
	public boolean alreadyExsists(Curatore curatore) {
		List<Curatore> artisti = curatoreRepo.findByNomeAndCognome(curatore.getNome(), curatore.getCognome());
		
		if(artisti.size() > 0)
			return true;
		
		return false;
	}
}
