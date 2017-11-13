<#if finderCol.comparator == "=">
	<#if finderCol.isPrimitiveType(false)>
		(${finderCol.name} != ${entity.varName}.get${finderCol.methodName}())
	<#else>
		!Objects.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == "!=">
	<#if finderCol.isPrimitiveType(false)>
		(${finderCol.name} == ${entity.varName}.get${finderCol.methodName}())
	<#else>
		Objects.equals(${finderCol.name}, ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == ">">
	<#if stringUtil.equals(finderCol.type, "Date")>
		(${finderCol.name}.getTime() >= ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} >= ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == ">=">
	<#if stringUtil.equals(finderCol.type, "Date")>
		(${finderCol.name}.getTime() > ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} > ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == "<">
	<#if stringUtil.equals(finderCol.type, "Date")>
		(${finderCol.name}.getTime() <= ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} <= ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif finderCol.comparator == "<=">
	<#if stringUtil.equals(finderCol.type, "Date")>
		(${finderCol.name}.getTime() < ${entity.varName}.get${finderCol.methodName}().getTime())
	<#else>
		(${finderCol.name} < ${entity.varName}.get${finderCol.methodName}())
	</#if>
<#elseif stringUtil.equals(finderCol.comparator, "LIKE")>
	!StringUtil.wildcardMatches(${entity.varName}.get${finderCol.methodName}(), ${finderCol.name}, '_', '%', '\\',
	<#if finderCol.isCaseSensitive()>
		true
	<#else>
		false
	</#if>
	)
</#if>