<#list propertyList as property>
<tr>
    <td>
        ${property.name}
        <input type="hidden"  name="productPropertyValueList[${property_index}].name" value="${property.name}">
    </td>
    <td>
        <#if property.type==1>
        <select class="form-control" name="productPropertyValueList[${property_index}].value">
            <#list property.propertyValueList as properValue>
            <option value="${properValue.value}">${properValue.value}</option>
            </#list>
        </select>
        <#else>
            <input type="text" class="form-control" name="productPropertyValueList[${property_index}].value">
        </#if>
    </td>
</tr>
</#list>
