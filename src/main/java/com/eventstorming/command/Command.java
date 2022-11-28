

forEach: Command
fileName: {{namePascalCase}}Command.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/command
---
package {{options.package}}.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
{{#checkDateType aggregate.aggregateRoot.fieldDescriptors}} {{/checkDateType}}
{{#checkBigDecimal aggregate.aggregateRoot.fieldDescriptors}} {{/checkBigDecimal}}

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class {{namePascalCase}}Command {

    {{#if (isRepositoryPost this)}}

    {{#aggregate.aggregateRoot.fieldDescriptors}}
    {{#isKey}}    
    //<<< Etc / ID Generation
    private {{className}} {{nameCamelCase}};  // Please comment here if you want user to enter the id directly
    //>>> Etc / ID Generation
    {{else}}
    private {{className}} {{nameCamelCase}};
    {{/isKey}}
    {{/aggregate.aggregateRoot.fieldDescriptors}}

    {{else}}

    {{#aggregate.aggregateRoot.fieldDescriptors}}
    {{#isKey}}
    @TargetAggregateIdentifier
    private {{className}} {{nameCamelCase}};
    {{/isKey}}
    {{/aggregate.aggregateRoot.fieldDescriptors}}

    {{#fieldDescriptors}}
        {{#isKey}}
            @Id
            //@GeneratedValue(strategy=GenerationType.AUTO)
        {{/isKey}}
        private {{className}} {{nameCamelCase}};
    {{/fieldDescriptors}}

    {{/if}}
}


window.$HandleBars.registerHelper('checkDateType', function (fieldDescriptors) {

    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].className == 'Date'){
            return "import java.util.Date; \n"
        }
    }
});

window.$HandleBars.registerHelper('checkBigDecimal', function (fieldDescriptors) {
    for(var i = 0; i < fieldDescriptors.length; i ++ ){
        if(fieldDescriptors[i] && fieldDescriptors[i].className.includes('BigDecimal')){
            return "import java.math.BigDecimal;";
        }
    }
});
