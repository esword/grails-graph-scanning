
grails.plugins.dynamicController.mixins = [
		'com.burtbeckwith.grails.plugins.appinfo.IndexControllerMixin':
				'com.burtbeckwith.appinfo_test.AdminManageController',

		'com.burtbeckwith.grails.plugins.appinfo.Log4jControllerMixin' :
				'com.burtbeckwith.appinfo_test.AdminManageController',

		'com.burtbeckwith.grails.plugins.appinfo.SpringControllerMixin' :
				'com.burtbeckwith.appinfo_test.AdminManageController',

		'com.burtbeckwith.grails.plugins.appinfo.MemoryControllerMixin' :
				'com.burtbeckwith.appinfo_test.AdminManageController',

		'com.burtbeckwith.grails.plugins.appinfo.PropertiesControllerMixin' :
				'com.burtbeckwith.appinfo_test.AdminManageController',

		'com.burtbeckwith.grails.plugins.appinfo.ScopesControllerMixin' :
				'com.burtbeckwith.appinfo_test.AdminManageController',

		'com.burtbeckwith.grails.plugins.appinfo.ThreadsControllerMixin' :
				'com.burtbeckwith.appinfo_test.AdminManageController',

		'app.info.custom.example.MyConfigControllerMixin' :
				'com.burtbeckwith.appinfo_test.AdminManageController'
]