## Documentação do Encurtador de URL usando Spring Boot e Bitly API

# NÃO ESQUEÇA DE SUBSTITUIR SEU BIITLY_TOKEN

Este é um exemplo de aplicação Spring Boot que utiliza a API do Bitly para encurtar URLs. A aplicação consiste em três pacotes principais: `controller`, `model` e `service`. O pacote `controller` contém a classe `BitlyRestController`, responsável por expor os endpoints REST. O pacote `model` contém a classe `BitlyRequest`, que representa a estrutura do objeto de requisição. O pacote `service` contém a classe `BitlyService`, que contém a lógica para interagir com a API do Bitly.

### Configuração

1. Clone o repositório do projeto do encurtador de URL em seu ambiente de desenvolvimento.
2. Abra o projeto em sua IDE preferida.
3. Certifique-se de ter as dependências corretas no arquivo `pom.xml` para o Spring Boot e o cliente HTTP, como o Apache HttpClient ou o Spring WebClient.
4. Crie uma conta no Bitly e obtenha suas credenciais da API. Isso inclui um token de acesso válido.
5. No arquivo `application.properties`, adicione as seguintes configurações:
    
    ```
    # Configurações do Bitly API
    BIITLY_TOKEN = YOUR_BITLY_API_TOKEN
    ```
    
    Substitua `YOUR_BITLY_API_TOKEN` pelo token de acesso obtido na etapa anterior.
    

### Estrutura do Projeto

A estrutura do projeto deve ser organizada da seguinte maneira:

```
└── src
    └── main
        ├── java
        │   └── com
        │       └── example
        │           ├── controller
        │           │   └── BitlyRestController.java
        │           ├── model
        │           │   └── BitlyRequest.java
        │           └── service
        │               └── BitlyService.java
        └── resources
            └── application.properties

```

### Classe `BitlyRequest`

```
package com.example.model;
import lombok.Data;

@Data // create automatically the getters and setters for us
public class BitlyRequest {

    private String longURL;
    // getters and setters
}

```

Essa classe representa a estrutura do objeto de requisição JSON que será recebido pelo endpoint REST.

### Classe `BitlyService`

```
package com.itssnico.BitlyDemo.service;

import com.opsmatters.bitly.Bitly;
import com.opsmatters.bitly.api.model.v4.CreateBitlinkRequest;
import com.opsmatters.bitly.api.model.v4.CreateBitlinkResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service // to indicate this is the service of the application
public class BitlyService {

    @Value("${BIITLY_TOKEN}")
    String BIITLY_TOKEN; // here were mapping the token from the properties file

    private Bitly client;

    @PostConstruct
    public void setUp(){
        client = new Bitly(BIITLY_TOKEN);
    }
    public String getShortURL(String longUrl){
        String link = "error"; //setting this as default

        try{
            CreateBitlinkResponse response = client.bitlinks().shorten(longUrl).get();

            link = response.getLink();
            //in this try block were converting the long into short by
            // first getting the longURL and shorten him and giving back the
            // shortURL in the response and getting the link
        }
        catch (Exception e) {
        }

        return link;
    }

}
```

Essa classe é responsável por interagir com a API do Bitly para encurtar URLs. Ela utiliza o `RestTemplate` para fazer chamadas HTTP.

### Classe `BitlyRestController`

```
package com.example.controller;

import com.itssnico.BitlyDemo.model.BitlyRequest;
import com.itssnico.BitlyDemo.service.BitlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController// shows Spring that this class is the controller of the application
@Slf4j // Simple Logging Facade for Java provides a Java logging API by means of a simple facade pattern.
@RequestMapping("/api/v1") // shoq the path that we're gonna use
public class BitlyRestController {

    @Autowired
    BitlyService bitlyService; // we connected the service with the controller

    @PostMapping("/processBitly")
    public String processBitly(@RequestBody BitlyRequest bitlyRequest){
        String shortURL = bitlyService.getShortURL(bitlyRequest.getLongURL()); // here were getting the longURL to make it short
        return shortURL;
    }

}

```

Essa classe é responsável por definir os endpoints REST para a aplicação. Neste caso, temos apenas um endpoint `POST /shorten`, que recebe um objeto `BitlyRequest` no corpo da requisição e retorna o URL encurtado.

### Testando a Aplicação

Para testar a aplicação usando o Postman, siga estas etapas:

1. Inicie a aplicação Spring Boot.
2. Abra o Postman e crie uma nova requisição com o método `POST`.
3. Defina a URL da requisição como `http://localhost:8080/shorten`.
4. No corpo da requisição, selecione o formato `JSON` e insira o seguinte JSON:
    
    ```
    {
      "longUrl": "<https://www.example.com/very-long-url-with-lots-of-characters>"
    }
    
    ```
    
    Substitua `https://www.example.com/very-long-url-with-lots-of-characters` pela URL que você deseja encurtar.
    
5. Envie a requisição e você receberá a resposta com o URL encurtado.

---
