package it.uniroma3.siw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Opera implements Comparable<Opera>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@ManyToOne
	private Artista artista;
	
	@ManyToOne
	private Collezione collezione;
	
	private String path;

	@Override
	public int compareTo(Opera o) {
		
		return this.nome.compareTo(o.getNome());
	}
	
	

	
}
