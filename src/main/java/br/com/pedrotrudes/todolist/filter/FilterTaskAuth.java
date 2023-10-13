package br.com.pedrotrudes.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pedrotrudes.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{//usando Once pois estamos fazendo HTTP 

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                //Pegar a autenticação(usuario e senha)
                var authorization = request.getHeader("Authorization");
               
                //fazendo ele calcular o tamanho da string para poder retirar da variavel .trim remove o espaço em branco
                var authEncode = authorization.substring("Basic".length()).trim();

                byte[] authDecode = Base64.getDecoder().decode(authEncode);
                var authString = new String(authDecode);

                //pegando o authString e dividindo ele em um array de duas posições para pegar username e password
                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];

                System.out.println("Autorização");
                System.out.println(authString);
                System.out.println(username);
                System.out.println(password);
                
                //Validar se ele existe

                var user = this.userRepository.findByUsername(username);
                if(user == null){
                    response.sendError(401);
                }else{
                    //validar Senha
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword())
                    if(passwordVerify.verified) {
                        filterChain.doFilter(request, response);
                    }else{
                        response.sendError(401);
                    }
                    //Continua...
                    
                }
    
    }
    
}
