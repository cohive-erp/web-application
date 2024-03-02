package backend.cohive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

    @RestController
    @RequestMapping("/produtos")
    public class ProdutoController {

        private List<Produto> produtosEmEstoque = new ArrayList<>();

        //Método para cadastrar um produto novo
        @PostMapping
        public ResponseEntity<Produto> cadastrar(@RequestBody Produto produtoNovo){
            produtosEmEstoque.add(produtoNovo);

            return ResponseEntity.status(201).body(produtoNovo);
        }


}
