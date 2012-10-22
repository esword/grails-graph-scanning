import grails.util.Holders

/**
 * @author esword
 */
class DomainClassSupport {
	static addRetrieveErrors() {
		Holders.grailsApplication.domainClasses.each {domainClass ->//iterate over the domainClasses
			domainClass.metaClass.retrieveErrors = {
				def messageSource = Holders.applicationContext.getBean('messageSource')
				delegate?.errors?.allErrors?.collect {messageSource.getMessage(it, null)}?.join(' \n')
			}
		}
	}
}
