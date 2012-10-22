package org.swordsystems.graphscan

import groovyx.net.http.RESTClient
import groovyx.net.http.HttpResponseException
import groovy.util.logging.Log4j

/**
 * @author esword
 */
@Log4j
class FacebookAccessor {
	String accessToken
	String facebookHost = 'https://graph.facebook.com'

	def getResource(resource, params = [:]) {
		def fullParms = [access_token:accessToken] + params
		def rc = new RESTClient(facebookHost)
		try {
			def result = rc.get(path:resource, query:fullParms)
			return result.data
		}
		catch (HttpResponseException hre) {
			def dataString = hre.response.data.toString()
			log.error("HTTP error processing getting resource $resource: $dataString")
			return null
		}
	}
}
