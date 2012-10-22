<%@ page import="org.swordsystems.graphscan.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'fbId', 'error')} ">
	<label for="fbId">
		<g:message code="user.fbId.label" default="Fb Id" />
		
	</label>
	<g:field type="number" name="fbId" value="${userInstance.fbId}" />
</div>

%{--
<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'friends', 'error')} ">
	<label for="friends">
		<g:message code="user.friends.label" default="Friends" />
		
	</label>
	<g:select name="friends" from="${org.swordsystems.graphscan.User.list()}" multiple="multiple" optionKey="id" size="5" required="" value="${userInstance?.friends*.id}" class="many-to-many"/>
</div>
--}%

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'items', 'error')} ">
	<label for="items">
		<g:message code="user.items.label" default="Items" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${userInstance?.items?}" var="i">
    <li><g:link controller="feedItem" action="show" id="${i.id}">${i?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="feedItem" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'feedItem.label', default: 'FeedItem')])}</g:link>
</li>
</ul>

</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="user.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${userInstance?.name}" />
</div>

