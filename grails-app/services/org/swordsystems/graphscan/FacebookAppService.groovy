package org.swordsystems.graphscan

import org.springframework.beans.factory.InitializingBean

class FacebookAppService implements InitializingBean {
	def grailsApplication

	@Delegate
	FacebookAccessor fah = new FacebookAccessor()
	String retrieveAppQueueName

	@Override
	void afterPropertiesSet() {
		def config = grailsApplication?.config
		retrieveAppQueueName = config.routes.retrieveApp.queueName
		facebookHost = config.facebook.host
		accessToken = config.facebook.accessToken

	}

	protected def postError(message) {
		//sendMessage(errorQueueName, message)
	}

	protected def sendMessage(queue, content) {

	}

	FacebookApp getFacebookApp(id) {
		FacebookApp fa = FacebookApp.get(id)
		if (!fa) {
			sendMessage(retrieveAppQueueName, id)
		}
		return fa
	}

	/**
	 * There should be the only one thread processing this.
	 * @param id
	 * @return
	 */
	def retrieveApp(id) {
		FacebookApp fa = FacebookApp.get(id)
		if (fa)
			return fa

		fa = new FacebookApp()
		fa.id = id

		log.info("Getting app info: $id")
		def params = [access_token:accessToken]

		def json = getResource(id)
		fa.id = id
		fa.name = json.name
		fa.category = json.category

		try {
			//another check before attempting to save
			if (FacebookApp.exists(id))
				fa = FacebookApp.find(id)
			else {
				FacebookApp.withTransaction {status ->
					if (!fa.save()) {
						log.error("Could not save app $id: ${fa.retrieveErrors()}")
						fa = null
					}
				}
			}
		}
		catch (Exception e) {
			log.error("Error trying to save app $id: $e.message")
			//for now, set to null.
			fa = null
		}
		return fa
	}
}
