grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
		excludes 'xml-apis', 'netty', 'stax-api', 'slf4j-jdk14','log4j-over-slf4j', 'logback-classic'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

		mavenRepo "http://repo.grails.org/grails/repo/"
		mavenRepo 'http://m2.neo4j.org/releases'

		// uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

	neo4jVerison="1.8.M07"
	dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

		compile 'org.apache.httpcomponents:httpclient:4.2'
		//compile 'net.sf.json-lib:json-lib:2.4'

		compile ('org.codehaus.groovy.modules.http-builder:http-builder:0.5.2') {
			exclude 'groovy'
		}

		compile("org.neo4j:neo4j-community:$neo4jVerison")

		// uncomment next three lines if you're using embedded/ha *and* you want the webadmin available
		compile(group:"org.neo4j.app", name:"neo4j-server", version:neo4jVerison)
		runtime(group:"org.neo4j.app", name:"neo4j-server", version:neo4jVerison, branch:"static-web")
		//runtime (group:"org.neo4j", name:"neo4j-shell", version:""1.8.M07")
		runtime('com.sun.jersey:jersey-server:1.9')
		runtime('com.sun.jersey:jersey-core:1.9')
	}

    plugins {
		//Built-in Plugins
        //runtime ":hibernate:$grailsVersion"
        runtime ":jquery:1.8.0"
		runtime ":resources:1.2-RC1"
		build ":tomcat:$grailsVersion"
		//runtime ":database-migration:1.1"
		compile ':cache:1.0.0'

		// Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.4"

		compile ":app-info:1.0.2"
		//compile ':grails-melody:1.12'
		compile ":console:1.2"
		compile ":jmx:0.7.2"

		compile ":neo4j:1.0.0.M2"

		compile ':jquery-ui:1.8.15'

		//compile ":cloud-foundry:1.2.3"
		compile ":standalone:1.1.1"
	}
}
