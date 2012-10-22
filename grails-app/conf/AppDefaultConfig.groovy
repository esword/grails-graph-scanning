import grails.util.Environment
import grails.util.GrailsUtil

app {
    init {
        createTestUsers = (GrailsUtil.environment == 'development')
        createTestData = (GrailsUtil.environment == 'development')
    }
}

facebook {
	//Insert your application's access token here or in the $userhome/graphscan/user_config.groovy file
	accessToken = ''
	host = 'https://graph.facebook.com'

	graphscan {

	}
}
