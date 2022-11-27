







forEach: Aggregate
fileName: {{namePascalCase}}Controller.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/api
---
package {{options.package}}.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.beans.BeanUtils;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;

import java.util.concurrent.CompletableFuture;


import {{options.package}}.aggregate.*;
import {{options.package}}.command.*;

//<<< Clean Arch / Inbound Adaptor
@RestController
public class {{ namePascalCase }}Controller {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public {{ namePascalCase }}Controller(CommandGateway commandGateway, QueryGateway queryGateway) {
      this.commandGateway = commandGateway;
      this.queryGateway = queryGateway;
  }
        {{#commands}}

        {{#isRestRepository}}
  @RequestMapping(value = "/{{ aggregate.namePlural }}",
        method = RequestMethod.{{restRepositoryInfo.method}}
      )
  public CompletableFuture {{nameCamelCase}}(@RequestBody {{namePascalCase}}Command {{nameCamelCase}}Command)
        throws Exception {
      System.out.println("##### /{{aggregate.nameCamelCase}}/{{nameCamelCase}}  called #####");

      // send command
      return commandGateway.send({{nameCamelCase}}Command)            
            .thenApply(
            id -> {
                  {{ ../namePascalCase }}Aggregate resource = new {{ ../namePascalCase }}Aggregate();
                  BeanUtils.copyProperties({{nameCamelCase}}Command, resource);

                  resource.set{{aggregate.aggregateRoot.keyFieldDescriptor.namePascalCase}}(({{aggregate.aggregateRoot.keyFieldDescriptor.className}})id);
                  
                  EntityModel<{{ ../namePascalCase }}Aggregate> model = EntityModel.of(resource);
                  model
                        .add(Link.of("/{{ ../namePlural }}/" + resource.get{{aggregate.aggregateRoot.keyFieldDescriptor.namePascalCase}}()).withSelfRel());

                  return new ResponseEntity<>(model, HttpStatus.OK);
            }
      );

  }
        {{/isRestRepository}}

        {{^isRestRepository}}
  @RequestMapping(value = "/{{ aggregate.namePlural }}/{id}/{{controllerInfo.apiPath}}",
        method = RequestMethod.{{controllerInfo.method}},
        produces = "application/json;charset=UTF-8")
  public CompletableFuture {{nameCamelCase}}(@PathVariable("id") {{aggregate.aggregateRoot.keyFieldDescriptor.className}} id, @RequestBody {{namePascalCase}}Command {{nameCamelCase}}Command)
        throws Exception {
      System.out.println("##### /{{aggregate.nameCamelCase}}/{{nameCamelCase}}  called #####");
      {{nameCamelCase}}Command.set{{aggregate.aggregateRoot.keyFieldDescriptor.namePascalCase}}(id);
      // send command
      return commandGateway.send({{nameCamelCase}}Command);
  }
        {{/isRestRepository}}
        {{/commands}}
}
//>>> Clean Arch / Inbound Adaptor
