app = angular.module("user-app", ["ngRoute"]);

app.config(function($routeProvider) {
	$routeProvider
		.when("/user/shop", {
			templateUrl: "/user/shop/index.html",
			controller: "category-ctrl"
		}) 
		.otherwise({
			templateUrl: "/user/home/index.html"
		});
});
 