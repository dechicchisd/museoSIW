package it.uniroma3.siw.model;

import java.time.LocalDate;
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
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Curatore implements Comparable<Curatore>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataDiNascita;
	
	
	
	@OneToMany(mappedBy="curatore", cascade = CascadeType.ALL)
	private List<Collezione> collezioni;

	@Override
	public int compareTo(Curatore o) {
		if(this.cognome.compareTo(o.getCognome()) == 0)
			return this.nome.compareTo(o.getNome());
			
		return this.cognome.compareTo(o.getCognome());
	}
	
}
