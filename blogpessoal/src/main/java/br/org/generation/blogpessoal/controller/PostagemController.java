package br.org.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.generation.blogpessoal.model.Postagem;
import br.org.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() { // getAll() método para trazer todas as postagens.
		return ResponseEntity.ok(postagemRepository.findAll());
		// select * from tb_postagens;
	}
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable long id){	// Metódo para localizar postagem pelo ID.
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta)) // Verifica se o ID existe.
				.orElse(ResponseEntity.notFound().build()); // retorna not found caso o id não exista.
		// select * from tb_postagens where id = 1;
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) { // Método para localizar uma postagem pelo título. Pathvariable recepciona a URI enviada para a aplicação.
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		// select * from tb_postagens;
	}
	
	@PostMapping
	public ResponseEntity<Postagem> postPostagem(@RequestBody Postagem postagem) { //Método para atualizar uma postagem, requestbody recepciona os objetos/valores enviado para a aplicação.
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}
	
	@PutMapping
	public ResponseEntity<Postagem> putPostagem(@RequestBody Postagem postagem, Long id) { //Método para atualizar uma postagem
		return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem)
				);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletaPostagem(@PathVariable long id) { // Metódo para deletar uma postagem pelo id.
		return	postagemRepository.findById(id)
				.map(exist -> { postagemRepository.deleteById(id); return ResponseEntity.ok().build();}) //Verifica se o ID existe e se existir deleta o mesmo.
				.orElse( ResponseEntity.notFound().build());
	}
	
	
}
