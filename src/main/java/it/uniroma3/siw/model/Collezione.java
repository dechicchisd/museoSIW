package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Collezione {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	private String descrizione;
	
	@OneToMany(mappedBy="collezione", cascade=CascadeType.ALL)
	private List<Opera> opere;
	
	@ManyToOne
	private Curatore curatore;
	
	
	public void deleteOpera(int index) {
		this.opere.remove(index);	
	}
}
