



forEach: RelationEventInfo
fileName: {{eventValue.namePascalCase}}Event.java
path: {{boundedContext.name}}/{{{options.packagePath}}}/event
priority: 2
---
package {{options.package}}.event;

import lombok.*;

{{#checkDateType fieldDescriptors}} {{/checkDateType}}
{{#checkBigDecimal fieldDescriptors}} {{/checkBigDecimal}}

@Data
@ToString
public class {{eventValue.namePascalCase}}Event {

{{#eventValue.fieldDescriptors}}
    private {{className}} {{nameCamelCase}};
{{/eventValue.fieldDescriptors}}
}