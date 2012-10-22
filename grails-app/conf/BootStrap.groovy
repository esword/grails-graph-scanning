class BootStrap {

    def init = { servletContext ->
		DomainClassSupport.addRetrieveErrors()
	}
    def destroy = {
    }
}
