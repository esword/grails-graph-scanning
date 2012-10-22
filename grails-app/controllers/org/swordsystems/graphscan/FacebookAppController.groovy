package org.swordsystems.graphscan

import org.springframework.dao.DataIntegrityViolationException

class FacebookAppController {

	static scaffold = true
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [facebookAppInstanceList: FacebookApp.list(params), facebookAppInstanceTotal: FacebookApp.count()]
    }

    def create() {
        [facebookAppInstance: new FacebookApp(params)]
    }

    def save() {
        def facebookAppInstance = new FacebookApp(params)
        if (!facebookAppInstance.save(flush: true)) {
            render(view: "create", model: [facebookAppInstance: facebookAppInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), facebookAppInstance.id])
        redirect(action: "show", id: facebookAppInstance.id)
    }

    def show(Long id) {
        def facebookAppInstance = FacebookApp.get(id)
        if (!facebookAppInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), id])
            redirect(action: "list")
            return
        }

        [facebookAppInstance: facebookAppInstance]
    }

    def edit(Long id) {
        def facebookAppInstance = FacebookApp.get(id)
        if (!facebookAppInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), id])
            redirect(action: "list")
            return
        }

        [facebookAppInstance: facebookAppInstance]
    }

    def update(Long id, Long version) {
        def facebookAppInstance = FacebookApp.get(id)
        if (!facebookAppInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (facebookAppInstance.version > version) {
                facebookAppInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'facebookApp.label', default: 'FacebookApp')] as Object[],
                          "Another user has updated this FacebookApp while you were editing")
                render(view: "edit", model: [facebookAppInstance: facebookAppInstance])
                return
            }
        }

        facebookAppInstance.properties = params

        if (!facebookAppInstance.save(flush: true)) {
            render(view: "edit", model: [facebookAppInstance: facebookAppInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), facebookAppInstance.id])
        redirect(action: "show", id: facebookAppInstance.id)
    }

    def delete(Long id) {
        def facebookAppInstance = FacebookApp.get(id)
        if (!facebookAppInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), id])
            redirect(action: "list")
            return
        }

        try {
            facebookAppInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'facebookApp.label', default: 'FacebookApp'), id])
            redirect(action: "show", id: id)
        }
    }
}
