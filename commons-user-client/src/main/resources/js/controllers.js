function UserController($xhr) {


    this.user = {
        id: 1,
        firstName: '',
        lastName: '',
        emailAddress: ''
    };

    var self = this;

//    $xhr('GET', 'http://local.commons-user.com/backend/user'
//        ,function(code, response) {
//            self.response = response;
//            self.code = code;
//            self.message = "Success was awesome!";
//        },
//        function(code, response) {
//            self.response = response;
//            self.code = code;
//            self.message = "Error occurred";
//        }
//
//    );


    self.registerUser = function()
    {
        $xhr.defaults.headers.post['Content-Type']='application/json';
        $xhr('POST', 'http://local.commons-user.com/backend/user', self.user,
            function(code, response) {
                self.response = response;
                self.code = code;
                self.message = "Success was awesome!";
            },
            function(code, response) {
                self.response = response;
                self.code = code;
                self.message = "Error occurred";
            }
        );
    }

}