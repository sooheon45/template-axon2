



forEach: View
fileName: {{namePascalCase}}QueryController.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/api
---
package {{options.package}}.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import {{options.package}}.query.*;

@RestController
public class {{namePascalCase}}QueryController {

  private final QueryGateway queryGateway;

  public {{namePascalCase}}QueryController(QueryGateway queryGateway) {
      this.queryGateway = queryGateway;
  }
  

  @GetMapping("/{{namePlural}}")
  public CompletableFuture findAll() {
      return queryGateway.query(new {{namePascalCase}}Query(), ResponseTypes.multipleInstancesOf({{namePascalCase}}.class))
              .thenApply(resources -> {
                CollectionModel<{{namePascalCase}}> model = CollectionModel.of(resources);
                
                return new ResponseEntity<>(model, HttpStatus.OK);
            });

  }

}

