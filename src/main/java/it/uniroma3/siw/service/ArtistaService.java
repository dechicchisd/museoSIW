package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Artista;
import it.uniroma3.siw.repository.ArtistaRepository;

@Service
public class ArtistaService {
	
	@Autowired
	private ArtistaRepository artistaRepo;
	
	@Transactional
	public Artista inserisci(Artista artista) {
		return artistaRepo.save(artista);
	}
	
	@Transactional
	public List<Artista> tutti(){
		return (List<Artista>) artistaRepo.findAll();
	}
	
	@Transactional
	public Artista cercaArtistaPerId(Long id) {
		Optional<Artista> optional = artistaRepo.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		
		return null;
	}
	
	@Transactional
	public boolean alreadyExists(Artista artista) {
		List<Artista> artisti = artistaRepo.findByNomeAndCognome(artista.getNome(), artista.getCognome());
		
		if(artisti.size() > 0)
			return true;
		
		return false;
	}

	public void deleteArtista(Long id) {
		this.artistaRepo.delete(this.cercaArtistaPerId(id));
	}

	public void updateArtista(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	
}
