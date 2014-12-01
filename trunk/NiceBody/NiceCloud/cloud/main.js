
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

Parse.Cloud.define("notify", function(request, response){
	var query = new Parse.Query(Parse.Installation);
	query.equalTo('phoneNumber', '821042746727');
	query.equalTo('wantPush', true);
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

