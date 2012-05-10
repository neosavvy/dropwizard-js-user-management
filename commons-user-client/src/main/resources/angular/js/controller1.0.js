function UserController($scope, $resource) {

    $scope.user = {
        firstName: '',
        lastName: '',
        email: ''
    };

    var User = $resource('/backend/user/:userId');

    $scope.users = {};

    var self = $scope;
    self.loadUsers = function() {

        $scope.users = User.query();

    }

    self.registerUser = function()
    {

        User.save( $scope.user, function(){
            $scope.loadUsers();
        }, function() {
            console.log('error');
        } );
    }

    self.loadUsers();
}