// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = org.swordsystems.graphscan // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
	// Example of changing the log pattern for the default console
	// appender:
	//
	appenders {
		rollingFile name: 'file', file: "graphscan.log", maxFileSize:'2MB', maxBackupIndex:3, threshold: org.apache.log4j.Level.DEBUG, layout: pattern(conversionPattern: '%d{dd HH:mm:ss.SSS} %-4.4p [%15.15t] %-50.50c{10}.%15.15M[%4L]: %m%n')
		console name: 'stdout', threshold: org.apache.log4j.Level.INFO, layout: pattern(conversionPattern: '%d{HH:mm:ss} %-4.4p [%15.15t] %c{2} %m%n')
	}
	root {
		info 'stdout', 'file'
	}

	error   'org.codehaus.groovy.grails.web.servlet',  //  controllers
			'org.codehaus.groovy.grails.web.pages', //  GSP
			'org.codehaus.groovy.grails.web.sitemesh', //  layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping', // URL mapping
			'org.codehaus.groovy.grails.commons', // core / classloading
			'org.codehaus.groovy.grails.plugins', // plugins
			'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate',
			'grails.util.GrailsUtil',
			'grails.plugin.webxml.WebxmlGrailsPlugin',
			'grails.app.resourceMappers.org.grails.plugin.resource.BundleResourceMapper'

	warn 'com.burtbeckwith.grails.plugins',
			'org.grails.datastore.gorm.neo4j.Neo4jSession'

	info 'grails.app.services.org.swordsystems.graphscan',
			'grails.app.controllers.org.swordsystems.graphscan',
			'grails.app.jobs'

	debug 'grails.app.services.org.swordsystems.graphscan.GraphQueryService'

	//debug 'org.codehaus.groovy.grails.plugins.springsecurity',
	//        'org.springframework.security.core'
}

//*****************************************************************************
// Application config file extensions - I like to keep the primary Config.groovy file
// nearly pristine so that when I upgrade to a new version of grails I can easily compare
// my app's Config.groovy to the template file in the new grails and see what new settings
// need to be incorporated.
//*****************************************************************************
grails.config.locations = []

//The security config contains the primary settings for the spring-security plugin, including the url-access mapping
//grails.config.locations << SecurityConfig
//Configs for app-info plugin
grails.config.locations << AppInfoConfig
//The AppDefaultConfig contains all of my application-specific config settings.
grails.config.locations << AppDefaultConfig

// JVM arg method: Useful mainly in dev. By passing in -Dapp.config.location=<FULL_PATH_OF_CONFIG_FILE>
// when starting the app you can specify any config file you desire
def appConfigPath = System.properties["app.config.location"]
if (appConfigPath && (new File(appConfigPath).exists())) {
	grails.config.locations << "file:" + appConfigPath
} else {
	//look in user dir.
	def configAppName = 'graphscan'
	def userConfigFile = new File(userHome, "${configAppName}/user_config.groovy")
	if (userConfigFile.exists())
		grails.config.locations << "file:$userConfigFile.path"
	grails.config.locations << "classpath:custom_${configAppName}_config.groovy"
}
