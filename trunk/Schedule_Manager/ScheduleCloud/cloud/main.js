
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});
Parse.Cloud.define("testSms", function(request, callback){
	var toParam=request.params.targetPhoneNumber;
	var textParam=request.params.msg;
	Parse.Cloud.httpRequest({
		url: "https://rest.nexmo.com/sms/json",
		params: {
			api_key : "52e5277d",
			api_secret : "210c9016",
			from : "Heo",
			to : toParam,
			text: textParam,
		},
		success: function(httpResponse) {
			console.log(httpResponse.text);
			callback.success("success: "+httpResponse.text);
		},
		error: function(httpResponse) {
			console.error('Request failed with response code '+httpResponse.status);
			callback.success("error: "+httpResponse.status);
		}
	});
});

Parse.Cloud.define("notify", function(request, response){
	var query = new Parse.Query(Parse.Installation);
	
	query.equalTo('phoneNumber', request.params.phone); 
	query.equalTo("wantPush", true);
	Parse.Push.send(
		{
			where: query, // Set our Installation query
			data: {
				alert: 'good evening! time to take a shower!'
			}
		},{
			success: function(){
				// Push was successful
			},
			error: function(error) {
				// Handle error
			}
		}
	);
	response.success("message sent!");
});

