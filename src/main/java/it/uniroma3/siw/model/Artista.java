package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
public class Artista implements Comparable<Artista>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	private String cognome;
	
	private String biografia;
	
	private String path;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataDiNascita;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataDiMorte;
	
	private String luogoDiNascita;

	private String luogoDiMorte;
	
	@OneToMany(mappedBy="artista", cascade=CascadeType.REMOVE)
	private List<Opera> opere;
	
	
	public Artista() {
		this.opere = new ArrayList<Opera>();
	}
	
	
	public void addOpera(Opera opera) {
		this.opere.add(opera);
	}

	
	@Override
	public int compareTo(Artista o) {
		if(this.nome.compareTo(o.getNome()) == 0)
			return this.cognome.compareTo(o.getCognome());
			
		return this.nome.compareTo(o.getNome());
	}

	
}
